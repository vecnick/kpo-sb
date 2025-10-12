package com.itzephir.kpo.homework1.di

import arrow.core.Either
import com.itzephir.kpo.homework1.di.DIContainer.Companion.factory
import com.itzephir.kpo.homework1.di.DIContainer.Companion.get
import com.itzephir.kpo.homework1.di.DIContainer.Companion.inject
import com.itzephir.kpo.homework1.di.DIContainer.Companion.singleton
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs


class DIContainerTests {
    val container = DIContainer()

    class Providable(val string: String)

    @Test
    fun `test di container get singleton`() {
        with(container) {
            singleton(Providable("Hello World!"))

            val providable = get<Providable>()
            assertIs<Either.Right<Providable>>(providable)
            assertEquals(providable.getOrNull()?.string, "Hello World!")
        }
    }

    @Test
    fun `test di container get factory`() {
        with(container) {
            factory { Providable("Hello World!") }

            val providable = get<Providable>()
            println(providable)
            assertIs<Either.Right<Providable>>(providable)
            assertEquals(providable.getOrNull()?.string, "Hello World!")
        }
    }

    @Test
    fun `test di container inject singleton`() {
        with(container) {
            singleton(Providable("Hello World!"))

            val providable by inject<Providable>()
            assertIs<Either.Right<Providable>>(providable)
            assertEquals(providable.getOrNull()?.string, "Hello World!")
        }
    }

    @Test
    fun `test di container inject factory`() {
        with(container) {
            factory { Providable("Hello World!") }

            val providable by inject<Providable>()
            assertIs<Either.Right<Providable>>(providable)
            assertEquals(providable.getOrNull()?.string, "Hello World!")
        }
    }
}