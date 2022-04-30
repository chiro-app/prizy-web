package io.prizy.publicapi.application.properties

import com.nimbusds.jose.Algorithm
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.security.interfaces.RSAPublicKey

/**
 *  @author Nidhal Dogga
 *  @created 4/30/2022 9:39 PM
 */

@ConstructorBinding
@ConfigurationProperties("prizy.security.jwt")
data class JwtProperties(
  val keyId: String,
  val issuer: String,
  val audience: String,
  val algorithm: String,
  val publicKey: String,
  val privateKey: String,
  val tokenExpiration: Long = 3600
) {

  val rsaPublicKey: RSAPublicKey by lazy {
    RSAKey.parseFromPEMEncodedObjects(publicKey).toRSAKey().toRSAPublicKey()
  }

  val jwk: RSAKey by lazy {
    RSAKey.Builder(JWK.parseFromPEMEncodedObjects(publicKey).toRSAKey())
      .keyID(keyId)
      .algorithm(Algorithm.parse(algorithm))
      .keyUse(KeyUse.SIGNATURE)
      .privateKey(JWK.parseFromPEMEncodedObjects(privateKey).toRSAKey().toRSAPrivateKey())
      .build()
  }
}