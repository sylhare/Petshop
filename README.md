# Petshop

Kotlin springboot project using a generated API from a swagger file

## Swagger code generation

Using the [petstore.yml](https://raw.githubusercontent.com/openapitools/openapi-generator/master/modules/openapi-generator/src/test/resources/3_0/petstore.yaml) example.
We are going to use [openapi-generator](https://github.com/OpenAPITools/openapi-generator) which is a fork from the [swagger-codegen](https://swagger.io/tools/swagger-codegen/).

### Create the API with only the swagger file

You need to install:

```bash
brew install openapi-generator
```

Then run it using your swagger yaml file and if you have your config file. You can also specify some [global properties](https://openapi-generator.tech/docs/globals/#available-global-properties) as key=value pairs.
(Find the configuration for [java](https://openapi-generator.tech/docs/generators/java/) and [kotlin](https://openapi-generator.tech/docs/generators/kotlin/))

```bash
openapi-generator generate -i src/main/resources/swagger/petstore.yml -g kotlin-spring  --config src/main/resources/api-config.json
# openapi-generator generate -i ../src/main/resources/swagger/petstore.yml -g kotlin-spring  --config ../src/main/resources/api-config.json --global-property apiTests=true,modelTests=true,apiDocs=true,modelDocs=true
```

This will create the project with a `build.gradle.kts` (and also a `pom.xml` for maven).

For gradle, the wrapper won't be created automatically, you can do so by running:

```bash
gradle wrapper --gradle-version 4.8 --distribution-type all
./gradlew assemble
```

The syntax used in the generated build.gradle.kts is rather old and might not be compatible with gradle 7.0+.
Then you need to create the `PetApiServiceImpl` that implements `PetApiService` and add it to the `PetApiController` implementing `PetApi`:

```kotlin
@RestController
class PetApiController : PetApi {

    @Autowired
    override lateinit var service: PetApiServiceImpl
}
```

This service is where you can start adding your own implementation of the Petshop.
(Because the swagger yaml only generate the endpoints, it's not that magical!)

> The generated code is a bit lacking and will require some changes to be working and up to date.

To build the project using gradle, run:

```bash
gradle build && java -jar build/libs/openapi-spring-1.0.0.jar
```

If all builds successfully, the server should run on [http://localhost:8080/](http://localhost:8080/)

### With customization

If you want to customize or use the generated classes for something else.
You can use the [org.openapi.generator plugin](https://openapi-generator.tech/docs/plugins/) in your code like:

```kotlin
plugins {
    id("org.openapi.generator") version "5.1.1"
}

openApiGenerate {
    generatorName.set("jaxrs-spec")
    inputSpec.set("src/main/resources/petstore.yml")
    outputDir.set("$buildDir/generated")
    configFile.set("src/main/resources/api-config.json")
}

// Add the generated sources to your project
java.sourceSets["main"].java.srcDir("$buildDir/generated/src/gen/java")
```

The api-config can also be added directly within the `openApiGenerate` gradle task.
The `inputSpec` is the swagger file that the code will be generated from.

We use [`jaxrs-spec`](https://en.wikipedia.org/wiki/Jakarta_RESTful_Web_Services) instead of `kotlin-spring` to customize it.
Since the JAX-RS provides annotations and interfaces that can be implemented to create a Restful API.
