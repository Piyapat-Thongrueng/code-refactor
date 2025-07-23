package com.ascendcorp.exam.service;

import com.ascendcorp.exam.exception.InvalidRequestException;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class InquiryRequestValidatorTest {

    @Test
    public void shouldThrow_exception_when_transactionIsNull() {
        InquiryRequestValidator inquiryRequestValidator = new InquiryRequestValidator();
        assertThrows(InvalidRequestException.class, () -> {
            inquiryRequestValidator.validate(null, new Date(), "Mobile", "004", "123456", 100.0);
        });
    }

    @Test
    public void shouldThrow_exception_when_transactionIsBlank() {
        InquiryRequestValidator inquiryRequestValidator = new InquiryRequestValidator();
        assertThrows(InvalidRequestException.class, () -> {
            inquiryRequestValidator.validate(" ", new Date(), "Mobile", "004", "123456", 100.0);
        });
    }

    @Test
    public void shouldThrow_exception_when_tranDateTimeIsNull() {
        InquiryRequestValidator inquiryRequestValidator = new InquiryRequestValidator();
        assertThrows(InvalidRequestException.class, () -> {
            inquiryRequestValidator.validate("12345", null, "Mobile", "004", "123456", 100.0);
        });
    }

    @Test
    public void shouldThrow_exception_when_channelIsNull() {
        InquiryRequestValidator inquiryRequestValidator = new InquiryRequestValidator();
        assertThrows(InvalidRequestException.class, () -> {
            inquiryRequestValidator.validate("12345", new Date(), null, "004", "123456", 100.0);
        });
    }

    @Test
    public void shouldThrow_exception_when_bankCodeIsNull() {
        InquiryRequestValidator inquiryRequestValidator = new InquiryRequestValidator();
        assertThrows(InvalidRequestException.class, () -> {
            inquiryRequestValidator.validate("12345", new Date(), "Moblie", null, "123456", 100.0);
        });
    }

    @Test
    public void shouldThrow_exception_when_bankNumberIsNull() {
        InquiryRequestValidator inquiryRequestValidator = new InquiryRequestValidator();
        assertThrows(InvalidRequestException.class, () -> {
            inquiryRequestValidator.validate("12345", new Date(), "Moblie", "004", null, 100.0);
        });
    }

    @Test
    public void shouldThrow_exception_when_amountIsZero() {
        InquiryRequestValidator inquiryRequestValidator = new InquiryRequestValidator();
        assertThrows(InvalidRequestException.class, () -> {
            inquiryRequestValidator.validate("12345", new Date(), "Moblie", "004", "12345", 0);
        });
    }

    @Test
    public void shouldThrow_exception_when_amountIsLessThanZero() {
        InquiryRequestValidator inquiryRequestValidator = new InquiryRequestValidator();
        assertThrows(InvalidRequestException.class, () -> {
            inquiryRequestValidator.validate("12345", new Date(), "Moblie", "004", "12345", -500);
        });
    }

    @Test
    public void inquiryRequestWorkNormally() {
        InquiryRequestValidator inquiryRequestValidator = new InquiryRequestValidator();
        inquiryRequestValidator.validate("12345", new Date(), "Moblie", "004", "12345", 500);
    }
}
