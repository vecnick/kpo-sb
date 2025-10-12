package com.itzephir.kpo.homework1.interactor

import com.itzephir.kpo.homework1.domain.Animal
import com.itzephir.kpo.homework1.domain.Feed
import com.itzephir.kpo.homework1.domain.Herbivore
import com.itzephir.kpo.homework1.service.AnimalHealthyChecker
import com.itzephir.kpo.homework1.service.AnimalService
import com.itzephir.kpo.homework1.service.InventoryService
import com.itzephir.kpo.homework1.service.KindnessChecker

class GetContactAnimalsInteractor(
    private val animalService: AnimalService,
    private val inventoryService: InventoryService,
    private val animalHealthyChecker: AnimalHealthyChecker,
    private val kindnessChecker: KindnessChecker,
) {
    operator fun invoke(): List<Animal> {
        val allFeed = inventoryService.getAll().filterIsInstance<Feed>().sumOf { it.count }
        val animals = animalService.getAll()
        val average = allFeed / (animals.size.takeIf { it != 0 } ?: return emptyList())
        val animalsAndFeed = animals zip List(animals.size) { average }
        return animalsAndFeed
            .filter { (animal, fed) -> animalHealthyChecker.isHealthy(animal, fed) }
            .map { (animal, _) -> animal }
            .filter {
                if (it !is Herbivore) true
                else kindnessChecker.isKind(it)
            }
    }
}