package ru.t1.KafkaConsumer.service;

import ru.t1.KafkaConsumer.model.Metric;

import java.util.List;

public interface MetricConsumerService {
    void saveMetric(Metric metric);
    List<Metric> getAllMetrics();
    Metric getMetricsById(long id);
}
