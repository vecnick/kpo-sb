package com.itzephir.kpo.homework1.di.shared

import com.itzephir.kpo.homework1.di.DIContainer

object DICurrentContainer {
    val currentContainer = DIContainer()
}

inline fun <T> di(block: DIContainer.() -> T): T = DICurrentContainer.currentContainer.block()