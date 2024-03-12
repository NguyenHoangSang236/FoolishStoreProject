package com.backend.core.usecase.service;

import com.backend.core.entity.invoice.model.InvoicesWithProducts;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CalculationService {
    double getTotalPriceOfSingleProduct(double price, double discount);

    double getTotalPriceFromProductsList(List<InvoicesWithProducts> invoiceProductsList);
}
