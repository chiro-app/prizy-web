package io.prizy.publicapi.graphql.scalars;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import lombok.experimental.UtilityClass;

/**
 * @author Nidhal Dogga
 * @created 6/24/2022 9:21 PM
 */


@UtilityClass
public class LongScalar {

  public GraphQLScalarType SCALAR = GraphQLScalarType.newScalar()
    .name("Long")
    .description("Long integer")
    .coercing(new InstantCoercing())
    .build();

  private class InstantCoercing implements Coercing<Long, String> {

    @Override
    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
      if (dataFetcherResult instanceof Long aLong) {
        return aLong.toString();
      }
      throw new CoercingSerializeException("Cannot serialize: " + dataFetcherResult);
    }

    @Override
    public Long parseValue(Object input) throws CoercingParseValueException {
      return Long.valueOf(input.toString());
    }

    @Override
    public Long parseLiteral(Object input) throws CoercingParseLiteralException {
      if (input instanceof StringValue stringValue) {
        return Long.valueOf(stringValue.getValue());
      }
      throw new CoercingParseLiteralException("Cannot parse value: " + input);
    }
  }

}