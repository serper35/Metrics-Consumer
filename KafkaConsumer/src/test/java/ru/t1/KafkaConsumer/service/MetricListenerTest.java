package ru.t1.KafkaConsumer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.test.context.EmbeddedKafka;
import ru.t1.KafkaConsumer.model.Metric;

import static org.mockito.Mockito.*;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"metrics-topic"})
public class MetricListenerTest {

    @Autowired
    private MetricListener metricListener;

    @MockBean
    private MetricConsumerService metricConsumerService;

    @Test
    void testListen() {
        Metric metric = new Metric();
        metric.setAppName("TestApp");

        metricListener.listen(metric);

        verify(metricConsumerService, times(1)).saveMetric(metric);
    }
}

