package com.ascendcorp.exam.service.handler;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;

public interface BankResponseHandler {

    boolean supports (String responseCode);

    InquiryServiceResultDTO handle (TransferResponse bankResponse);
}
