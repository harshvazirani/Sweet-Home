package com.upgrad.BookingService.controllers;

import com.upgrad.BookingService.dto.BookingDTO;
import com.upgrad.BookingService.dto.ExceptionDTO;
import com.upgrad.BookingService.dto.PaymentDTO;
import com.upgrad.BookingService.entities.BookingInfoEntity;
import com.upgrad.BookingService.exceptions.BookingIdNotPresentException;
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
        BookingInfoEntity b = bookingService.acceptBookingDetails(bookingDTO);
        return new ResponseEntity(b, HttpStatus.CREATED);
    }

    @PostMapping(value = "/booking/{id}/transaction" , consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity bookingConfirmation(@RequestBody PaymentDTO paymentDTO, @PathVariable(name="id") int id){
        String paymentMode = paymentDTO.getPaymentMode();
        if(paymentMode.equals("CASH") || paymentMode.equals("UPI")) {
            try{
            BookingInfoEntity b = bookingService.acceptPaymentDetails(paymentDTO);
            return new ResponseEntity(b, HttpStatus.CREATED);}
            catch(BookingIdNotPresentException e){
                ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage(), 400);
                return new ResponseEntity(exceptionDTO, HttpStatus.BAD_REQUEST);
            }
        }
        else{
            ExceptionDTO exceptionDTO = new ExceptionDTO("Invalid mode of payment", 400);
            return new ResponseEntity(exceptionDTO, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value = "/booking/demo")
    public ResponseEntity demoMethod(){
        return new ResponseEntity("Hello", HttpStatus.OK);
    }

}
