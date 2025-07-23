package com.ascendcorp.exam.service.handler;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionErrorHandlerTest {

    @Test
    public void should_return500_when_descIsNull() {
        TransactionErrorHandler handler = new TransactionErrorHandler();
        TransferResponse bankResponse = new TransferResponse();
        bankResponse.setDescription(null);

        InquiryServiceResultDTO result = handler.handle(bankResponse);

        assertNotNull(result);
        assertEquals("500", result.getReasonCode());
        assertEquals("General Transaction Error", result.getReasonDesc());
    }

    @Test
    public void should_return500_when_descLengthLessThanTwo() {
        TransactionErrorHandler handler = new TransactionErrorHandler();
        TransferResponse bankResponse = new TransferResponse();
        bankResponse.setDescription("Just one part");

        InquiryServiceResultDTO result = handler.handle(bankResponse);

        assertNotNull(result);
        assertEquals("500", result.getReasonCode());
        assertEquals("General Transaction Error", result.getReasonDesc());
    }

    @Test
    public void should_return98_when_descIs98() {
        TransactionErrorHandler handler = new TransactionErrorHandler();
        TransferResponse bankResponse = new TransferResponse();
        bankResponse.setDescription("98:Transaction is error with code 98.");

        InquiryServiceResultDTO result = handler.handle(bankResponse);

        assertNotNull(result);
        assertEquals("98", result.getReasonCode());
        assertEquals("Transaction is error with code 98.", result.getReasonDesc());
    }

    @Test
    public void should_return_index1_and_index2_when_descLegnthIsThree() {
        TransactionErrorHandler handler = new TransactionErrorHandler();
        TransferResponse bankResponse = new TransferResponse();
        bankResponse.setDescription("100:1091:Transaction is error with code 1091.");

        InquiryServiceResultDTO result = handler.handle(bankResponse);

        assertNotNull(result);
        assertEquals("1091", result.getReasonCode());
        assertEquals("Transaction is error with code 1091.", result.getReasonDesc());
    }

    @Test
    public void should_return_index0_and_index1_when_descLengthIsTwo() {
        TransactionErrorHandler handler = new TransactionErrorHandler();
        TransferResponse bankResponse = new TransferResponse();
        bankResponse.setDescription("1092:Transaction is error with code 1092.");

        InquiryServiceResultDTO result = handler.handle(bankResponse);

        assertNotNull(result);
        assertEquals("1092", result.getReasonCode());
        assertEquals("Transaction is error with code 1092.", result.getReasonDesc());
    }
}
