package io.prizy.domain.resources.service;

import java.util.UUID;

import io.prizy.domain.contest.ports.ContestRepository;
import io.prizy.domain.contest.service.ContestSubscriptionService;
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
 * @created 08/06/2022 11:33
 */


@ExtendWith(MockitoExtension.class)
class ResourceBoostServiceTest {

  @Mock
  private ContestRepository contestRepository;

  @Mock
  private ContestSubscriptionService subscriptionService;

  @Mock
  private ResourceProperties resourceProperties;

  private ResourceBoostService boostService;

  @BeforeEach
  void setup() {
    Mockito
      .when(contestRepository.exists(any()))
      .thenReturn(true);
    Mockito
      .when(resourceProperties.livesBoostMultiplier())
      .thenReturn(2);
    Mockito
      .when(resourceProperties.diamondsBoostMultiplier())
      .thenReturn(25);
    Mockito
      .when(resourceProperties.maxBoostReferrals())
      .thenReturn(4);
    boostService = new ResourceBoostService(contestRepository, subscriptionService, resourceProperties);
  }

  @Test
  void shouldReturnZeroWhenNoReferrals() {
    // Given
    Mockito
      .when(subscriptionService.subscribedReferralsCount(any(), any()))
      .thenReturn(0);

    // When
    var boost = boostService.boost(UUID.randomUUID(), UUID.randomUUID());
    // Then
    assertThat(boost.diamonds()).isEqualTo(0);
    assertThat(boost.lives()).isEqualTo(0);
  }

  @Test
  void shouldReturnCorrectBoost() {
    // Given
    Mockito
      .when(subscriptionService.subscribedReferralsCount(any(), any()))
      .thenReturn(2);

    // When
    var boost = boostService.boost(UUID.randomUUID(), UUID.randomUUID());
    // Then
    assertThat(boost.diamonds()).isEqualTo(50);
    assertThat(boost.lives()).isEqualTo(4);

    // Given
    Mockito
      .when(subscriptionService.subscribedReferralsCount(any(), any()))
      .thenReturn(4);

    // When
    boost = boostService.boost(UUID.randomUUID(), UUID.randomUUID());
    // Then
    assertThat(boost.diamonds()).isEqualTo(100);
    assertThat(boost.lives()).isEqualTo(8);

    // Given
    Mockito
      .when(subscriptionService.subscribedReferralsCount(any(), any()))
      .thenReturn(8);

    // When
    boost = boostService.boost(UUID.randomUUID(), UUID.randomUUID());
    // Then
    assertThat(boost.diamonds()).isEqualTo(100);
    assertThat(boost.lives()).isEqualTo(8);
  }

}