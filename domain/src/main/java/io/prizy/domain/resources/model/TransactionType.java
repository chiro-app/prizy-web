package io.prizy.domain.resources.model;

import java.util.function.BiFunction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {

  WITHDRAW((lhs, rhs) -> lhs - rhs),
  DEPOSIT(Long::sum),
  RECURRENT(Long::sum);

  private final BiFunction<Long, Long, Long> transactionFunction;

}
