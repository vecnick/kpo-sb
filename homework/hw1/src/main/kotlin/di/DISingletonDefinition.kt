package com.itzephir.kpo.homework1.di

class DISingletonDefinition<out T: Any>(private val instance: T): DIDefinition<T> {
    override fun get(): T {
        return instance
    }
}