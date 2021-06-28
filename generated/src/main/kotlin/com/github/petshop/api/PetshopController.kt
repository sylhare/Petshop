package com.github.petshop.api

import com.github.petshop.service.PetshopService
import org.generated.petstore.api.PetApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController

@RestController
class PetshopController : PetApi {

    @Autowired
    override lateinit var service: PetshopService
}
