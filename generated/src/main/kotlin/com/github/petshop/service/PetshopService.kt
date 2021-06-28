package com.github.petshop.service

import org.generated.petstore.api.PetApiService
import org.generated.petstore.model.ModelApiResponse
import org.generated.petstore.model.Pet
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service

@Service
class PetshopService : PetApiService {
    override fun addPet(pet: Pet): Pet {
        TODO("Not yet implemented")
    }

    override fun deletePet(petId: Long, apiKey: String?) {
        TODO("Not yet implemented")
    }

    override fun findPetsByStatus(status: List<String>): List<Pet> {
        TODO("Not yet implemented")
    }

    override fun findPetsByTags(tags: List<String>): List<Pet> {
        TODO("Not yet implemented")
    }

    override fun getPetById(petId: Long): Pet {
        TODO("Not yet implemented")
    }

    override fun updatePet(pet: Pet): Pet {
        TODO("Not yet implemented")
    }

    override fun updatePetWithForm(petId: Long, name: String?, status: String?) {
        TODO("Not yet implemented")
    }

    override fun uploadFile(petId: Long, additionalMetadata: String?, file: Resource?): ModelApiResponse {
        TODO("Not yet implemented")
    }
}
