package com.itzephir.kpo.homework1.service

import com.itzephir.kpo.homework1.domain.Animal

interface AnimalHealthyChecker {
    fun isHealthy(animal: Animal, fed: Int): Boolean
}