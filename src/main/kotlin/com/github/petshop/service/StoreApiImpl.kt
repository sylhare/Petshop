package com.github.petshop.service

import org.generated.petstore.api.StoreApi
import org.generated.petstore.model.Order
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

/**
 * Does nothing, just as an example
 */
@RestController
class StoreApiImpl : StoreApi {
    override fun deleteOrder(orderId: String?): ResponseEntity<Void>? {
        return ResponseEntity.ok(null)
    }

    override fun getInventory(): ResponseEntity<MutableMap<String, Int>>? {
        return ResponseEntity.ok(null)
    }

    override fun getOrderById(orderId: Long?): ResponseEntity<Order>? {
        return ResponseEntity.ok(null)
    }

// When not implemented, the default version in StoreApi will be used.
//    override fun placeOrder(order: Order?): ResponseEntity<Order>? {
//        return ResponseEntity.ok(null)
//    }
}
