package com.backend.core.serviceimpl;

import com.backend.core.entity.dto.InvoicesWithProducts;
import com.backend.core.entity.interfaces.PurchaseCalculation;
import com.backend.core.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CalculationServiceImpl implements CalculationService {
    @Autowired CalculationService calculationService;


    // calculate total price of a product after discount
    @Override
    public double getTotalPriceOfSingleProduct(PurchaseCalculation productItem, double price, double discount) {
        BigDecimal priceDec = new BigDecimal(price);
        BigDecimal discountDec = new BigDecimal(discount);
        BigDecimal result  = priceDec.multiply(discountDec.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));

        return price - result.doubleValue();
    }


    // calculate total price of a list of products from an invoice
    @Override
    public double getTotalPriceFromProductsList(PurchaseCalculation productItem, List<InvoicesWithProducts> invoiceProductsList) {
        double result = 0;

        for(InvoicesWithProducts itm: invoiceProductsList) {
            result += itm.getProductManagement().calculation(calculationService) * itm.getQuantity();
        }

        return result;
    }
}