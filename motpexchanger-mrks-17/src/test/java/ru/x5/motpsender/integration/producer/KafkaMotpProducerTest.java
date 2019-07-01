package ru.x5.motpsender.integration.producer;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ru.x5.motpsender.dao.MotpSender;
import ru.x5.motpsender.integration.consumer.KafkaMotpConsumer;

@RunWith(SpringRunner.class)
@ActiveProfiles("unit")
@TestPropertySource(locations = "classpath:application-unit.properties")
@SpringBootTest
public abstract class KafkaMotpProducerTest {

    @Autowired
    protected KafkaTemplate kafkaTemplate;

    @MockBean
    protected MotpSender motpSender;

    @Autowired
    protected KafkaMotpConsumer kafkaMotpConsumer;

}