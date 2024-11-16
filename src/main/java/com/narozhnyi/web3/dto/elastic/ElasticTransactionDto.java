package com.narozhnyi.web3.dto.elastic;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(NON_NULL)
public class ElasticTransactionDto {

  private Long blockNumber;
  private Long transactionIndex;
  private String hash;
  private String transferFrom;
  private String transferTo;
  private String blockHash;
  private String transferValue;
}
