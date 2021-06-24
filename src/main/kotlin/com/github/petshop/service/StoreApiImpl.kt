package com.github.petshop.service

import org.generated.petstore.api.StoreApi
import org.generated.petstore.model.Order

class StoreApiImpl : StoreApi {
    override fun deleteOrder(orderId: String?) {
        // do nothing
    }

    override fun getInventory(): MutableMap<String, Int> {
        return mutableMapOf()
    }

    override fun getOrderById(orderId: Long?): Order {
        return Order()
    }

    override fun placeOrder(order: Order?): Order {
        return Order()
    }
}
