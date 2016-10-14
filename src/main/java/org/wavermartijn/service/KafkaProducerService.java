package org.wavermartijn.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

@Service
@Slf4j
public class KafkaProducerService {

  private final String topic = "";
  private Properties props = null;
  Producer<String, String> producer = null;

  @Value("${application.kafka.topic}")
  public String applicationTopic;


  @PostConstruct
  public void intitalizeKafkaProducer() throws IOException {
    props = new Properties();
    props.load(KafkaProducerService.class.getResourceAsStream("/producer.props"));
    producer = new KafkaProducer<>(props);
  }

  public void produceTestMessages() throws IOException {

    log.info("going to send some test messages");
    for (int i = 0; i < 100; i++) {
      producer.send(new ProducerRecord<String, String>(applicationTopic, Integer.toString(i), "martijn is " + i));
    }
    log.info("send one");
    producer.flush();
  }

  public void produceSpecificMessage(String messageContent){
    producer.send(new ProducerRecord<String, String>(applicationTopic,messageContent));
    producer.flush();
  }
}