package io.prizy.graphql.auth

import de.slub.urn.URN

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 12:01 AM
 */

data class Authorizations(
  val roles: Collection<String>,
  val permissions: Collection<Permission>
) {

  fun hasRole(roles: Collection<String>): Boolean {
    return this.roles.containsAll(roles)
  }

  fun hasPermission(resourceType: String, resourceId: String, scope: String): Boolean {
    return permissions.any { permission ->
      permission.urn.namespaceIdentifier().toString() == resourceType &&
        permission.urn.namespaceSpecificString().unencoded() == resourceId &&
        permission.scopes.contains(scope)
    }
  }

}


data class Permission(
  val urn: URN,
  val scopes: Collection<String>
)