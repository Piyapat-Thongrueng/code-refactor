package com.ascendcorp.exam.service.handler;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UnknownResponseHandlerTest {

    @Test
    public void should_return501_when_descIsNull() {
        UnknownResponseHandler handler = new UnknownResponseHandler();
        TransferResponse bankResponse = new TransferResponse();
        bankResponse.setDescription(null);

        InquiryServiceResultDTO result = handler.handle(bankResponse);

        assertNotNull(result);
        assertEquals("501", result.getReasonCode());
        assertEquals("General Invalid Data", result.getReasonDesc());
    }

    @Test
    public void should_return_index0_and_index1_when_descIsMoreThanOrEqualTwo() {
        UnknownResponseHandler handler = new UnknownResponseHandler();
        TransferResponse bankResponse = new TransferResponse();
        bankResponse.setDescription("5001:Unknown error code 5001");

        InquiryServiceResultDTO result = handler.handle(bankResponse);

        assertNotNull(result);
        assertEquals("5001", result.getReasonCode());
        assertEquals("Unknown error code 5001", result.getReasonDesc());
    }

    @Test
    public void should_return_error_when_descIndexOneIsNull() {
        UnknownResponseHandler handler = new UnknownResponseHandler();
        TransferResponse bankResponse = new TransferResponse();
        bankResponse.setDescription("5002: ");

        InquiryServiceResultDTO result = handler.handle(bankResponse);

        assertNotNull(result);
        assertEquals("5002", result.getReasonCode());
        assertEquals("General Invalid Data", result.getReasonDesc());
    }

    @Test
    public void should_return_error501_when_descLengthIsOne() {
        UnknownResponseHandler handler = new UnknownResponseHandler();
        TransferResponse bankResponse = new TransferResponse();
        bankResponse.setDescription("unknown error");

        InquiryServiceResultDTO result = handler.handle(bankResponse);

        assertNotNull(result);
        assertEquals("501", result.getReasonCode());
        assertEquals("General Invalid Data", result.getReasonDesc());
    }




}
