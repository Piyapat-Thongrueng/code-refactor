package com.ascendcorp.exam.service.handler;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InvalidDataResponseHandlerTest {

    @Test
    public void should_return400_when_descIsNull() {
        InvalidDataResponseHandler handler = new InvalidDataResponseHandler();
        TransferResponse bankResponse = new TransferResponse();
        bankResponse.setDescription(null);

        InquiryServiceResultDTO result = handler.handle(bankResponse);

        assertNotNull(result);
        assertEquals("400", result.getReasonCode());
        assertEquals("General Invalid Data", result.getReasonDesc());
    }

    @Test
    public void should_return400_when_descFormatIsShort() {
        InvalidDataResponseHandler handler = new InvalidDataResponseHandler();
        TransferResponse bankResponse = new TransferResponse();
        bankResponse.setDescription("Transaction Error");

        InquiryServiceResultDTO result = handler.handle(bankResponse);

        assertNotNull(result);
        assertEquals("400", result.getReasonCode());
        assertEquals("General Invalid Data", result.getReasonDesc());
    }

    @Test
    public void should_returnValid_descFormat(){
        InvalidDataResponseHandler handler = new InvalidDataResponseHandler();
        TransferResponse bankResponse = new TransferResponse();
        bankResponse.setDescription("100:1091:Data type is invalid.");

        InquiryServiceResultDTO result = handler.handle(bankResponse);

        assertNotNull(result);
        assertEquals("1091", result.getReasonCode());
        assertEquals("Data type is invalid.", result.getReasonDesc());
    }
}
