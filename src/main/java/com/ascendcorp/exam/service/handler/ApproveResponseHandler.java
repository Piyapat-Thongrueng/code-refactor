package com.ascendcorp.exam.service.handler;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;

public class ApproveResponseHandler implements BankResponseHandler {

    private static final String bankResponseKeyword = "approved";

    @Override
    public boolean supports(String responseCode) {
        return bankResponseKeyword.equalsIgnoreCase(responseCode);
    }

    @Override
    public InquiryServiceResultDTO handle(TransferResponse bankResponse) {

        InquiryServiceResultDTO respDTO = new InquiryServiceResultDTO();
        respDTO.setRef_no1(bankResponse.getReferenceCode1());
        respDTO.setRef_no2(bankResponse.getReferenceCode2());
        respDTO.setAmount(bankResponse.getBalance());
        respDTO.setTranID(bankResponse.getBankTransactionID());

        // bank response code = approved
        respDTO.setReasonCode("200");
        respDTO.setReasonDesc(bankResponse.getDescription());
        respDTO.setAccountName(bankResponse.getDescription());

        return respDTO;
    }
}
