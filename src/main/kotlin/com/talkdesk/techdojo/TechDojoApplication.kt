package com.talkdesk.techdojo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TechDojoApplication

fun main(args: Array<String>) {
	runApplication<TechDojoApplication>(*args)
}
