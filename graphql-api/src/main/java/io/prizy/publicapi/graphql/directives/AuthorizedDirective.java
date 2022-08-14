package io.prizy.publicapi.graphql.directives;

import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

/**
 * @author Nidhal Dogga
 * @created 6/24/2022 8:54 PM
 */


public class AuthorizedDirective implements SchemaDirectiveWiring {

  public static final String NAME = "authorized";

  @Override
  public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
    return SchemaDirectiveWiring.super.onField(environment);
  }

}