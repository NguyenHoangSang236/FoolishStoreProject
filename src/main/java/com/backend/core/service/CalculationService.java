package com.backend.core.service;

import com.backend.core.entities.tableentity.InvoicesWithProducts;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CalculationService {
    double getTotalPriceOfSingleProduct(double price, double discount);

    double getTotalPriceFromProductsList(List<InvoicesWithProducts> invoiceProductsList);
}
