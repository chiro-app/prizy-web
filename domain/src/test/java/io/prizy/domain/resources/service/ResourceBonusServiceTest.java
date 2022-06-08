package io.prizy.domain.resources.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.prizy.domain.contest.service.ContestSubscriptionService;
import io.prizy.domain.resources.model.ResourceBoost;
import io.prizy.domain.resources.ports.ResourceRepository;
import io.prizy.domain.resources.properties.ResourceProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

/**
 * @author Nidhal Dogga
 * @created 08/06/2022 10:25
 */

@ExtendWith(MockitoExtension.class)
class ResourceBonusServiceTest {

  @Mock
  private ResourceBoostService boostService;

  @Mock
  private ResourceRepository resourceRepository;

  @Mock
  private ContestSubscriptionService contestSubscriptionService;

  @Mock
  private ResourceProperties resourceProperties;

  private ResourceBonusService bonusService;

  @BeforeEach
  void setup() {
    Mockito
      .when(boostService.boost(any(), any()))
      .thenReturn(ResourceBoost.builder()
        .diamonds(0L)
        .lives(0)
        .build()
      );
    bonusService = new ResourceBonusService(resourceRepository, boostService, contestSubscriptionService,
      resourceProperties);
  }

  @Test
  void shouldCorrectInitialContestBonus() {
    Mockito
      .when(contestSubscriptionService.daysSinceContestSubscription(any(), any()))
      .thenReturn(Optional.of(0));
    Mockito
      .when(resourceProperties.dailyDiamondsBonus())
      .thenReturn(List.of(10, 20, 30, 40, 50));
    Mockito
      .when(resourceProperties.dailyLivesBonus())
      .thenReturn(List.of(1, 2, 3, 4, 5));

    var maybeBonus = bonusService.getAvailableContestBonus(UUID.randomUUID(), UUID.randomUUID());
    assertThat(maybeBonus).isNotEmpty();
    var bonus = maybeBonus.get();
    assertThat(bonus.diamonds()).isEqualTo(10);
    assertThat(bonus.lives()).isEqualTo(1);

    Mockito
      .when(boostService.boost(any(), any()))
      .thenReturn(ResourceBoost.builder()
        .diamonds(50L)
        .lives(2)
        .build()
      );

    maybeBonus = bonusService.getAvailableContestBonus(UUID.randomUUID(), UUID.randomUUID());
    assertThat(maybeBonus).isNotEmpty();
    bonus = maybeBonus.get();
    assertThat(bonus.diamonds()).isEqualTo(60);
    assertThat(bonus.lives()).isEqualTo(3);
  }

  @Test
  void shouldReturnEmptyContestBonusIfAlreadyClaimed() {
    // Given
    Mockito
      .when(contestSubscriptionService.daysSinceContestSubscription(any(), any()))
      .thenReturn(Optional.empty());

    // When
    var bonus = bonusService.getAvailableContestBonus(UUID.randomUUID(), UUID.randomUUID());
    // Then
    assertThat(bonus).isEmpty();
  }

  @Test
  void shouldReturnCorrectContestBonusDaysAfter() {
    // Given
    Mockito
      .when(contestSubscriptionService.daysSinceContestSubscription(any(), any()))
      .thenReturn(Optional.of(1));
    Mockito
      .when(resourceProperties.dailyDiamondsBonus())
      .thenReturn(List.of(0, 10, 20, 30, 40, 50));
    Mockito
      .when(resourceProperties.dailyLivesBonus())
      .thenReturn(List.of(0, 1, 2, 3, 4, 5));

    // When
    var maybeBonus = bonusService.getAvailableContestBonus(UUID.randomUUID(), UUID.randomUUID());
    // Then
    assertThat(maybeBonus).isNotEmpty();
    var bonus = maybeBonus.get();
    assertThat(bonus.diamonds()).isEqualTo(10);
    assertThat(bonus.lives()).isEqualTo(1);

    // Given
    Mockito
      .when(contestSubscriptionService.daysSinceContestSubscription(any(), any()))
      .thenReturn(Optional.of(2));

    // When
    maybeBonus = bonusService.getAvailableContestBonus(UUID.randomUUID(), UUID.randomUUID());
    // Then
    assertThat(maybeBonus).isNotEmpty();
    bonus = maybeBonus.get();
    assertThat(bonus.diamonds()).isEqualTo(20);
    assertThat(bonus.lives()).isEqualTo(2);

    // Given
    Mockito
      .when(contestSubscriptionService.daysSinceContestSubscription(any(), any()))
      .thenReturn(Optional.of(3));

    // When
    maybeBonus = bonusService.getAvailableContestBonus(UUID.randomUUID(), UUID.randomUUID());
    // Then
    assertThat(maybeBonus).isNotEmpty();
    bonus = maybeBonus.get();
    assertThat(bonus.diamonds()).isEqualTo(30);
    assertThat(bonus.lives()).isEqualTo(3);

    // Given
    Mockito
      .when(contestSubscriptionService.daysSinceContestSubscription(any(), any()))
      .thenReturn(Optional.of(22));

    // When
    maybeBonus = bonusService.getAvailableContestBonus(UUID.randomUUID(), UUID.randomUUID());
    // Then
    assertThat(maybeBonus).isNotEmpty();
    bonus = maybeBonus.get();
    assertThat(bonus.diamonds()).isEqualTo(50);
    assertThat(bonus.lives()).isEqualTo(5);
  }

}