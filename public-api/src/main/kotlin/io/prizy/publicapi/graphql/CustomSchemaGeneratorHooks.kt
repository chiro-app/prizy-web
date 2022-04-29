package io.prizy.publicapi.graphql

import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import graphql.schema.GraphQLType
import io.prizy.publicapi.graphql.scalars.InstantScalar
import io.prizy.publicapi.graphql.scalars.UUIDScalar
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.KType


@Component
class CustomSchemaGeneratorHooks : SchemaGeneratorHooks {

  override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
    UUID::class -> UUIDScalar.graphqlScalarType
    Instant::class -> InstantScalar.graphqlScalarType
    else -> super.willGenerateGraphQLType(type)
  }
}