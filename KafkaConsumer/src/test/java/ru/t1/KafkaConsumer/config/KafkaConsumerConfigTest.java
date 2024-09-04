
package ru.t1.KafkaConsumer.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.test.util.ReflectionTestUtils;
import ru.t1.KafkaConsumer.KafkaConsumerConfig;
import ru.t1.KafkaConsumer.model.Metric;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class KafkaConsumerConfigTest {

    @Mock
    private Metric metric;

    @InjectMocks
    private KafkaConsumerConfig kafkaConsumerConfig;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers = "localhost:9092";

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId = "test-group";


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        kafkaConsumerConfig = new KafkaConsumerConfig();
        ReflectionTestUtils.setField(kafkaConsumerConfig, "bootstrapServers", "localhost:9092");
        ReflectionTestUtils.setField(kafkaConsumerConfig, "groupId", "test-group");
    }


    @Test
    void testConsumerFactory() {
        ConsumerFactory<String, Metric> consumerFactory = kafkaConsumerConfig.consumerFactory();
        assertNotNull(consumerFactory);

        Map<String, Object> props = consumerFactory.getConfigurationProperties();
        assertEquals(bootstrapServers, props.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
        assertEquals(groupId, props.get(ConsumerConfig.GROUP_ID_CONFIG));
        assertEquals(StringDeserializer.class, props.get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG));
        assertEquals(JsonDeserializer.class.getName(), props.get(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS));
        assertEquals("ru.t1.KafkaProducer.model", props.get(JsonDeserializer.TRUSTED_PACKAGES));
    }

    @Test
    void testKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Metric> factory = kafkaConsumerConfig.kafkaListenerContainerFactory();
        assertNotNull(factory);
        assertNotNull(factory.getConsumerFactory());
    }
}
