# How `CurrencyExchangeProxy` Maps Exchange JSON to `CurrencyConversion`

## Question

`/currency-exchange/from/{from}/to/{to}` returns a `CurrencyExchange` object from the currency-exchange service. How can `CurrencyExchangeProxy` in the currency-conversion service receive it as a `CurrencyConversion` object?

## Answer

The proxy does **not** receive a Java `CurrencyExchange` object directly. The services communicate through HTTP, so the exchange service serializes its object to JSON. OpenFeign receives that JSON and Spring's Jackson message converter deserializes it into the return type declared on the proxy method.

```java
@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    CurrencyConversion retrieveExchangeValue(
            @PathVariable String from,
            @PathVariable String to);
}
```

Because the method return type is `CurrencyConversion`, Feign/Jackson creates a `CurrencyConversion` instance.

## Request flow

```text
CurrencyConversionController
        |
        | proxy.retrieveExchangeValue("USD", "INR")
        v
CurrencyExchangeProxy (OpenFeign HTTP client)
        |
        | GET /currency-exchange/from/USD/to/INR
        v
CurrencyExchangeController
        |
        | returns CurrencyExchange
        v
HTTP JSON response
        |
        | Jackson deserializes JSON using the proxy return type
        v
CurrencyConversion instance
```

For example, the exchange service sends JSON similar to:

```json
{
  "id": 10001,
  "from": "USD",
  "to": "INR",
  "conversionMultiple": 65.00,
  "environment": "8000"
}
```

## Why the conversion succeeds

The two Java class names do not need to be the same. Jackson maps JSON properties by name, using setters (or fields/annotations, depending on configuration).

Both classes have compatible properties:

| JSON field | `CurrencyExchange` | `CurrencyConversion` |
| --- | --- | --- |
| `id` | `Long` | `Long` |
| `from` | `String` | `String` |
| `to` | `String` | `String` |
| `conversionMultiple` | `BigDecimal` | `BigDecimal` |
| `environment` | `String` | `String` |

`CurrencyConversion` also has `quantity` and `totalCalculatedAmount`. Since these fields are not in the exchange-service response, Jackson leaves them as `null`.

The conversion controller then builds a new, complete `CurrencyConversion` object with those values:

```java
CurrencyConversion exchangeData = proxy.retrieveExchangeValue(from, to);

return new CurrencyConversion(
        exchangeData.getId(),
        from,
        to,
        quantity,
        exchangeData.getConversionMultiple(),
        quantity.multiply(exchangeData.getConversionMultiple()),
        exchangeData.getEnvironment() + " feign");
```

## Design note

This works, but a clearer production design is to create a dedicated response DTO (for example, `CurrencyExchangeResponse`) for the Feign client and map it explicitly to `CurrencyConversion`. That avoids coupling the client to a model whose extra fields belong to a different responsibility.
