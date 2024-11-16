package com.narozhnyi.web3.service;

import com.narozhnyi.web3.dto.kafka.TransactionEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
  private final KafkaTemplate<String, Object> kafkaTemplate;
  @Value("${spring.kafka.topic.name}")
  private String topic;

  public KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendEvent(TransactionEvent dto) {
    kafkaTemplate.send(topic, dto);
  }
}
