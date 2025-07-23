package com.ascendcorp.exam.service.handler;

import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;

public class TransactionErrorHandler implements BankResponseHandler {

    private static final String bankResponseKeyword = "transaction_error";
    private static final String errorCode = "500";
    private static final String errorDesc = "General Transaction Error";


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

        if (replyDesc != null) {

            String[] respDesc = replyDesc.split(":");

            if (respDesc.length >= 2) {
                String subIdx1 = respDesc[0];
                String subIdx2 = respDesc[1];

                if ("98".equalsIgnoreCase(subIdx1)) {
                    respDTO.setReasonCode(subIdx1);
                    respDTO.setReasonDesc(subIdx2);
                } else {
                    if (respDesc.length >= 3) {
                        String subIdx3 = respDesc[2];
                        respDTO.setReasonCode(subIdx2);
                        respDTO.setReasonDesc(subIdx3);
                    } else {
                        respDTO.setReasonCode(subIdx1);
                        respDTO.setReasonDesc(subIdx2);
                    }
                }
            } else {
                respDTO.setReasonCode(errorCode);
                respDTO.setReasonDesc(errorDesc);
            }
        } else {
            respDTO.setReasonCode(errorCode);
            respDTO.setReasonDesc(errorDesc);
        }
        return respDTO;
    }
}
