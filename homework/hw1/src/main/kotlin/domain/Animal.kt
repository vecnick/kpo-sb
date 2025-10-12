package com.itzephir.kpo.homework1.domain

abstract class Animal : Eater, Inventory, Named {
    override val count: Int = 1
}
