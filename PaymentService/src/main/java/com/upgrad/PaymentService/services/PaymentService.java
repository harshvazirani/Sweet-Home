package com.upgrad.PaymentService.services;

import com.upgrad.PaymentService.dto.PaymentDTO;
import com.upgrad.PaymentService.dto.TransactionDTO;


public interface PaymentService {
    public int acceptPaymentDetails(PaymentDTO paymentDTO);

    public TransactionDTO getTransactionDetails(int transactionId);
}
