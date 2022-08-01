package com.garnerju.invoiceservice.repository;

import com.garnerju.invoiceservice.model.Tax;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@RefreshScope
public interface TaxRepository extends JpaRepository<Tax, String> {
}
