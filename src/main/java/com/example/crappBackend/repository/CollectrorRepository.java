package com.example.crappBackend.repository;

import com.example.crappBackend.model.Collector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectrorRepository extends JpaRepository<Collector, Long> {
}
