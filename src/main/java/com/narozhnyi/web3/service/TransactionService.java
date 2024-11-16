package com.narozhnyi.web3.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import com.narozhnyi.web3.dto.kafka.TransactionEvent;
import com.narozhnyi.web3.dto.transaction.TransactionDto;
import com.narozhnyi.web3.mapper.TransactionMapper;
import com.narozhnyi.web3.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final TransactionMapper transactionMapper;

  @Transactional(readOnly = true)
  public Page<TransactionDto> findAllBy(BigInteger blockNumber, Pageable pageable) {
    return transactionRepository.findByBlockNumber(blockNumber, pageable)
        .map(transactionMapper::toTransactionDto);
  }

  public Optional<TransactionDto> getLatestProcessedTransaction() {
    return transactionRepository.findLatestTransaction()
        .map(transactionMapper::toTransactionDto);
  }

  public void saveAll(List<TransactionEvent> transactionEvents) {
    var transactions = transactionEvents.stream()
        .map(transactionMapper::toTransaction)
        .toList();

    transactionRepository.saveAll(transactions);
  }
}
