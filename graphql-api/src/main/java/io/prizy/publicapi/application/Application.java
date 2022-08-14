package io.prizy.publicapi.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Nidhal Dogga
 * @created 6/24/2022 7:23 PM
 */


@SpringBootApplication
@ComponentScan(basePackages = "io.prizy.publicapi")
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
