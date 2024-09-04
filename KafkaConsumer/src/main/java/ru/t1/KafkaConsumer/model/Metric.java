package ru.t1.KafkaConsumer.model;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class Metric {
    private String appName;
    private String metricName;
    private double metricValue;
    private int numberOfMistakes;
    private double processTime;
    private double usingMemory;
    private Timestamp timestamp;
}
