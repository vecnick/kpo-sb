package com.itzephir.kpo.homework1.helper

import com.itzephir.kpo.homework1.domain.Computer
import com.itzephir.kpo.homework1.domain.Feed
import com.itzephir.kpo.homework1.domain.Table
import com.itzephir.kpo.homework1.domain.Thing

object ThingCreator {
    fun create(params: List<String>): Thing {
        return when (val type = params[0]) {
            "Computer" -> createComputer()
            "Feed"     -> createFeed(params)
            "Table"    -> createTable()
            else       -> error("Unknown thing type: $type")
        }
    }

    private fun createComputer(): Computer = Computer()

    private fun createFeed(params: List<String>): Feed {
        val (_, count) = params
        return Feed(count.toInt())
    }

    private fun createTable(): Table = Table()
}