package ru.t1.KafkaConsumer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.t1.KafkaConsumer.model.Metric;

@Service
public class MetricListener {
    private final MetricConsumerService metricConsumerService;

    public MetricListener(MetricConsumerService metricConsumerService) {
        this.metricConsumerService = metricConsumerService;
    }

    @KafkaListener(topics = "metrics-topic", groupId = "myGroup")
    public void listen(Metric metric) {
        metricConsumerService.saveMetric(metric);
    }
}
