package com.backend.core.entity.interfaces;

import com.backend.core.service.CalculationService;
import org.springframework.stereotype.Service;

@Service
public interface PurchaseCalculation {
    public double calculation(CalculationService calculationService);
}
