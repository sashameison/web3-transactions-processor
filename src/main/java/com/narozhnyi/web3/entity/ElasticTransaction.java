package com.narozhnyi.web3.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "transactions")
public class ElasticTransaction {

  @Id
  private String transactionId;

  @Field(type = FieldType.Long)
  private Long blockNumber;

  @Field(type = FieldType.Long)
  private Long transactionIndex;

  private String hash;
  private String transferFrom;
  private String transferTo;
  private String blockHash;
  private String transferValue;
}
