package com.itzephir.kpo.homework1.di

interface DIDefinition<out T: Any> {
    fun get(): T
}