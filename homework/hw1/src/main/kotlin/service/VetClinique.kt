package com.itzephir.kpo.homework1.service

import com.itzephir.kpo.homework1.domain.Animal

class VetClinique : AnimalHealthyChecker {
    override fun isHealthy(animal: Animal, fed: Int): Boolean = animal.requiredFoodPerDay <= fed
}