package com.backend.core.usecase.statics;

public enum InvoiceEnum {
    SUCCESS,
    SHIPPING,
    FAILED,
    SHIPPER_CANCEL,
    CUSTOMER_CANCEL,
    ACCEPTANCE_WAITING,
    PACKING,
    FINISH_PACKING,
    PAYMENT_WAITING,
}