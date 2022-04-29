package io.prizy.publicapi.graphql.scalars

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import java.util.UUID

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