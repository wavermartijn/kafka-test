package org.wavermartijn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestController {


  @Autowired
  KafkaProducerService kafkaProducerService;

  @RequestMapping(name = "/martijn",method = RequestMethod.GET)
  public String testIt() throws IOException {
    System.out.println("ffffffffff");
    kafkaProducerService.produceMessage();
    return "done";
  }

}
