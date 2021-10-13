package com.upgrad.BookingService.controllers;

import com.upgrad.BookingService.dto.BookingDTO;
import com.upgrad.BookingService.dto.PaymentDTO;
import com.upgrad.BookingService.entities.BookingInfoEntity;
import com.upgrad.BookingService.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/hotel")
public class Controller {

    @Autowired
    private BookingService bookingService;

    @PostMapping(value = "/booking" , consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity bookingDetails(@RequestBody BookingDTO bookingDTO){
        BookingInfoEntity b = new BookingInfoEntity();
        return new ResponseEntity(b, HttpStatus.CREATED);
    }

    @PostMapping(value = "/booking/{id}/transaction" , consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity bookingConfirmation(@RequestBody PaymentDTO paymentDTO, @PathVariable(name="id") int id){
        BookingInfoEntity b = new BookingInfoEntity();
        return new ResponseEntity(b, HttpStatus.CREATED);
    }
}
