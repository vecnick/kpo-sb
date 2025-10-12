package com.itzephir.kpo.homework1.interactor

import com.itzephir.kpo.homework1.domain.Report
import com.itzephir.kpo.homework1.domain.StringReport
import com.itzephir.kpo.homework1.service.InventoryService

class GetReportInteractor(
    private val inventoryService: InventoryService,
    private val getContactAnimalsInteractor: GetContactAnimalsInteractor,
) {
    operator fun invoke(): Report {
        return StringReport("Inventory Report", generateReportContent())
    }

    private fun generateReportContent(): String {
        val inventory = inventoryService.getAll()
        val healthyAnimals = getContactAnimalsInteractor()
        return "All inventory: $inventory\nHealthy animals: $healthyAnimals\n\n"
    }
}