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
      /*InputStream propsStream = KafkaProducerService.class.getResourceAsStream("/producer.props");
      props = new Properties();
      props.load(propsStream);
      props.put("value.deserializer", StringDeserializer.class.getName());
      props.put("zookeeper.connect","localhost:2181");
      props.put("broker.id","0");
      props.put("zk.connect","127.0.0.1:2181");
      props.put("bootstrap.servers","127.0.0.1:9092");
      props.put("broker.list","0:127.0.0.1:9092");
      //props.put(,);
      props.put("key.deserializer", StringDeserializer.class.getName());
      props.put("client.id", InetAddress.getLocalHost().getHostName());
      producer = new KafkaProducer(props);
      //consumer = new KafkaConsumer(props);
      */
      props = new Properties();
      props.put("zookeeper.connect","localhost:2181");
      props.put("bootstrap.servers", "localhost:9092");

      props.put("acks", "0");
      props.put("retries", 0);
      props.put("batch.size", 10);
      props.put("linger.ms", 5);
      props.put("buffer.memory", 33554432);
      props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
      props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

      producer = new KafkaProducer<>(props);


    }
    log.info("going to send");
    for(int i = 0; i < 100; i++) {
      producer.send(new ProducerRecord<String, String>("martijn1", Integer.toString(i), "martijn is "+i));
    }
    log.info("send one");
    producer.flush();
    producer.close();

   // producer.flush();
   // producer.close();
/*
    ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
    log.info("revords "+records);
    if (records!=null){
      log.info("records lengte = "+records.count());
    }
    for (ConsumerRecord<String, String> record : records) {
      Map<String, Object> data = new HashMap();
      data.put("partition", record.partition());
      data.put("offset", record.offset());
      data.put("value", record.value());
      System.out.println( ": " + data);
    }*/
  }
}
