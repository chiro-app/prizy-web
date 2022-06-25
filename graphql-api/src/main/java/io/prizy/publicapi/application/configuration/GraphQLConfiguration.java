package io.prizy.publicapi.application.configuration;

import io.prizy.publicapi.graphql.directives.AuthorizedDirective;
import io.prizy.publicapi.graphql.scalars.InstantScalar;
import io.prizy.publicapi.graphql.scalars.LocalDateScalar;
import io.prizy.publicapi.graphql.scalars.LongScalar;
import io.prizy.publicapi.graphql.scalars.UUIDScalar;
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.NativeDetector;
import org.springframework.core.io.ClassPathResource;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

/**
 * @author Nidhal Dogga
 * @created 6/24/2022 8:40 PM
 */


@Configuration
public class GraphQLConfiguration {

  @Bean
  RuntimeWiringConfigurer runtimeWiringConfigurer() {
    return wiringBuilder -> wiringBuilder
      .scalar(LongScalar.SCALAR)
      .scalar(UUIDScalar.SCALAR)
      .scalar(InstantScalar.SCALAR)
      .scalar(LocalDateScalar.SCALAR)
      .directive(AuthorizedDirective.NAME, new AuthorizedDirective())
      .build();
  }

  @Bean
  GraphQlSourceBuilderCustomizer graphQlSourceBuilderCustomizer() {
    return builder -> {
      // this part isn't great, but:
      // right now the PatternResourceResolver used to 'find' the schema files on the classpath fails because
      // in the graalvm native image world there is NO classpath to speak of. So this code manually
      // adds a Resource, knowing that the default resolution logic fail. But, it only does so in
      // a GraalVM native image context
      if (NativeDetector.inNativeImage()) {
        builder.schemaResources(new ClassPathResource("graphql/schema.graphqls"));
      }
    };
  }

}
