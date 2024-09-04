package ru.t1.KafkaConsumer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.t1.KafkaConsumer.mapper.MetricMapper;
import ru.t1.KafkaConsumer.model.Metric;
import ru.t1.KafkaConsumer.model.MetricDto;
import ru.t1.KafkaConsumer.repository.MetricConsumerRepository;
import ru.t1.KafkaConsumer.service.impl.MetricConsumerServiceImpl;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MetricConsumerServiceImplTest {

    @Mock
    private MetricConsumerRepository metricRepository;

    @InjectMocks
    private MetricConsumerServiceImpl metricService;

    private MetricMapper metricMapper = new MetricMapper();

    private Metric metric;
    private MetricDto metricDto;


    @BeforeEach
    public void setUp() {
        metric = new Metric();
        metric.setAppName("TestApp");
        metric.setMetricName("TestMetric");
        metric.setMetricValue(123.45);
        metric.setNumberOfMistakes(0);
        metric.setProcessTime(12.34);
        metric.setUsingMemory(256.78);
        metric.setTimestamp(new Timestamp(System.currentTimeMillis()));

        metricDto = metricMapper.toDto(metric);
    }

    @Test
    public void testSaveMetric() {
        when(metricRepository.save(metricDto)).thenReturn(metricDto);

        assertDoesNotThrow(() -> metricService.saveMetric(metric));

        verify(metricRepository, times(1)).save(metricDto);
    }

    @Test
    void testGetAllMetrics() {
        when(metricRepository.findAll()).thenReturn(Arrays.asList(metricDto));

        List<Metric> metrics = metricService.getAllMetrics();

        assertEquals(1, metrics.size());
        assertEquals(metric, metrics.get(0));

        verify(metricRepository, times(1)).findAll();
    }

    @Test
    void testGetMetricsById_Success() {
        when(metricRepository.findById(1L)).thenReturn(Optional.of(metricDto));

        Metric foundMetric = metricService.getMetricsById(1L);

        assertEquals(metric, foundMetric);

        verify(metricRepository, times(1)).findById(1L);
    }

    @Test
    void testGetMetricsById_NotFound() {
        when(metricRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> metricService.getMetricsById(1L));

        verify(metricRepository, times(1)).findById(1L);
    }
}
