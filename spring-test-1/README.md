# spring-test-1

A small Spring Boot dependency-injection example. `GameRunner` depends only on
the `NintendoGames` interface. Spring creates exactly one implementation based
on configuration.

## Run

```bash
mvn spring-boot:run
```

## Switch games without Java code changes

Edit `src/main/resources/application.properties`:

```properties
nintendo.game=mario
# or
nintendo.game=super-contra
```

You can also override it for one run:

```bash
mvn spring-boot:run "-Dspring-boot.run.arguments=--nintendo.game=super-contra"
```

## Data flow

`GET /data/sum` calls `DataController`, which delegates to `BusinessService`.
The business service obtains the initialized sample array from `DataService` and
returns its sum (`150`).
