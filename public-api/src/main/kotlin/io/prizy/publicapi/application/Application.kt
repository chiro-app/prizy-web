package io.prizy.publicapi.application

import io.prizy.publicapi.application.properties.AppConfigurationProperties
import io.prizy.publicapi.application.properties.GameProperties
import io.prizy.publicapi.application.properties.ResourceProperties
import io.prizy.publicapi.application.properties.StorageProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = ["io.prizy.publicapi"])
@EnableConfigurationProperties(
  GameProperties::class,
  StorageProperties::class,
  ResourceProperties::class,
  AppConfigurationProperties::class
)
class Application

fun main(args: Array<String>) {
  runApplication<Application>(*args)
}
