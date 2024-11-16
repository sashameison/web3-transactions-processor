package com.narozhnyi.web3.configuration;

import static org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG;

import java.util.HashMap;
import java.util.Map;

import com.narozhnyi.web3.dto.kafka.TransactionEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;

@Configuration
public class KafkaConfiguration {

  @Value("${spring.kafka.topic.name}")
  private String topic;

  @Bean("batchKafkaListener")
  public ConcurrentKafkaListenerContainerFactory<String, TransactionEvent> batchKafkaListener(
      ConsumerFactory<String, TransactionEvent> consumerFactory) {
    ConcurrentKafkaListenerContainerFactory<String, TransactionEvent> factory
        = new ConcurrentKafkaListenerContainerFactory<>();

    Map<String, Object> configProps = new HashMap<>();
    configProps.put(MAX_POLL_RECORDS_CONFIG, "500");
    consumerFactory.updateConfigs(configProps);
    factory.setConcurrency(1);
    factory.setConsumerFactory(consumerFactory);
    factory.getContainerProperties().setPollTimeout(3000);
    factory.setBatchListener(true);
    return factory;
  }

  @Bean
  public NewTopic topic() {
    return TopicBuilder.name(topic).build();
  }
}
