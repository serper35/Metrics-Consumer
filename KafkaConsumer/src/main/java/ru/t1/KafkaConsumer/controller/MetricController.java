package ru.t1.KafkaConsumer.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.t1.KafkaConsumer.model.Metric;
import ru.t1.KafkaConsumer.service.MetricConsumerService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/consumer")
@Api(tags = "Metric Management")
@Slf4j
public class MetricController {
    private final MetricConsumerService metricConsumerService;

    public MetricController(MetricConsumerService metricConsumerService) {
        this.metricConsumerService = metricConsumerService;
    }


    @GetMapping
    @ApiOperation(value = "Get all metrics", notes = "Returns a list of all metrics")
    public ResponseEntity<List<Metric>> getAllMetrics() {
        log.info("Getting all metrics");
        List<Metric> metrics = metricConsumerService.getAllMetrics();
        log.info("Got {} metrics", metrics.size());
        return ResponseEntity.ok(metrics);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get metric by ID", notes = "Returns a metric by ID")
    public ResponseEntity<Metric> getMetricById(@PathVariable long id) {
        log.info("Getting metric with ID: {}", id);

        if (id <= 0) {
            log.warn("Invalid ID: {}", id);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid ID");
        }

        try {
            Metric metric = metricConsumerService.getMetricsById(id);
            log.info("Fetched metric: {}", metric);
            return ResponseEntity.ok(metric);
        } catch (NoSuchElementException e) {
            log.error("Metric not found with ID: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Metric not found", e);
        }
    }
}
