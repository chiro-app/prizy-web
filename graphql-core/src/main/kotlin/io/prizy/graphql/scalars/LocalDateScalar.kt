package io.prizy.graphql.scalars

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType
import java.time.LocalDate

object LocalDateScalar {

  val graphqlScalarType: GraphQLScalarType = GraphQLScalarType.newScalar()
    .name("Date")
    .description("Date")
    .coercing(InstantCoercing)
    .build()

  object InstantCoercing : Coercing<LocalDate, String> {

    override fun serialize(dataFetcherResult: Any): String {
      when (dataFetcherResult) {
        is LocalDate -> return dataFetcherResult.toString()
        else -> throw CoercingSerializeException("Cannot serialize $dataFetcherResult")
      }
    }

    override fun parseValue(input: Any): LocalDate {
      return LocalDate.parse(input.toString())
    }

    override fun parseLiteral(input: Any): LocalDate {
      when (input) {
        is StringValue -> return LocalDate.parse(input.value)
        else -> throw CoercingParseLiteralException("Cannot parse value: $input")
      }
    }
  }
}