package io.prizy.publicapi.application

import com.expediagroup.graphql.generator.hooks.SchemaGeneratorHooks
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Instant
import java.util.UUID
import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 *  @author Nidhal Dogga
 *  @created 4/24/2022 3:44 PM
 */

@Configuration
class GraphQLConfiguration {

  @Bean
  fun schemaGeneratorHooks(): SchemaGeneratorHooks = CustomSchemaGeneratorHooks()

  class CustomSchemaGeneratorHooks : SchemaGeneratorHooks {

    override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
      UUID::class -> UUIDScalar.graphqlScalarType
      Instant::class -> InstantScalar.graphqlScalarType
      else -> super.willGenerateGraphQLType(type)
    }
  }

  object InstantScalar {

    val graphqlScalarType: GraphQLScalarType = GraphQLScalarType.newScalar()
      .name("Instant")
      .description("Timezoned date and time")
      .coercing(InstantCoercing)
      .build()

    object InstantCoercing : Coercing<Instant, String> {

      override fun serialize(dataFetcherResult: Any): String {
        when (dataFetcherResult) {
          is Instant -> return dataFetcherResult.toString()
          else -> throw CoercingSerializeException("Cannot serialize $dataFetcherResult")
        }
      }

      override fun parseValue(input: Any): Instant {
        return Instant.parse(input.toString())
      }

      override fun parseLiteral(input: Any): Instant {
        when (input) {
          is StringValue -> return Instant.parse(input.value)
          else -> throw CoercingParseLiteralException("Cannot parse value: $input")
        }
      }
    }
  }

  object UUIDScalar {

    val graphqlScalarType: GraphQLScalarType = GraphQLScalarType.newScalar()
      .name("UUID")
      .description("UUID")
      .coercing(UUIDCoercing)
      .build()

    object UUIDCoercing : Coercing<UUID, String> {

      override fun serialize(dataFetcherResult: Any): String {
        when (dataFetcherResult) {
          is UUID -> return dataFetcherResult.toString()
          else -> throw CoercingSerializeException("Not a valid UUID")
        }
      }

      override fun parseValue(input: Any): UUID {
        return UUID.fromString(input.toString())
      }

      override fun parseLiteral(input: Any): UUID {
        when (input) {
          is StringValue -> return UUID.fromString(input.value)
          else -> throw CoercingParseLiteralException("Value is not a valid UUID")
        }
      }
    }
  }
}
