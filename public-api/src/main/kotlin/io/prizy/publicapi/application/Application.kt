package io.prizy.publicapi.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan(basePackages = ["io.prizy.publicapi"])
@SpringBootApplication
class Application

fun main(args: Array<String>) {
  runApplication<Application>(*args)
}
