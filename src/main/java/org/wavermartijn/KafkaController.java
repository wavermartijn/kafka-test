package org.wavermartijn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
public class KafkaController {


  @Autowired
  KafkaProducerService kafkaProducerService;

  @RequestMapping(path = "/test",method = RequestMethod.GET)
  public String testIt() throws IOException {
    log.info("going to call the kafkaProducerService");
    kafkaProducerService.produceTestMessages();
    return "done";
  }

  @RequestMapping(path="/specific",method = RequestMethod.GET)
  public String sendSpecificMessage(@RequestParam String messageToSend){
    log.info("going to send a specific message");
    kafkaProducerService.produceSpecificMessage(messageToSend);
    return "done send requested message: "+messageToSend;
  }

}
