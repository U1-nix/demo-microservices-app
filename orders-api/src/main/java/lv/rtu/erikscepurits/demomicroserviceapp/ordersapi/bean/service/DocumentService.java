package lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.bean.client.ActiveMQClient;
import lv.rtu.erikscepurits.demomicroserviceapp.ordersapi.model.CustomerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DocumentService {

    private final String DOCUMENT_GENERATION_QUEUE = "activemq.generate.documents.order";

    private final ActiveMQClient activeMQClient;

    public DocumentService(@Autowired ActiveMQClient activeMQClient) {
        this.activeMQClient = activeMQClient;
    }

    public boolean requestDocumentGeneration(CustomerOrder order) {
        log.info(String.format("Document generation requested for order id: %s", order.getId()));

        return activeMQClient.sendMessage(DOCUMENT_GENERATION_QUEUE, order);
    }

}
