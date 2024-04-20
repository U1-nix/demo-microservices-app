package lv.rtu.erikscepurits.demomicroserviceapp.documentsgenerationapi.bean.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lv.rtu.erikscepurits.demomicroserviceapp.documentsgenerationapi.dto.DocumentGenerationRequest;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DocumentGenerationService {
    private final String DOCUMENT_GENERATION_QUEUE = "activemq.generate.documents.order";

    @JmsListener(destination = DOCUMENT_GENERATION_QUEUE)
    public void receiveMessage(String jsonMessage) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            DocumentGenerationRequest request = objectMapper.readValue(jsonMessage, DocumentGenerationRequest.class);
            log.info(String.format("%s: generating documents for the order id: %s",
                DOCUMENT_GENERATION_QUEUE, request.getId()));
        } catch (JsonProcessingException e) {
            log.error("Error deserializing JSON message", e);
        }
    }
}
