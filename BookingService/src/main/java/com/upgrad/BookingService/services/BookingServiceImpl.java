package com.upgrad.BookingService.services;

import com.upgrad.BookingService.dao.BookingInfoDao;
import com.upgrad.BookingService.dto.BookingDTO;
import com.upgrad.BookingService.dto.PaymentDTO;
import com.upgrad.BookingService.entities.BookingInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

@Service
public class BookingServiceImpl implements BookingService{
    @Autowired
    private BookingInfoDao bookingInfoDao;

    private final int roomRate = 1000;

    public static String getRandomNumbers(int count){
        Random rand = new Random();
        int upperBound = 100;
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder.append(String.valueOf(rand.nextInt(upperBound)));

        for (int i=1; i<count; i++){
            stringBuilder.append(',');
            stringBuilder.append(rand.nextInt(upperBound));
        }

        return stringBuilder.toString();
    }


    @Override
    public BookingInfoEntity acceptBookingDetails(BookingDTO bookingDTO) {
        String roomNumbers = getRandomNumbers(bookingDTO.getNumOfRooms());
        int numDays = (int) Duration.between(bookingDTO.getFromDate(), bookingDTO.getToDate()).toDays();
        int roomPrice = roomRate * bookingDTO.getNumOfRooms() * numDays;
        LocalDateTime bookedOn = LocalDateTime.now();

        BookingInfoEntity bookingInfo = new BookingInfoEntity();
        bookingInfo.setBookedOn(bookedOn);
        bookingInfo.setAadharNumber(bookingDTO.getAadharNumber());
        bookingInfo.setFromDate(bookingDTO.getFromDate());
        bookingInfo.setToDate(bookingDTO.getToDate());
        bookingInfo.setNumOfRooms(bookingDTO.getNumOfRooms());
        bookingInfo.setRoomNumbers(roomNumbers);
        bookingInfo.setRoomPrice(roomPrice);

        return  bookingInfoDao.save(bookingInfo);
    }

    @Override
    public BookingInfoEntity acceptPaymentDetails(PaymentDTO paymentDTO) {
        return null;
    }
}
