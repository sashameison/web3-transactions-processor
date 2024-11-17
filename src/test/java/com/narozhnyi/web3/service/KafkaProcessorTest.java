package com.narozhnyi.web3.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.data.domain.PageRequest.ofSize;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

import com.narozhnyi.web3.dto.kafka.TransactionEvent;
import com.narozhnyi.web3.repository.ElasticTransactionRepository;
import com.narozhnyi.web3.repository.TransactionRepository;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(
    partitions = 1,
    brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" },
    topics = "${spring.kafka.topic.name}")
class KafkaProcessorTest {

  @Value("${spring.kafka.topic.name}")
  private String topic;

  @Value("${spring.kafka.consumer.group-id}")
  private String group;

  @Autowired
  private KafkaProducer kafkaProducer;
  @Autowired
  private EmbeddedKafkaBroker broker;
  @Autowired
  private TransactionRepository transactionRepository;
  private Consumer<String, TransactionEvent> consumer;

  @MockBean
  private ElasticTransactionRepository elasticTransactionRepository;

  @BeforeEach
  final void init() {
    consumer = createConsumer();
  }

  @Test
  void shouldConsumerKafkaEventWhenMessageIsSendAndSaveTransactionToDB() {
    // given
    consumer.subscribe(List.of(
        topic
    ));
    var uuid = UUID.randomUUID().toString();
    var data = TransactionEvent.builder()
        .hash(uuid)
        .blockNumber(BigInteger.ONE)
        .blockHash(uuid)
        .transferTo(uuid)
        .transferFrom(uuid)
        .build();

    //when
    kafkaProducer.sendEvent(data);

    //then
    ConsumerRecord<String, TransactionEvent> records =
        KafkaTestUtils.getSingleRecord(consumer, topic);

    var transaction = transactionRepository.findByBlockNumber(BigInteger.ONE, ofSize(1))
        .stream().findFirst();

    assertEquals(records.value(), data);

    assertTrue(transaction.isPresent());
    var transactionInDB = transaction.get();

    assertEquals(data.getBlockHash(), transactionInDB.getBlockHash());
    assertEquals(data.getHash(), transactionInDB.getHash());
    assertEquals(data.getTransferTo(), transactionInDB.getTransferTo());
    assertEquals(data.getTransferFrom(), transactionInDB.getTransferFrom());
    assertEquals(data.getBlockNumber(), transactionInDB.getBlockNumber());
  }


  private Consumer<String, TransactionEvent> createConsumer() {
    final var factory = new DefaultKafkaConsumerFactory<>(
        KafkaTestUtils.consumerProps(group, "true", broker),
        new StringDeserializer(),
        new JsonDeserializer<>(TransactionEvent.class)
    );

    return factory.createConsumer();
  }
}
