package com.itzephir.kpo.homework1.service

import com.itzephir.kpo.homework1.domain.Inventory

class ZooInventoryService : InventoryService {
    val inventories = mutableListOf<Inventory>()
    override fun getAll(): List<Inventory> {
        return inventories.toList()
    }

    override fun add(inventory: Inventory) {
        inventories.add(inventory)
    }
}