package io.prizy.graphql.scalars

import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingSerializeException
import graphql.schema.GraphQLScalarType

object LongScalar {

  val graphqlScalarType: GraphQLScalarType = GraphQLScalarType.newScalar()
    .name("Long")
    .description("Long integer")
    .coercing(LongCoercing)
    .build()

  object LongCoercing : Coercing<Long, String> {

    override fun serialize(dataFetcherResult: Any): String {
      when (dataFetcherResult) {
        is Long -> return dataFetcherResult.toString()
        else -> throw CoercingSerializeException("Not a valid Long")
      }
    }

    override fun parseValue(input: Any): Long {
      return input.toString().toLong()
    }

    override fun parseLiteral(input: Any): Long {
      when (input) {
        is StringValue -> return input.value.toLong()
        else -> throw CoercingParseLiteralException("Value is not a valid Long")
      }
    }
  }
}