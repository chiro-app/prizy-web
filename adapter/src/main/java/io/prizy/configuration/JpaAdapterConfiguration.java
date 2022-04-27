package io.prizy.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "io.prizy.adapters")
@ComponentScan(basePackages = "io.prizy.adapters")
@EnableJpaRepositories(basePackages = "io.prizy.adapters")
public class JpaAdapterConfiguration {

}