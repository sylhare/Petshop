package com.github.petshop.service

import org.generated.petstore.api.PetshopApi
import org.generated.petstore.api.ShopApi
import org.generated.petstore.model.Pet
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class PetShopImpl : PetshopApi {
    override fun petshopMethod(): ResponseEntity<String> = ResponseEntity("Hello", HttpStatus.OK)
}

@RestController
class ShopImpl: ShopApi {
    override fun petshopMethod(pet: Pet?): ResponseEntity<Pet> = ResponseEntity(pet, HttpStatus.OK)

}
