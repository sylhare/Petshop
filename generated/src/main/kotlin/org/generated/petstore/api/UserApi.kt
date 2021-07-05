/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (5.1.1).
 * https://openapi-generator.tech
 * Do not edit the class manually.
*/
package org.generated.petstore.api

import org.generated.petstore.model.User
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.validation.annotation.Validated
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.beans.factory.annotation.Autowired

import javax.validation.Valid
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

import kotlin.collections.List
import kotlin.collections.Map

@Validated
@RequestMapping("\${api.base-path:/v2}")
interface UserApi {

    val service: UserApiService // Had to be added for the generated code to compile

    @PostMapping(
            value = ["/user"],
            consumes = ["application/json"]
    )
    fun createUser( @Valid @RequestBody user: User
): ResponseEntity<Unit> {
        return ResponseEntity(service.createUser(user), HttpStatus.valueOf(200))
    }


    @PostMapping(
            value = ["/user/createWithArray"],
            consumes = ["application/json"]
    )
    fun createUsersWithArrayInput( @Valid @RequestBody user: kotlin.collections.List<User>
): ResponseEntity<Unit> {
        return ResponseEntity(service.createUsersWithArrayInput(user), HttpStatus.valueOf(200))
    }


    @PostMapping(
            value = ["/user/createWithList"],
            consumes = ["application/json"]
    )
    fun createUsersWithListInput( @Valid @RequestBody user: kotlin.collections.List<User>
): ResponseEntity<Unit> {
        return ResponseEntity(service.createUsersWithListInput(user), HttpStatus.valueOf(200))
    }


    @DeleteMapping(
            value = ["/user/{username}"]
    )
    fun deleteUser( @PathVariable("username") username: kotlin.String
): ResponseEntity<Unit> {
        return ResponseEntity(service.deleteUser(username), HttpStatus.valueOf(400))
    }


    @GetMapping(
            value = ["/user/{username}"],
            produces = ["application/xml", "application/json"]
    )
    fun getUserByName( @PathVariable("username") username: kotlin.String
): ResponseEntity<User> {
        return ResponseEntity(service.getUserByName(username), HttpStatus.valueOf(200))
    }


    @GetMapping(
            value = ["/user/login"],
            produces = ["application/xml", "application/json"]
    )
    fun loginUser(@NotNull @Pattern(regexp="^[a-zA-Z0-9]+[a-zA-Z0-9\\.\\-_]*[a-zA-Z0-9]+$")  @RequestParam(value = "username", required = true) username: kotlin.String
,@NotNull  @RequestParam(value = "password", required = true) password: kotlin.String
): ResponseEntity<kotlin.String> {
        return ResponseEntity(service.loginUser(username, password), HttpStatus.valueOf(200))
    }


    @GetMapping(
            value = ["/user/logout"]
    )
    fun logoutUser(): ResponseEntity<Unit> {
        return ResponseEntity(service.logoutUser(), HttpStatus.valueOf(200))
    }


    @PutMapping(
            value = ["/user/{username}"],
            consumes = ["application/json"]
    )
    fun updateUser( @PathVariable("username") username: kotlin.String
, @Valid @RequestBody user: User
): ResponseEntity<Unit> {
        return ResponseEntity(service.updateUser(username, user), HttpStatus.valueOf(400))
    }
}
