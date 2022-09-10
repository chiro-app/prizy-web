package io.prizy.graphql.autoconfiguration

import io.prizy.graphql.properties.GraphQLLoggingProperties
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.elasticsearch.client.ClientConfiguration
import org.springframework.data.elasticsearch.client.RestClients
import org.springframework.data.elasticsearch.core.ElasticsearchOperations
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 3:44 PM
 */

@Configuration
@ComponentScan("io.prizy.graphql")
@EnableConfigurationProperties(GraphQLLoggingProperties::class)
open class GraphQLAutoConfiguration {

  @Bean
  @ConditionalOnProperty("prizy.graphql.logging.enabled", havingValue = "true")
  open fun client(properties: GraphQLLoggingProperties): RestHighLevelClient {
    val clientConfiguration: ClientConfiguration = ClientConfiguration.builder()
      .connectedTo("${properties.host}:${properties.port}")
      .usingSsl()
      .withBasicAuth(properties.username, properties.password)
      .build()
    return RestClients.create(clientConfiguration).rest()
  }

  @Bean
  @ConditionalOnProperty("prizy.graphql.logging.enabled", havingValue = "true")
  open fun elasticsearchTemplate(client: RestHighLevelClient): ElasticsearchOperations {
    return ElasticsearchRestTemplate(client)
  }

}
