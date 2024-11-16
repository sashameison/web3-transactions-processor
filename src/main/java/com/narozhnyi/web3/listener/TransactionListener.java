package com.narozhnyi.web3.listener;

import java.util.ArrayList;
import java.util.List;

import com.narozhnyi.web3.dto.kafka.TransactionEvent;
import com.narozhnyi.web3.service.ElasticTransactionService;
import com.narozhnyi.web3.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionListener {

  private final TransactionService transactionService;
  private final ElasticTransactionService elasticTransactionService;
  private final List<TransactionEvent> receivedEvents = new ArrayList<>();

  @KafkaListener(
      topics = "${spring.kafka.topic.name}",
      batch = "true",
      containerFactory = "batchKafkaListener",
      groupId = "web3_eth_consumer_group")
  public void processTransactions(ConsumerRecords<String, TransactionEvent> records) {
    records.forEach(record -> receivedEvents.add(record.value()));

    log.info("Events: {}", receivedEvents.size());
    transactionService.saveAll(receivedEvents);
    elasticTransactionService.saveAll(receivedEvents);

    receivedEvents.clear();

  }
}
