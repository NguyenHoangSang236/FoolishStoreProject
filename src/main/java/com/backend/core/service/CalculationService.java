package com.backend.core.service;

import com.backend.core.entity.dto.InvoicesWithProducts;
import com.backend.core.entity.interfaces.PurchaseCalculation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CalculationService {
    public double getTotalPriceOfSingleProduct(PurchaseCalculation productItem, double price, double discount);

    public double getTotalPriceFromProductsList(PurchaseCalculation productItem, List<InvoicesWithProducts> invoiceProductsList);
}
