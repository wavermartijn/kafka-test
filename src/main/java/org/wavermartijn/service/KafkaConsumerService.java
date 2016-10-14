package org.wavermartijn.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

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
    for (ConsumerRecord<String, String> record : records) {
      log.info(new Date() + " offset = " + record.offset() + ", key = " + record.key() + ", value = " + record.value());
    }
  }

  @PostConstruct
  public void initializeKafkaConsumer() throws Exception {
    Properties consumerProperties = new Properties();
    consumerProperties.load(KafkaProducerService.class.getResourceAsStream("/consumer.props"));

    kafkaConsumer = new KafkaConsumer(consumerProperties);
    kafkaConsumer.subscribe(Arrays.asList(consumingTopicName));
  }
}
