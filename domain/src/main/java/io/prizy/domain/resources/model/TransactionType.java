package io.prizy.domain.resources.model;

import java.util.function.BiFunction;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum TransactionType implements BiFunction<Long, Long, Long> {

  DEBIT((lhs, rhs) -> lhs - rhs),
  CREDIT(Long::sum),
  BONUS(Long::sum);

  private final BiFunction<Long, Long, Long> reducer;

  @Override
  public Long apply(Long lhs, Long rhs) {
    return reducer.apply(lhs, rhs);
  }

}
