package io.prizy.publicapi.graphql.scalars;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * @author Nidhal Dogga
 * @created 6/24/2022 9:22 PM
 */


@UtilityClass
public class UUIDScalar {

  public GraphQLScalarType SCALAR = GraphQLScalarType.newScalar()
    .name("UUID")
    .description("UUID")
    .coercing(new InstantCoercing())
    .build();

  private class InstantCoercing implements Coercing<UUID, String> {

    @Override
    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
      if (dataFetcherResult instanceof UUID uuid) {
        return uuid.toString();
      }
      throw new CoercingSerializeException("Cannot serialize: " + dataFetcherResult);
    }

    @Override
    public UUID parseValue(Object input) throws CoercingParseValueException {
      return UUID.fromString(input.toString());
    }

    @Override
    public UUID parseLiteral(Object input) throws CoercingParseLiteralException {
      if (input instanceof StringValue stringValue) {
        return UUID.fromString(stringValue.getValue());
      }
      throw new CoercingParseLiteralException("Cannot parse value: " + input);
    }
  }

}