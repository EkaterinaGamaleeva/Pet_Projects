package com.api.AdvancedServiceWebFlux.repository;


import com.api.AdvancedServiceWebFlux.models.RequestLog;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface RequestLogRepository extends ReactiveCrudRepository<RequestLog, Long> {
}
