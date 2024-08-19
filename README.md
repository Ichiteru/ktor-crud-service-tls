# ktor-counter-crud-service

## Stack:
* Kotlin 2.0.10
* Ktor 2.3.12
* H2 in-memory database
* Exposed JB
* Hikari CP
* Swagger UI

## Links:
* Http url:  http://0.0.0.0:8080
* Https url:  https://0.0.0.0:8443/
* Swagger http url: http://0.0.0.0:8080/swagger
* Swagger https url: https://0.0.0.0:8443/swagger

## Request examples: 

- Create new counter

```
curl --location 'https://localhost:8443/counters' \
--header 'Content-Type: application/json' \
--data '{
    "name": "example",
    "value": 30
}'
```
- Get counter by name

```
curl --location 'https://localhost:8443/counters/{name}'
```
- Get all counters
```
curl --location 'https://localhost:8443/counters'
```
- Increment counter value
```
curl --location --request POST 'https://localhost:8443/counters/{name}/increment'
```
- Delete counter by name
```
curl --location --request DELETE 'https://localhost:8443/counters/{name}'
```

### To create new self-signed jks file with certificate keys, launch next code:
```
        val keyStoreFile = File("build/keystore.jks")
        val keyStore = buildKeyStore {
          certificate("sampleAlias") {
            password = "foobar"
            domains = listOf("127.0.0.1", "0.0.0.0", "localhost")
          }
        }

        KeyStore.getInstance("build/keystore.jks", "123456")
        keyStore.saveToFile(keyStoreFile, "123456")
```