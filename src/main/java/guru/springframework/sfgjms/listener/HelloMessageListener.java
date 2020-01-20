package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloMessageListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void listen(final @Payload HelloWorldMessage helloWorldMessage,
                       final @Headers MessageHeaders messageHeaders,
                       final Message message) {
        System.out.println(helloWorldMessage.toString());
    }

    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
    public void listenSendAndReceiveQueue(final @Payload HelloWorldMessage helloWorldMessage,
                       final @Headers MessageHeaders messageHeaders,
                       final Message message) throws JMSException {
        final HelloWorldMessage payload = HelloWorldMessage
                .builder()
                .message("World")
                .id(UUID.randomUUID())
                .build();
      jmsTemplate.convertAndSend(message.getJMSReplyTo(), payload);
    }

}
