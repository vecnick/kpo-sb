package com.itzephir.kpo.homework1.domain

abstract class Herbivore(
    open val kindness: Int,
) : Animal() {
    init {
        require(kindness in 0..10)
    }
}
