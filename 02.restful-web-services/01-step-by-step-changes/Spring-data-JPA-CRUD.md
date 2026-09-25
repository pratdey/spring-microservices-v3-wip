# Spring Data JPA CRUD Internals

## 1. Overall request flow

When a REST endpoint uses a Spring Data JPA repository, the request flows through these layers:

```text
HTTP request
    ↓
Controller
    ↓
Repository interface
    ↓
SimpleJpaRepository (Spring Data's implementation)
    ↓
EntityManager (JPA)
    ↓
Hibernate (JPA provider)
    ↓
Database
```

For example:

```java
Post savedPost = postRepository.save(post);
```

`PostRepository` is only an interface. Spring Data creates its implementation at runtime. That implementation delegates the work to JPA's `EntityManager`.

## 2. What `JpaRepository` provides

```java
public interface PostRepository extends JpaRepository<Post, Integer> {
}
```

`Post` is the entity type and `Integer` is the type of its primary key. By extending `JpaRepository`, the repository receives methods such as:

```java
save(entity)
findById(id)
findAll()
delete(entity)
deleteById(id)
existsById(id)
count()
```

Spring Data generates the repository implementation; you normally do not write these CRUD methods yourself.

## 3. How `save()` works

The important part of Spring Data's `save()` logic is conceptually similar to this:

```java
public <S extends T> S save(S entity) {
    if (entityInformation.isNew(entity)) {
        entityManager.persist(entity);
        return entity;
    }

    return entityManager.merge(entity);
}
```

Spring first determines whether the entity is new. For an entity with a generated ID, it normally checks whether the ID is `null`.

```java
entity.getId() == null
```

This is why the value of the ID changes the behavior of `save()`.

## 4. Creating a new entity: `persist()`

For a create request, the ID should be omitted:

```json
{
  "description": "I want to Get AWS Certified"
}
```

Before saving:

```java
post.getId() == null
```

Spring calls:

```java
entityManager.persist(post);
```

Hibernate treats the object as new and schedules an `INSERT` statement. The generated ID is assigned by Hibernate or the database.

Conceptually, the SQL is:

```sql
INSERT INTO post (description, user_id, id)
VALUES (?, ?, ?);
```

## 5. Updating an existing entity: `merge()`

For an update request, the entity has an ID that already exists in the database:

```json
{
  "id": 21003,
  "description": "Updated description"
}
```

Spring sees a non-null ID and generally calls:

```java
entityManager.merge(post);
```

Hibernate then attempts an update:

```sql
UPDATE post
SET description = ?, user_id = ?
WHERE id = 21003;
```

`merge()` returns a managed copy, so the returned value should be used when working directly with `EntityManager`:

```java
Post managedPost = entityManager.merge(post);
```

## 6. Why the optimistic locking exception occurred

The create endpoint is:

```java
@PostMapping("/jpa/users/{id}/posts")
public ResponseEntity<Object> createPostForUser(
        @PathVariable int id,
        @Valid @RequestBody Post post) {
```

The request included an ID:

```json
{
  "id": 20003,
  "description": "I want to Get AWS Certified"
}
```

Because the ID was non-null, Spring Data interpreted the object as an existing post and used `merge()` rather than `persist()`. If that ID did not exist, Hibernate tried to update a row that was not present. This resulted in an exception such as:

```text
ObjectOptimisticLockingFailureException:
Row was updated or deleted by another transaction
```

The message can also occur when another transaction really changed or deleted the row, but in this case the most likely cause is sending a generated ID for a new post.

## 7. CRUD methods and their typical SQL

| Repository method | Typical JPA operation | Typical SQL |
|---|---|---|
| `save(newEntity)` | `persist()` | `INSERT` |
| `save(existingEntity)` | `merge()` | `UPDATE` |
| `findById(id)` | `find()` | `SELECT ... WHERE id = ?` |
| `findAll()` | Query generation | `SELECT ...` |
| `deleteById(id)` | Find, then remove | `DELETE ... WHERE id = ?` |
| `delete(entity)` | `remove()` | `DELETE` |

The exact SQL depends on the database, mappings, Hibernate version, and transaction state.

## 8. Entity lifecycle and persistence context

JPA manages entities through different lifecycle states:

```text
Transient → Managed → Detached
                 ↓
              Removed
```

### Transient

The object was created with `new`, but is not known to the persistence context:

```java
Post post = new Post();
post.setDescription("A new post");
```

### Managed

After `persist()` or `findById()`, the entity is managed by the current persistence context. Hibernate tracks changes to it.

### Detached

After the transaction ends, the entity is no longer attached to the persistence context. `merge()` can copy its state back into a managed entity.

### Removed

After `remove()`, the entity is scheduled for deletion when the transaction commits.

## 9. Dirty checking and transactions

Hibernate uses a persistence context (first-level cache) to track managed entities. If a managed entity changes, Hibernate detects the change automatically:

```java
Post post = entityManager.find(Post.class, id);
post.setDescription("New description");
```

At transaction commit, Hibernate performs the required `UPDATE`. This is called dirty checking.

```text
Load entity
    ↓
Modify managed entity
    ↓
Transaction commits
    ↓
Hibernate detects changes
    ↓
UPDATE is executed
```

Spring Boot usually manages transactions around repository operations. SQL may not execute exactly at the `save()` line; it is commonly flushed at transaction commit.

## 10. Recommended API design

For a create endpoint, do not allow the client to send the generated ID:

```json
{
  "description": "I want to Get AWS Certified"
}
```

For stronger protection, use a request DTO instead of accepting the entity directly:

```java
public class CreatePostRequest {
    @Size(min = 10)
    private String description;

    // getter and setter
}
```

Then create the entity in the controller or service, leaving its ID as `null`. This prevents clients from accidentally forcing an insert endpoint to behave like an update endpoint.
