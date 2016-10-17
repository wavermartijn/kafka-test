package org.wavermartijn.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KafkaConsumerService {

  KafkaConsumer kafkaConsumer = null;

  @Value("${application.kafka.topic}")
  String consumingTopicName;

  @Scheduled(fixedRate = 10_000)
  public void readMessages() {
    log.info("reading messages");
    ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
    kafkaConsumer.partitionsFor(consumingTopicName);
    List<ConsumerRecord<String, String>> collectedFilteredRecords = records.records(new TopicPartition(consumingTopicName, 0)).stream().filter(record -> record.value().indexOf("1") > -1).collect(Collectors.toList());
    for (ConsumerRecord<String, String> record : records) {

      log.info("--"+new Date() + " offset = " + record.offset() + ", key = " + record.key() + ", value = " + record.value());
    }
    log.info("filtered:");
    collectedFilteredRecords.stream().forEach(s -> {
      log.info("filtered "+s.key()+" -- "+s.value());
    });

  }

  @PostConstruct
  public void initializeKafkaConsumer() throws Exception {
    Properties consumerProperties = new Properties();
    consumerProperties.load(KafkaProducerService.class.getResourceAsStream("/consumer.props"));

    kafkaConsumer = new KafkaConsumer(consumerProperties);
    kafkaConsumer.subscribe(Arrays.asList(consumingTopicName));
  }
}
