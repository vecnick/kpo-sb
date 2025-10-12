package com.itzephir.kpo.homework1.service

import com.itzephir.kpo.homework1.domain.Herbivore

class KindnessChecker {
    fun isKind(herbivore: Herbivore) = herbivore.kindness >= 5
}