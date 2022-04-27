package io.prizy.publicapi.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = ["io.prizy.publicapi"])
class Application

fun main(args: Array<String>) {
  runApplication<Application>(*args)
}
