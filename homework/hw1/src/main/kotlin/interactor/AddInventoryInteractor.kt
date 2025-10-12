package com.itzephir.kpo.homework1.interactor

import com.itzephir.kpo.homework1.domain.Inventory
import com.itzephir.kpo.homework1.service.InventoryService

class AddInventoryInteractor(
    private val inventoryService: InventoryService,
) {
    operator fun invoke(inventory: Inventory) = inventoryService.add(inventory)
}