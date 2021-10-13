package com.upgrad.PaymentService.services;

import com.upgrad.PaymentService.dao.TransactionDetailsDao;
import com.upgrad.PaymentService.dto.PaymentDTO;
import com.upgrad.PaymentService.dto.TransactionDTO;
import com.upgrad.PaymentService.entities.TransactionDetailsEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    private TransactionDetailsDao transactionDetailsDao;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public int acceptPaymentDetails(PaymentDTO paymentDTO) {
        TransactionDetailsEntity transactionDetailsEntity = new TransactionDetailsEntity();
        transactionDetailsEntity.setBookingId(paymentDTO.getBookingId());
        transactionDetailsEntity.setPaymentMode(paymentDTO.getPaymentMode());
        transactionDetailsEntity.setCardNumber(paymentDTO.getCardNumber());
        transactionDetailsEntity.setUpiId(paymentDTO.getUpiId());

        return transactionDetailsDao.save(transactionDetailsEntity).getTransactionId();
    }

    @Override
    public TransactionDTO getTransactionDetails(int transactionId) {
        TransactionDetailsEntity t = transactionDetailsDao.getById(transactionId);
        return modelMapper.map(t, TransactionDTO.class);
    }
}
