package com.itzephir.kpo.homework1.service

import com.itzephir.kpo.homework1.domain.Animal

class AnimalService(
    private val inventoryService: InventoryService,
) {
    fun getAll(): List<Animal> = inventoryService.getAll().filterIsInstance<Animal>()

    fun add(animal: Animal): Unit = inventoryService.add(animal)
}