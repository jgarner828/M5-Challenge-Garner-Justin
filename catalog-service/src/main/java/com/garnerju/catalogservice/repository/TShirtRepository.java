package com.garnerju.catalogservice.repository;

import com.garnerju.catalogservice.models.TShirt;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RefreshScope
public interface TShirtRepository extends JpaRepository<TShirt, Long> {
    List<TShirt> findAllByColor(String color);
    List<TShirt> findAllBySize(String size);
}

