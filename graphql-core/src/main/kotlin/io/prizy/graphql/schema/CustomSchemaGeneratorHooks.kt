package io.prizy.graphql.schema

import com.expediagroup.graphql.generator.directives.KotlinDirectiveWiringFactory
import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import graphql.schema.GraphQLType
import io.prizy.graphql.directives.AuthorizedDirective
import io.prizy.graphql.directives.AuthorizedSchemaDirectiveWiring
import io.prizy.graphql.directives.ResourceIdentifierDirective
import io.prizy.graphql.directives.ResourceIdentifierSchemaDirectiveWiring
import io.prizy.graphql.scalars.InstantScalar
import io.prizy.graphql.scalars.LocalDateScalar
import io.prizy.graphql.scalars.UUIDScalar
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDate
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.KType

@Component
class CustomSchemaGeneratorHooks : SchemaGeneratorHooks {

  override val wiringFactory: KotlinDirectiveWiringFactory
    get() = KotlinDirectiveWiringFactory(
      mapOf(
        AuthorizedDirective.NAME to AuthorizedSchemaDirectiveWiring(),
        ResourceIdentifierDirective.NAME to ResourceIdentifierSchemaDirectiveWiring()
      )
    )

  override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
    UUID::class -> UUIDScalar.graphqlScalarType
    Instant::class -> InstantScalar.graphqlScalarType
    LocalDate::class -> LocalDateScalar.graphqlScalarType
    else -> super.willGenerateGraphQLType(type)
  }
}