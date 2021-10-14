package com.upgrad.BookingService.services;

import com.upgrad.BookingService.dao.BookingInfoDao;
import com.upgrad.BookingService.dto.BookingDTO;
import com.upgrad.BookingService.dto.PaymentDTO;
import com.upgrad.BookingService.entities.BookingInfoEntity;
import com.upgrad.BookingService.exceptions.BookingIdNotPresentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class BookingServiceImpl implements BookingService{
    @Autowired
    private BookingInfoDao bookingInfoDao;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${paymentService.url}")
    private String paymentServiceUrl;

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

    public static LocalDate stringToLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }


    @Override
    public BookingInfoEntity acceptBookingDetails(BookingDTO bookingDTO) {
        String roomNumbers = getRandomNumbers(bookingDTO.getNumOfRooms());
        LocalDate fromDate = stringToLocalDate(bookingDTO.getFromDate());
        LocalDate toDate = stringToLocalDate(bookingDTO.getToDate());
        int numDays = (int) Duration.between(fromDate.atStartOfDay(), toDate.atStartOfDay()).toDays();
        int roomRate = 1000;
        int roomPrice = roomRate * bookingDTO.getNumOfRooms() * numDays;
        LocalDateTime bookedOn = LocalDateTime.now();

        BookingInfoEntity bookingInfo = new BookingInfoEntity();
        bookingInfo.setBookedOn(bookedOn);
        bookingInfo.setAadharNumber(bookingDTO.getAadharNumber());
        bookingInfo.setFromDate(fromDate);
        bookingInfo.setToDate(toDate);
        bookingInfo.setNumOfRooms(bookingDTO.getNumOfRooms());
        bookingInfo.setRoomNumbers(roomNumbers);
        bookingInfo.setRoomPrice(roomPrice);

        return  bookingInfoDao.save(bookingInfo);
    }

    @Override
    public BookingInfoEntity acceptPaymentDetails(PaymentDTO paymentDTO) throws BookingIdNotPresentException {
        Optional<BookingInfoEntity> b = bookingInfoDao.findById(paymentDTO.getBookingId());
        if(b.isEmpty()) throw new BookingIdNotPresentException("Invalid Booking Id");

        BookingInfoEntity bookingInfoEntity = b.get();

        Map<String, String> paymentUriMap = new HashMap<>();
        paymentUriMap.put("paymentMode", paymentDTO.getPaymentMode());
        paymentUriMap.put("bookingId", String.valueOf(paymentDTO.getBookingId()));
        paymentUriMap.put("upiId", paymentDTO.getUpiId());
        paymentUriMap.put("cardNumber", paymentDTO.getCardNumber());
        Integer transactionId = restTemplate.postForObject(paymentServiceUrl, paymentDTO, Integer.class);
        if(transactionId==null){
            return null;
        }

        bookingInfoEntity.setTransactionId(transactionId);
        return bookingInfoEntity;
    }
}
