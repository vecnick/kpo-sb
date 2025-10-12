package com.itzephir.kpo.homework1

import com.itzephir.kpo.homework1.controller.ZooController
import com.itzephir.kpo.homework1.di.shared.di
import com.itzephir.kpo.homework1.di.unsafe.factory
import com.itzephir.kpo.homework1.di.unsafe.get
import com.itzephir.kpo.homework1.di.unsafe.singleton
import com.itzephir.kpo.homework1.helper.AnimalCreator
import com.itzephir.kpo.homework1.helper.ThingCreator
import com.itzephir.kpo.homework1.interactor.AddInventoryInteractor
import com.itzephir.kpo.homework1.interactor.GetContactAnimalsInteractor
import com.itzephir.kpo.homework1.interactor.GetReportInteractor
import com.itzephir.kpo.homework1.service.*

fun setupDI() = di {
    singleton(ZooInventoryService() as InventoryService)
    singleton(AnimalService(get()))
    singleton(KindnessChecker())
    singleton(VetClinique() as AnimalHealthyChecker)
    factory { AddInventoryInteractor(get()) }
    factory { GetContactAnimalsInteractor(get(), get(), get(), get()) }
    factory { GetReportInteractor(get(), get()) }
    factory { ZooController(get(), get(), get()) }
}

fun main() {
    setupDI()
    println("Здравствуйте!")
    println("Мы рады приветствовать вас в нашем зоопарке!")

    val controller: ZooController = di { get<ZooController>() }

    println(controller)
    while (true) {
        val input = readln().trim()
        when {
            input == "exit"                -> break
            input == "show-go-zoo"         -> {
                val animals = controller.getContactAnimals()
                println(animals.joinToString("\n") { it.toString() })
            }

            input.startsWith("add-animal") -> {
                val params = input.split(" ").drop(1)
                controller.addInventory(AnimalCreator.createAnimal(params))
            }

            input.startsWith("add-thing")  -> {
                val params = input.split(" ").drop(1)
                controller.addInventory(ThingCreator.create(params))
            }

            input == "report"              -> {
                val report = controller.generateReport()
                println(report)
            }

            else                           -> println("Неизвестная команда")
        }
    }
}