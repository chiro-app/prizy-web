package io.prizy.publicapi.graphql.scalars;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import lombok.experimental.UtilityClass;

import java.time.Instant;

/**
 * @author Nidhal Dogga
 * @created 6/24/2022 9:19 PM
 */


@UtilityClass
public class InstantScalar {

  public GraphQLScalarType SCALAR = GraphQLScalarType.newScalar()
    .name("DateTime")
    .description("Timezoned date and time")
    .coercing(new InstantCoercing())
    .build();

  private class InstantCoercing implements Coercing<Instant, String> {

    @Override
    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
      if (dataFetcherResult instanceof Instant instant) {
        return instant.toString();
      }
      throw new CoercingSerializeException("Cannot serialize: " + dataFetcherResult);
    }

    @Override
    public Instant parseValue(Object input) throws CoercingParseValueException {
      return Instant.parse(input.toString());
    }

    @Override
    public Instant parseLiteral(Object input) throws CoercingParseLiteralException {
      if (input instanceof StringValue stringValue) {
        return Instant.parse(stringValue.getValue());
      }
      throw new CoercingParseLiteralException("Cannot parse value: " + input);
    }
  }

}