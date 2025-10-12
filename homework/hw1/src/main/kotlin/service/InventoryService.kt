package com.itzephir.kpo.homework1.service

import com.itzephir.kpo.homework1.domain.Inventory

interface InventoryService {
    fun getAll(): List<Inventory>
    fun add(inventory: Inventory)
}