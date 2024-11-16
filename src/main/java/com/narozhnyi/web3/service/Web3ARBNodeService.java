package com.narozhnyi.web3.service;

import java.math.BigInteger;
import java.util.Optional;

import com.narozhnyi.web3.dto.kafka.TransactionEvent;
import com.narozhnyi.web3.dto.transaction.TransactionDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.utils.Convert;

@Slf4j
@Service
@RequiredArgsConstructor
public class Web3ARBNodeService {

  private final Web3j web3j;
  private final KafkaProducer kafkaProducer;
  private final TransactionService transactionService;

  public void subscribeToARBTransactions() {
    transactionService.getLatestProcessedTransaction()
            .ifPresentOrElse(this::processTransactionsFromBlock, this::processCurrentTransactions);
  }

  private void processTransactionsFromBlock(TransactionDto transactionDto) {
      web3j.replayPastAndFutureTransactionsFlowable(DefaultBlockParameter.valueOf(transactionDto.getBlockNumber()))
          .retry(3)
          .subscribe(
              transaction -> processTransactionFromBlock(transaction, transactionDto),
              error -> log.error("The error message is: " + error.getMessage()));
  }

  private void processCurrentTransactions() {
      web3j.transactionFlowable()
          .retry(3)
          .subscribe(this::processTransaction, error -> log.error("The error message is: " + error.getMessage()));
  }

  @SneakyThrows
  private void processTransactionFromBlock(Transaction transaction, TransactionDto transactionDto) {
    var currentBlock = new BigInteger(transaction.getBlockNumber().toString());
    var transactionIndex = new BigInteger(transaction.getTransactionIndex().toString());

    if (currentBlock.equals(transactionDto.getBlockNumber()) &&
        isTransactionInBlockNotProcessed(transactionIndex, transactionDto.getTransactionIndex())) {
      processTransaction(transaction);
    }

    if (isCurrentBlockHigher(currentBlock, transactionDto.getBlockNumber())) {
      processTransaction(transaction);
    }
  }

  private void processTransaction(Transaction transaction) {
    Optional.ofNullable(transaction)
        .map(this::buildTransactionEvent)
        .ifPresent(kafkaProducer::sendEvent);
  }
  private TransactionEvent buildTransactionEvent(Transaction transaction) {
    return TransactionEvent.builder()
        .hash(transaction.getHash())
        .transferFrom(transaction.getFrom())
        .transferTo(transaction.getTo())
        .transferValue(Convert.fromWei(transaction.getValue().toString(), Convert.Unit.WEI))
        .blockHash(transaction.getHash())
        .blockNumber(transaction.getBlockNumber())
        .transactionIndex(new BigInteger(transaction.getTransactionIndex().toString()))
        .build();
  }

  private boolean isCurrentBlockHigher(BigInteger currentBlock, BigInteger blockToCompare) {
    return currentBlock.compareTo(blockToCompare) > 0;
  }

  private boolean isTransactionInBlockNotProcessed(
      BigInteger transactionIndex, BigInteger transactionIndexToCompare) {
    return transactionIndex.compareTo(transactionIndexToCompare) > 0;
  }
}
