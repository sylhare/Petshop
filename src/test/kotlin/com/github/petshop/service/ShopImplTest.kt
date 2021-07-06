package com.github.petshop.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.generated.petstore.model.Category
import org.generated.petstore.model.Pet
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class ShopImplTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val pet = Pet().also {
        it.photoUrls = listOf()
        it.status = Pet.StatusEnum.AVAILABLE
        it.id = 1L
        it.name = "example"
        it.tags = listOf()
        it.category = Category().also { cat ->
            cat.id = 1L
            cat.name = "category-lolilol"
        }
    }

    private val petPayload = "{\"id\":1,\"category\":{\"id\":1,\"name\":\"category-lolilol\"},\"name\":\"example\",\"photoUrls\":[],\"tags\":[],\"status\":\"available\"}"

    @Test
    fun petTest() {
        assertEquals(
            "{\"id\":1,\"category\":{\"id\":1,\"name\":\"category-lolilol\"},\"name\":\"example\",\"photoUrls\":[],\"tags\":[],\"status\":\"available\"}",
            objectMapper.writeValueAsString(pet)
        )

    }

    @Test
    fun postExamplePetMvc() {
        mockMvc.perform(
            post("/shop")
                .content(petPayload)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON) // If there is xml and json offered
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(petPayload))
    }
}
