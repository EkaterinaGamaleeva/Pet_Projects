package com.dunice.advancedServiceKotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories("com.dunice.advancedServiceKotlin.repository")
class AdvancedServiceKotlinApplication

fun main(args: Array<String>) {
	runApplication<AdvancedServiceKotlinApplication>(*args)
}
