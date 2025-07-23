package com.ascendcorp.exam.service.handler;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;

public class InvalidDataResponseHandler implements BankResponseHandler {

    private static final String bankResponseKeyword = "invalid_data";
    private static final String errorCode = "400";
    private static final String errorDesc = "General Invalid Data";


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

        // Bank Response code = "invalid_data"
        String replyDesc = bankResponse.getDescription();

        if (replyDesc == null || replyDesc.trim().isEmpty()) {
            respDTO.setReasonCode(errorCode);
            respDTO.setReasonDesc(errorDesc);
            return respDTO;
        }

        String[] respDesc = replyDesc.split(":");
        if (respDesc.length >= 3) {
            respDTO.setReasonCode(respDesc[1]);
            respDTO.setReasonDesc(respDesc[2]);
        } else {
            respDTO.setReasonCode(errorCode);
            respDTO.setReasonDesc(errorDesc);
        }

        return respDTO;
    }
}
