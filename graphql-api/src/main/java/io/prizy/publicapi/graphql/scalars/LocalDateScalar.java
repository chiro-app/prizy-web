package io.prizy.publicapi.graphql.scalars;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

/**
 * @author Nidhal Dogga
 * @created 6/24/2022 9:21 PM
 */


@UtilityClass
public class LocalDateScalar {

  public GraphQLScalarType SCALAR = GraphQLScalarType.newScalar()
    .name("Date")
    .description("Date")
    .coercing(new InstantCoercing())
    .build();

  private class InstantCoercing implements Coercing<LocalDate, String> {

    @Override
    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
      if (dataFetcherResult instanceof LocalDate instant) {
        return instant.toString();
      }
      throw new CoercingSerializeException("Cannot serialize: " + dataFetcherResult);
    }

    @Override
    public LocalDate parseValue(Object input) throws CoercingParseValueException {
      return LocalDate.parse(input.toString());
    }

    @Override
    public LocalDate parseLiteral(Object input) throws CoercingParseLiteralException {
      if (input instanceof StringValue stringValue) {
        return LocalDate.parse(stringValue.getValue());
      }
      throw new CoercingParseLiteralException("Cannot parse value: " + input);
    }
  }

}