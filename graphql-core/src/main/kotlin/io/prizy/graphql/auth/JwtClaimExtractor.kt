package io.prizy.graphql.auth

import com.nimbusds.jose.shaded.json.JSONArray
import com.nimbusds.jose.shaded.json.JSONObject
import de.slub.urn.URN
import org.springframework.security.oauth2.jwt.Jwt
import java.util.UUID

/**
 *  @author Nidhal Dogga
 *  @created 11/17/2021 10:00 PM
 *  @credits prizy.io
 *  All rights reserved
 */

object JwtClaimExtractor {

  fun getPrincipal(jwt: Jwt) = Principal(
    UUID.fromString(jwt.subject),
    jwt.getClaimAsString("username"),
    jwt.getClaimAsString("email"),
    jwt.getClaimAsBoolean("email_verified") ?: true // TODO: Change later
  )

  fun getRoles(jwt: Jwt): Collection<String> {
    return jwt.getClaimAsStringList("roles")
  }

  fun getPermissions(jwt: Jwt): Collection<Permission> {
    return jwt
      .getClaim<JSONArray>("authorization")
      ?.map { it as JSONObject }
      ?.map { permission ->
        Permission(
          URN.rfc2141().parse(permission.getAsString("name")),
          (permission["scopes"] as JSONArray).map { scope -> scope as String }
        )
      }
      ?: emptyList()
  }
}
