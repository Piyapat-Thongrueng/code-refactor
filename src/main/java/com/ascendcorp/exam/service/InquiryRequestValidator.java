package com.ascendcorp.exam.service;

import com.ascendcorp.exam.exception.InvalidRequestException;

import java.util.Date;
import java.util.Objects;

public class InquiryRequestValidator {

    public void validate(String transactionId, Date tranDateTime, String channel,
                         String bankCode, String bankNumber, double amount) {

        if (transactionId == null || transactionId.trim().isEmpty()) {
            throw new InvalidRequestException("Transaction id is required!");
        }
        if (Objects.isNull(tranDateTime)) {
            throw new InvalidRequestException("Transaction DateTime is required!");
        }
        if (channel == null || channel.trim().isEmpty()) {
            throw new InvalidRequestException("Channel is required!");
        }
        if (bankCode == null || bankCode.trim().isEmpty()) {
            throw new InvalidRequestException("Bank Code is required!");
        }
        if (bankNumber == null || bankNumber.trim().isEmpty()) {
            throw new InvalidRequestException("Bank Number is required!");
        }
        if (amount <= 0) {
            throw new InvalidRequestException("Amount must more than zero!");
        }
    }
}
