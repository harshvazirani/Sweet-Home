package com.upgrad.PaymentService.services;

import com.upgrad.PaymentService.dao.TransactionDetailsDao;
import com.upgrad.PaymentService.dto.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    private TransactionDetailsDao transactionDetailsDao;

    @Override
    public int acceptPaymentDetails(PaymentDTO paymentDTO) {
        return 0;
    }

    @Override
    public TransactionDetailsDao getTransactionDetails(int transactionId) {
        return null;
    }
}
