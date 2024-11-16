package com.narozhnyi.web3.dto.transaction;

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
public class TransactionDto {

  private String hash;
  private String from;
  private String to;
  private BigDecimal value;
  private BigInteger blockNumber;
  private String blockHash;
  private BigInteger transactionIndex;
}
