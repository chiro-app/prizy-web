package io.prizy.adapters.user.persistence.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.vladmihalcea.hibernate.type.json.JsonType;
import io.prizy.domain.user.model.EmailSettings;
import io.prizy.domain.user.model.NotificationSettings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

/**
 * @author Nidhal Dogga
 * @created 5/3/2022 10:22 PM
 */


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "UserPreferences")
@Table(name = "user_preferences")
@TypeDef(
  name = "json",
  typeClass = JsonType.class
)
public class UserPreferencesEntity {

  @Id
  @Column(name = "id")
  private UUID userId;
  @Builder.Default
  @Type(type = "json")
  @Column(columnDefinition = "json")
  private NotificationSettings notifications = new NotificationSettings();
  @Type(type = "json")
  @Column(columnDefinition = "json")
  private EmailSettings emails = new EmailSettings();

}
