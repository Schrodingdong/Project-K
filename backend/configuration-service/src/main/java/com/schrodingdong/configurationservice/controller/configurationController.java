package com.schrodingdong.configurationservice.controller;

import com.schrodingdong.configurationservice.amqp.AmqpService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "config")
@AllArgsConstructor
public class configurationController {
    private AmqpService amqpService;



    /**
     * Make sure that all the queues are empty
     * @return
     */
    @GetMapping("/verify-queues")
    public ResponseEntity<?> verifyQueues(){
        try{
            amqpService.verifyQueues();
            return ResponseEntity.ok().body("All Queues are empty");
        } catch (AmqpException e) {
            return ResponseEntity.internalServerError().body("Queues are not empty");
        }
    }
}
