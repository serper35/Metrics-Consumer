package ru.t1.KafkaConsumer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class MetricDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String appName;
    private String metricName;
    private double metricValue;
    private int numberOfMistakes;
    private double processTime;
    private double usingMemory;
    private Timestamp timestamp;
}
