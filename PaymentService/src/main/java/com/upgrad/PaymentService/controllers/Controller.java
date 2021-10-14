package com.upgrad.PaymentService.controllers;

import com.upgrad.PaymentService.dto.PaymentDTO;
import com.upgrad.PaymentService.dto.TransactionDTO;
import com.upgrad.PaymentService.entities.TransactionDetailsEntity;
import com.upgrad.PaymentService.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payment")
public class Controller {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/transaction" , consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transactionConfirmation(@RequestBody PaymentDTO paymentDTO){
        int transactionId = paymentService.acceptPaymentDetails(paymentDTO);
        return new ResponseEntity(transactionId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/transaction/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity transactionDetails(@PathVariable(name="id") int id){
        TransactionDTO transaction = paymentService.getTransactionDetails(id);
        return  new ResponseEntity(transaction, HttpStatus.OK);
    }
}
