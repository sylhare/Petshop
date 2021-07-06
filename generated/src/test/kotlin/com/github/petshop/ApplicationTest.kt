package com.github.petshop

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringJUnit4ClassRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
internal class ApplicationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun contextLoad() = Unit

    private val petPayload =
        "{\"name\":\"example\",\"photoUrls\":[],\"id\":1,\"category\":{\"id\":1,\"name\":\"categrory\"},\"tags\":[],\"status\":\"available\"}"

    @Test
    fun postExamplePetMvc() {
        mockMvc.perform(
            post("/v2/pet")
                .content(petPayload)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().string(petPayload))
    }
}
