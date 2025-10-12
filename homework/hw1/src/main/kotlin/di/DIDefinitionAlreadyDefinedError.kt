package com.itzephir.kpo.homework1.di

data class DIDefinitionAlreadyDefinedError(val key: String) : Throwable() {
    override val message: String
        get() = "A definition for $key was already defined!"
}