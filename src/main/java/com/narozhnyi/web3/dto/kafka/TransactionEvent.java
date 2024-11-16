package com.narozhnyi.web3.dto.kafka;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionEvent {

  private String hash;
  private String transferFrom;
  private String transferTo;
  private String blockHash;
  private BigDecimal transferValue;
  private BigInteger blockNumber;
  private BigInteger transactionIndex;
}
