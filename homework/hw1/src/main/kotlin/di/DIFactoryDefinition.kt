package com.itzephir.kpo.homework1.di

class DIFactoryDefinition<out T : Any>(private val factory: () -> T) : DIDefinition<T> {
    override fun get(): T {
        return factory.invoke()
    }
}