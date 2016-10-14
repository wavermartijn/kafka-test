package org.wavermartijn;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
public class TestController {


  @Autowired
  KafkaProducerService kafkaProducerService;

  @RequestMapping(name = "/martijn",method = RequestMethod.GET)
  public String testIt() throws IOException {
    log.info("going to call the kafkaProducerService");
    kafkaProducerService.produceTestMessages();
    return "done";
  }

}
