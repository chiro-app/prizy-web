package io.prizy.graphql.autoconfiguration

import io.prizy.graphql.properties.GraphQLLoggingProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 3:44 PM
 */

@Configuration
@ComponentScan("io.prizy.graphql")
@EnableConfigurationProperties(GraphQLLoggingProperties::class)
open class GraphQLAutoConfiguration
