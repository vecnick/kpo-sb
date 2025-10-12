package com.itzephir.kpo.homework1.di

data class DIDefinitionNotFoundError(val key: String): Throwable() {
    override val message: String
        get() = "No definition for $key was found!"
}