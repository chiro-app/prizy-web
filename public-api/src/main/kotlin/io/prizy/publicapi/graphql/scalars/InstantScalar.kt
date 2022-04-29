package io.prizy.publicapi.graphql.scalars

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import java.time.Instant

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