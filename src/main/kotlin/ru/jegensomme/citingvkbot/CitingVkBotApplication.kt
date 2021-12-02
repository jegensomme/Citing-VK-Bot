package ru.jegensomme.citingvkbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CitingVkBotApplication

fun main(args: Array<String>) {
    runApplication<CitingVkBotApplication>(*args)
}
