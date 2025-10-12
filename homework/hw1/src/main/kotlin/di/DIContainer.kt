package com.itzephir.kpo.homework1.di

import arrow.core.Either
import arrow.core.raise.either

class DIContainer {
    private val definitions = mutableMapOf<String, DIDefinition<Any>>()

    val classKeys = mutableMapOf<String, String>()

    @PublishedApi
    internal fun register(key: String, definition: DIDefinition<Any>): Either<DIDefinitionAlreadyDefinedError, Unit> =
        either {
            if (key in definitions) {
                raise(DIDefinitionAlreadyDefinedError(key))
            }
            definitions[key] = definition
        }

    @PublishedApi
    internal fun get(key: String): Either<DIDefinitionNotFoundError, Any> = either {
        if (key !in definitions) raise(DIDefinitionNotFoundError(key))
        definitions.getValue(key).get()
    }

    companion object {

        inline fun <reified T> DIContainer.singleton(instance: T): Either<DIDefinitionAlreadyDefinedError, Unit> =
            register(T::class.qualifiedName + "singleton", DISingletonDefinition(instance as Any)).also {
                classKeys[T::class.qualifiedName ?: ""] = T::class.qualifiedName + "singleton"
            }

        inline fun <reified T> DIContainer.factory(noinline factory: () -> T): Either<DIDefinitionAlreadyDefinedError, Unit>{
            val factory = { factory() as Any }
            val key = T::class.qualifiedName + "factory"
            classKeys[T::class.qualifiedName ?: ""] = key
            return register(key, DIFactoryDefinition(factory))
        }

        inline fun <reified T> DIContainer.get(): Either<DIDefinitionNotFoundError, T> = either {
            val key = classKeys[T::class.qualifiedName ?: ""]
                ?: raise(DIDefinitionNotFoundError(T::class.qualifiedName ?: ""))
            get(key).map { it as T }.bind()
        }

        inline fun <reified T> DIContainer.inject(): Lazy<Either<DIDefinitionNotFoundError, T>> = lazy { get() }
    }
}