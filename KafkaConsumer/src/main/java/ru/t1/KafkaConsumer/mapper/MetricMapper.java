package ru.t1.KafkaConsumer.mapper;

import ru.t1.KafkaConsumer.model.Metric;
import ru.t1.KafkaConsumer.model.MetricDto;

public class MetricMapper {
    public MetricDto toDto(Metric metric) {
        if (metric == null) {
            return null;
        }

        MetricDto dto = new MetricDto();
        dto.setAppName(metric.getAppName());
        dto.setMetricName(metric.getMetricName());
        dto.setMetricValue(metric.getMetricValue());
        dto.setNumberOfMistakes(metric.getNumberOfMistakes());
        dto.setProcessTime(metric.getProcessTime());
        dto.setUsingMemory(metric.getUsingMemory());
        dto.setTimestamp(metric.getTimestamp());
        return dto;
    }

    public Metric toEntity(MetricDto metricDto) {
        if (metricDto == null) {
            return null;
        }

        Metric metric = new Metric();
        metric.setAppName(metricDto.getAppName());
        metric.setMetricName(metricDto.getMetricName());
        metric.setMetricValue(metricDto.getMetricValue());
        metric.setNumberOfMistakes(metricDto.getNumberOfMistakes());
        metric.setProcessTime(metricDto.getProcessTime());
        metric.setUsingMemory(metricDto.getUsingMemory());
        metric.setTimestamp(metricDto.getTimestamp());
        return metric;
    }
}
