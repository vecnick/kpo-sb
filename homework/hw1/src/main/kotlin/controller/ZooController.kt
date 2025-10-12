package com.itzephir.kpo.homework1.controller

import com.itzephir.kpo.homework1.domain.Inventory
import com.itzephir.kpo.homework1.domain.Report
import com.itzephir.kpo.homework1.interactor.AddInventoryInteractor
import com.itzephir.kpo.homework1.interactor.GetContactAnimalsInteractor
import com.itzephir.kpo.homework1.interactor.GetReportInteractor

class ZooController(
    private val getContactAnimalsInteractor: GetContactAnimalsInteractor,
    private val addInventoryInteractor: AddInventoryInteractor,
    private val getReportInteractor: GetReportInteractor,
) {
    fun getContactAnimals() = getContactAnimalsInteractor()

    fun addInventory(inventory: Inventory) = addInventoryInteractor(inventory)

    fun generateReport(): Report = getReportInteractor()
}