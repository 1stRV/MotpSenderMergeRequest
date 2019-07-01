package ru.x5.motpsender.integration.consumer;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.listener.LoggingErrorHandler;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * Класс для обработки ошибок ru.x5.motpsender.integration.consumer.KafkaMotpConsumer
 * Планируется использование для логирования и отправки сообщений об ошибках
 */
@Log4j2
@Component
public class KafkaMotpListenerErrorHandler implements ErrorHandler, KafkaListenerErrorHandler {
    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception) {
        log.error("Error while processing: {}", ObjectUtils.nullSafeToString(message.getHeaders().get(KafkaHeaders.RECEIVED_MESSAGE_KEY)));
        throw exception;
    }

    @Override
    public void handle(Exception thrownException, ConsumerRecord<?, ?> data) {
        //todo: send to dead letter topic
        log.error("Error while processing message {} after max attempts", data.key().toString());
        log.info("Message {} forward to dead letter", data.key().toString());
        log.debug("Error message body:{}", data.value());
        throw new ListenerExecutionFailedException("handle error throwed");

    }

}
