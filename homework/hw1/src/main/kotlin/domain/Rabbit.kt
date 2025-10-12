package com.itzephir.kpo.homework1.domain

data class Rabbit(
    override val name: String,
    override val requiredFoodPerDay: Int,
    override val kindness: Int,
) : Herbivore(kindness)
