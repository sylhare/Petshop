package com.github.petshop.service


import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class PetShopImplTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @LocalServerPort
    var applicationPort: Int = 0

    var testRestTemplate = TestRestTemplate()

    @Test
    fun getPetshopTest() {
        mvc.perform(get("/petshop"))
            .andExpect(status().isOk)
    }

    @Test
    fun postPetshopFail() {
        mvc.perform(post("/petshop"))
            .andExpect(status().is4xxClientError)
    }

    @Test
    fun getPetshopTestRestTemplate() {
        val result = testRestTemplate.exchange(
            applicationUrl() + "/petshop",
            HttpMethod.GET,
            HttpEntity("", HttpHeaders()),
            String::class.java
        )

        Assertions.assertEquals(HttpStatus.OK, result.statusCode)
        Assertions.assertEquals("Hello", result.body)
    }

    private fun applicationUrl() = "http://localhost:$applicationPort"
    fun jsonHeader() = HttpHeaders().also {
        it.contentType = MediaType.APPLICATION_JSON
    }
}
