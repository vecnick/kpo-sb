package com.itzephir.kpo.homework1.helper

import com.itzephir.kpo.homework1.domain.*

object AnimalCreator {
    fun createAnimal(params: List<String>): Animal {
        return when (val type = params[0]) {
            "Monkey" -> createMonkey(params)
            "Rabbit" -> createRabbit(params)
            "Tiger"  -> createTiger(params)
            "Wolf"   -> createWolf(params)
            else     -> error("Unknown animal type: $type")
        }
    }

    private fun createMonkey(params: List<String>): Monkey {
        val (_, name, requiredFood) = params
        return Monkey(name, requiredFood.toInt())
    }

    private fun createRabbit(params: List<String>): Rabbit {
        val (_, name, requiredFood, kindness) = params
        return Rabbit(name, requiredFood.toInt(), kindness.toInt())
    }

    private fun createTiger(params: List<String>): Tiger {
        val (_, name, requiredFood) = params
        return Tiger(name, requiredFood.toInt())
    }

    private fun createWolf(params: List<String>): Wolf {
        val (_, name, requiredFood) = params
        return Wolf(name, requiredFood.toInt())
    }
}