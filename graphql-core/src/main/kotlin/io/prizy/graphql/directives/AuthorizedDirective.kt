package io.prizy.graphql.directives

import com.expediagroup.graphql.generator.annotations.GraphQLDirective
import graphql.introspection.Introspection.DirectiveLocation.FIELD_DEFINITION

/**
 *  @author Nidhal Dogga
 *  @created 11/27/2021 11:54 AM
 *  @credits prizy.io
 *  All rights reserved
 */

@GraphQLDirective(
  name = AuthorizedDirective.NAME,
  locations = [FIELD_DEFINITION]
)
annotation class AuthorizedDirective(
  vararg val roles: String = []
) {

  companion object {
    const val NAME = "authorized"
  }
}
