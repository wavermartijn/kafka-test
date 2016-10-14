package org.wavermartijn;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

@Service
@Slf4j
public class KafkaProducerService {

  private final String topic = "";
  private Properties props = null;
  Producer<String, String> producer = null;
  KafkaConsumer consumer = null;

  public KafkaProducerService() {

  }

  public void produceMessage() throws IOException {
    if (props == null) {

      props = new Properties();
      props.load(KafkaProducerService.class.getResourceAsStream("/producer.props"));
      //      props.put("zookeeper.connect","172.17.0.1:2181");
      //      props.put("bootstrap.servers", "172.17.0.1:9092");
      //
      //      props.put("acks", "all");
      //      props.put("retries", 1);
      //      props.put("auto.commit.interval.ms", "1000");
      //      props.put("batch.size", 10);
      //      props.put("linger.ms", 5);
      //      props.put("buffer.memory", 33554432);
      //      props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
      //      props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

      producer = new KafkaProducer<>(props);

    }
    log.info("going to send");
    for (int i = 0; i < 100; i++) {
      producer.send(new ProducerRecord<String, String>("martijn1", Integer.toString(i), "martijn is " + i));
    }
    log.info("send one");
    producer.flush();

  }
}
