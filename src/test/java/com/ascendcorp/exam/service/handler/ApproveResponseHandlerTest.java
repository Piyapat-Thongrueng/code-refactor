package com.ascendcorp.exam.service.handler;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ApproveResponseHandlerTest {

    @Test
    public void should_run_test_normally() {
        ApproveResponseHandler handler = new ApproveResponseHandler();

        TransferResponse bankResponse = new TransferResponse();
        bankResponse.setReferenceCode1("123");
        bankResponse.setReferenceCode2("321");
        bankResponse.setAmount("5000.75");
        bankResponse.setBankTransactionID("123456");
        bankResponse.setDescription("Transaction is successful.");

        InquiryServiceResultDTO result = handler.handle(bankResponse);

        assertNotNull(result, "result not null");

        assertEquals("200", result.getReasonCode());
        assertEquals("Transaction is successful.", result.getReasonDesc());
        assertEquals("Transaction is successful.", result.getAccountName());

        assertEquals("123", result.getRef_no1());
        assertEquals("321", result.getRef_no2());
        assertEquals("5000.75", result.getAmount());
        assertEquals("123456", result.getTranID());
    }
}
