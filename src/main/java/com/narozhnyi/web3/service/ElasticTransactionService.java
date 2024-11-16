package com.narozhnyi.web3.service;

import java.util.List;

import com.narozhnyi.web3.dto.elastic.ElasticTransactionDto;
import com.narozhnyi.web3.dto.kafka.TransactionEvent;
import com.narozhnyi.web3.mapper.TransactionMapper;
import com.narozhnyi.web3.repository.ElasticTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElasticTransactionService {

  private final ElasticTransactionRepository elasticTransactionRepository;
  private final TransactionMapper transactionMapper;

  public void saveAll(List<TransactionEvent> transactionEvents) {
    var elastics = transactionEvents.stream()
        .map(transactionMapper::toElasticTransaction)
        .toList();

    elasticTransactionRepository.saveAll(elastics);
  }

  public Page<ElasticTransactionDto> findBy(Long blockNumber, Pageable pageable) {
    return elasticTransactionRepository.findByBlockNumber(blockNumber, pageable)
        .map(transactionMapper::toElasticTransactionDto);
  }

}
