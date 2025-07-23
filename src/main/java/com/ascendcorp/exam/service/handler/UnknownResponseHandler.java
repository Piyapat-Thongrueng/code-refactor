package com.ascendcorp.exam.service.handler;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;

public class UnknownResponseHandler implements BankResponseHandler {

    private static final String bankResponseKeyword = "unknown";
    private static final String errorCode = "501";
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

        String replyDesc = bankResponse.getDescription();

        if (replyDesc == null || replyDesc.trim().isEmpty()) {
            respDTO.setReasonCode(errorCode);
            respDTO.setReasonDesc(errorDesc);
            return respDTO;
        }

        String[] respDesc = replyDesc.split(":");
        if (respDesc.length >= 2) {
            respDTO.setReasonCode(respDesc[0]);
            respDTO.setReasonDesc(respDesc[1]);
            if (respDTO.getReasonDesc() == null || respDTO.getReasonDesc().trim().isEmpty()) {
                respDTO.setReasonDesc(errorDesc);
            }
        } else {
                respDTO.setReasonCode(errorCode);
                respDTO.setReasonDesc(errorDesc);
        }
        return respDTO;
    }
}
