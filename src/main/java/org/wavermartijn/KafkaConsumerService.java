package org.wavermartijn;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Properties;

@Slf4j
@Service
public class KafkaConsumerService implements InitializingBean{

  KafkaConsumer kafkaConsumer = null;

  @Scheduled(fixedRate = 10_000)
  public void readMessages(){
    log.info("reading messages");
    ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
    for (ConsumerRecord<String, String> record : records)
      log.info("offset = "+record.offset()+", key = "+record.key()+", value = "+record.value());
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092");
    props.put("group.id", "test");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "1000");
    props.put("session.timeout.ms", "30000");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    kafkaConsumer = new KafkaConsumer(props);
    kafkaConsumer.subscribe(Arrays.asList("martijn1"));
  }
}
