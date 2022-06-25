package io.prizy.publicapi.application.properties;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.security.interfaces.RSAPublicKey;

/**
 * @author Nidhal Dogga
 * @created 4/30/2022 9:39 PM
 */

@ConstructorBinding
@ConfigurationProperties("prizy.security.jwt")
public record JwtProperties(
  String keyId,
  String issuer,
  String audience,
  String algorithm,
  String publicKey,
  String privateKey,
  String tokenExpiration
) {

  @SneakyThrows
  public RSAPublicKey getRSAPublicKey() {
    return RSAKey.parseFromPEMEncodedObjects(publicKey).toRSAKey().toRSAPublicKey();
  }

  @SneakyThrows
  public RSAKey getJWK() {
    return new RSAKey.Builder(JWK.parseFromPEMEncodedObjects(publicKey).toRSAKey())
      .keyID(keyId)
      .algorithm(Algorithm.parse(algorithm))
      .keyUse(KeyUse.SIGNATURE)
      .privateKey(JWK.parseFromPEMEncodedObjects(privateKey).toRSAKey().toRSAPrivateKey())
      .build();
  }
}