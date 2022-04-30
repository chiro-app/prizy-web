package io.prizy.graphql.directives

import com.expediagroup.graphql.generator.annotations.GraphQLDirective
import graphql.introspection.Introspection.DirectiveLocation.ARGUMENT_DEFINITION
import graphql.introspection.Introspection.DirectiveLocation.FIELD_DEFINITION
import graphql.introspection.Introspection.DirectiveLocation.INPUT_FIELD_DEFINITION


/**
 *  @author Nidhal Dogga
 *  @created 11/27/2021 3:47 PM
 *  @credits prizy.io
 *  All rights reserved
 */


@GraphQLDirective(
  name = ResourceIdentifierDirective.NAME,
  locations = [
    FIELD_DEFINITION,
    ARGUMENT_DEFINITION,
    INPUT_FIELD_DEFINITION
  ]
)
annotation class ResourceIdentifierDirective {

  companion object {
    const val NAME = "resourceIdentifier"
  }

}
