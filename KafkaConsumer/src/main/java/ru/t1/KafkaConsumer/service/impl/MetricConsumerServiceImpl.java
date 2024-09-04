package ru.t1.KafkaConsumer.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.t1.KafkaConsumer.mapper.MetricMapper;
import ru.t1.KafkaConsumer.model.Metric;
import ru.t1.KafkaConsumer.model.MetricDto;
import ru.t1.KafkaConsumer.repository.MetricConsumerRepository;
import ru.t1.KafkaConsumer.service.MetricConsumerService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MetricConsumerServiceImpl implements MetricConsumerService {
    private final MetricConsumerRepository metricRepository;
    private final MetricMapper metricMapper = new MetricMapper();

    public MetricConsumerServiceImpl(MetricConsumerRepository metricRepository) {
        this.metricRepository = metricRepository;
    }

    public void saveMetric(Metric metric) {
        try {
            log.info("Saving metric: {}", metric);
            MetricDto metricDto = metricMapper.toDto(metric);
            metricRepository.save(metricDto);
            log.info("Metric saved successfully");
        } catch (Exception e) {
            log.error("Error saving metric: {}", metric, e);
            throw new RuntimeException("Failed to save metric", e);
        }
    }

    public List<Metric> getAllMetrics() {
        try {
            log.info("Getting all metrics");
            List<MetricDto> metricDtoList = metricRepository.findAll();
            List<Metric> metrics = metricDtoList.stream()
                    .map(metricMapper::toEntity)
                    .collect(Collectors.toList());
            log.info("Getting {} metrics", metrics.size());
            return metrics;
        } catch (Exception e) {
            log.error("Error getting all metrics", e);
            throw new RuntimeException("Failed to get metrics", e);
        }
    }

    @Override
    public Metric getMetricsById(long id) {
        log.info("Fetching metric by ID: {}", id);
        MetricDto metricDto = metricRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Metric not found with ID: " + id));

        return metricMapper.toEntity(metricDto);
    }
}
