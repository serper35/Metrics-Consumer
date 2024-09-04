package ru.t1.KafkaConsumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.t1.KafkaConsumer.model.Metric;
import ru.t1.KafkaConsumer.model.MetricDto;

@Repository
public interface MetricConsumerRepository extends JpaRepository<MetricDto, Long> {
}
