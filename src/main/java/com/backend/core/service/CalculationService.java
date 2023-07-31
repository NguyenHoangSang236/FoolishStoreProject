package com.backend.core.service;

import com.backend.core.entities.dto.invoice.InvoicesWithProducts;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CalculationService {
    public double getTotalPriceOfSingleProduct(double price, double discount);

    public double getTotalPriceFromProductsList(List<InvoicesWithProducts> invoiceProductsList);
}
