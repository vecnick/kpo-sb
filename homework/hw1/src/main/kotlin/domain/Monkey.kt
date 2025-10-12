package com.itzephir.kpo.homework1.domain

data class Monkey(
    override val name: String,
    override val requiredFoodPerDay: Int,
) : Predator()
