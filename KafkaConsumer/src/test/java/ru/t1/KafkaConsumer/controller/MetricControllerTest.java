package ru.t1.KafkaConsumer.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.t1.KafkaConsumer.model.Metric;
import ru.t1.KafkaConsumer.service.impl.MetricConsumerServiceImpl;

import java.util.Collections;
import java.util.NoSuchElementException;

class MetricControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MetricConsumerServiceImpl metricConsumerService;

    @InjectMocks
    private MetricController metricController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(metricController).build();
    }

    @Test
    void testGetAllMetrics() throws Exception {
        when(metricConsumerService.getAllMetrics()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/consumer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void testGetMetricById_Success() throws Exception {
        Metric metric = new Metric();
        metric.setMetricName("Test");
        when(metricConsumerService.getMetricsById(1L)).thenReturn(metric);

        mockMvc.perform(get("/consumer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetMetricById_NotFound() throws Exception {
        when(metricConsumerService.getMetricsById(1L)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/consumer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetMetricById_InvalidId() throws Exception {
        mockMvc.perform(get("/consumer/-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}

