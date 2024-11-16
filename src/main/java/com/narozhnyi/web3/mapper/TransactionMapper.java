package com.narozhnyi.web3.mapper;

import com.narozhnyi.web3.dto.elastic.ElasticTransactionDto;
import com.narozhnyi.web3.dto.kafka.TransactionEvent;
import com.narozhnyi.web3.dto.transaction.TransactionDto;
import com.narozhnyi.web3.entity.ElasticTransaction;
import com.narozhnyi.web3.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

  Transaction toTransaction(TransactionEvent transactionEvent);

  @Mapping(target = "from", source = "transferFrom")
  @Mapping(target = "to", source = "transferTo")
  @Mapping(target = "value", source = "transferValue")
  TransactionDto toTransactionDto(Transaction transaction);

  ElasticTransactionDto toElasticTransactionDto(ElasticTransaction elasticTransactionDto);

  ElasticTransaction toElasticTransaction(TransactionEvent elasticTransactionDto);
}
