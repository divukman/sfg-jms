package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage() {
        final HelloWorldMessage message = HelloWorldMessage
                .builder()
                .message("Hello world from Hello Sender")
                .id(UUID.randomUUID())
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);
    }


    @Scheduled(fixedRate = 2000)
    public void sendReceiveMessage() throws JMSException {
        System.out.println("Sending a message....");

        final HelloWorldMessage message = HelloWorldMessage
                .builder()
                .message("Hello")
                .id(UUID.randomUUID())
                .build();

        Message receivedMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage = null;
                try {
                    helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                    helloMessage.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");
                }catch (JsonProcessingException e) {
                    throw new JMSException("Error creating a message");
                }
                return helloMessage;
            }
        });

        System.out.println(receivedMsg.getBody(String.class));
    }

}
