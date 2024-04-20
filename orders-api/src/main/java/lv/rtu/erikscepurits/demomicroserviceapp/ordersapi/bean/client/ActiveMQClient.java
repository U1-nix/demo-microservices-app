package lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.model.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ActiveMQClient {

    private final JmsTemplate jmsTemplate;

    public ActiveMQClient(@Autowired JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public boolean sendMessage(String queueName, Object objectToSend) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(objectToSend);
            jmsTemplate.convertAndSend(queueName, jsonMessage);
            return true;
        } catch (JsonProcessingException e) {
            log.error("Error sending message", e);
            return false;
        }
    }
}
