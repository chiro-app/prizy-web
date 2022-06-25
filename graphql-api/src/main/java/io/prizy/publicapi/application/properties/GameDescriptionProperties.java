package io.prizy.publicapi.application.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

/**
 * @author Nidhal Dogga
 * @created 06/05/2022 13:59
 */

@ConstructorBinding
@ConfigurationProperties(prefix = "prizy.game-description")
public record GameDescriptionProperties(
  String id,
  String title,
  String description,
  String usageInstructions,
  String iconAssetId,
  String coverAssetId
) {

}