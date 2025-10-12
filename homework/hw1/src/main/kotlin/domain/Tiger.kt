package com.itzephir.kpo.homework1.domain

data class Tiger(
    override val name: String,
    override val requiredFoodPerDay: Int,
) : Predator()
