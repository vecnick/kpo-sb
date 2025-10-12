package com.itzephir.kpo.homework1.di.unsafe

import arrow.core.identity
import com.itzephir.kpo.homework1.di.DIContainer
import com.itzephir.kpo.homework1.di.DIContainer.Companion.factory
import com.itzephir.kpo.homework1.di.DIContainer.Companion.get
import com.itzephir.kpo.homework1.di.DIContainer.Companion.inject
import com.itzephir.kpo.homework1.di.DIContainer.Companion.singleton

inline fun <reified T> DIContainer.singleton(instance: T) = singleton(instance).fold(
    ifLeft = { throw it },
    ifRight = ::identity
)

inline fun <reified T> DIContainer.factory(noinline factory: () -> T) = factory(factory).fold(
    ifLeft = { throw it },
    ifRight = ::identity
)

inline fun <reified T> DIContainer.get(): T = get<T>().fold(
    ifLeft = { throw it },
    ifRight = ::identity
)

@Suppress("unused")
inline fun <reified T> DIContainer.inject(): Lazy<T> = lazy {
    inject<T>().value.fold(ifLeft = { throw it }, ifRight = ::identity)
}
