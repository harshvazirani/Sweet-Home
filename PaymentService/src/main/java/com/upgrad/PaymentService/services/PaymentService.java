package com.upgrad.PaymentService.services;

import com.upgrad.PaymentService.dao.TransactionDetailsDao;
import com.upgrad.PaymentService.dto.PaymentDTO;


public interface PaymentService {
    public int acceptPaymentDetails(PaymentDTO paymentDTO);

    public TransactionDetailsDao getTransactionDetails(int transactionId);
}
