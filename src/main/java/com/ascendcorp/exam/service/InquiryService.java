package com.ascendcorp.exam.service;

import com.ascendcorp.exam.exception.InvalidRequestException;
import com.ascendcorp.exam.model.InquiryServiceResultDTO;
import com.ascendcorp.exam.model.TransferResponse;
import com.ascendcorp.exam.proxy.BankProxyGateway;
import com.ascendcorp.exam.service.handler.ApproveResponseHandler;
import com.ascendcorp.exam.service.handler.InvalidDataResponseHandler;
import com.ascendcorp.exam.service.handler.TransactionErrorHandler;
import com.ascendcorp.exam.service.handler.UnknownResponseHandler;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServerException;

import java.util.Date;

public class InquiryService {

    @Autowired
    private BankProxyGateway bankProxyGateway;

    private final InquiryRequestValidator inquiryRequestValidator = new InquiryRequestValidator();
    private final ApproveResponseHandler approveHandler = new ApproveResponseHandler();
    private final InvalidDataResponseHandler invalidDataHandler = new InvalidDataResponseHandler();
    private final TransactionErrorHandler transactionErrorHandler = new TransactionErrorHandler();
    private final UnknownResponseHandler unknownResponseHandler = new UnknownResponseHandler();

    final static Logger log = Logger.getLogger(InquiryService.class);

    public InquiryServiceResultDTO inquiry(String transactionId,
                                           Date tranDateTime,
                                           String channel,
                                           String locationCode,
                                           String bankCode,
                                           String bankNumber,
                                           double amount,
                                           String reference1,
                                           String reference2,
                                           String firstName,
                                           String lastName)
    {
        InquiryServiceResultDTO respDTO = null;
        try {
            log.info("validate request parameters.");
            inquiryRequestValidator.validate(transactionId, tranDateTime, channel, bankCode, bankNumber, amount);

            log.info("call bank web service");
            TransferResponse response = bankProxyGateway.requestTransfer(transactionId, tranDateTime, channel,
                    bankCode, bankNumber, amount, reference1, reference2);

            log.info("check bank response code");
            if (response != null) {
                log.debug("found response code");

                if(approveHandler.supports(response.getResponseCode())) {

                    // Bank Response code = "approved"
                    log.info("Using ApproveResponseHandler for 'approved' case.");
                    // Call handle() method and put response from Bank Web Service in Parameter
                    respDTO = approveHandler.handle(response);

                } else if(invalidDataHandler.supports(response.getResponseCode())) {

                    // Bank Response code = "invalid_data"
                    log.info("Using InvalidDataResponseHandler for 'invalid_data' case.");

                    // Call handle() method and put response from Bank Web Service in Parameter
                    respDTO = invalidDataHandler.handle(response);

                } else if(transactionErrorHandler.supports(response.getResponseCode())) {
                    // Bank Response code = "transaction_error"
                    log.info("Using TransactionErrorHandler for 'transaction_error' case.");

                    // Call handle() method and put response from Bank Web Service in Parameter
                    respDTO = transactionErrorHandler.handle(response);

                } else if(unknownResponseHandler.supports(response.getResponseCode())) {
                    // Bank Response code = "unknown"
                    log.info("Using UnknownResponseHandler for 'unknown' case.");

                    // Call handle() method and put response from Bank Web Service in Parameter
                    respDTO = unknownResponseHandler.handle(response);

                } else
                    // bank code not support
                    throw new Exception("Unsupported Error Reason Code");
            } else
                // no response from bank
                throw new Exception("Unable to inquiry from service.");

        } catch(InvalidRequestException ire) {
            log.error("Invalid request from validation: " + ire.getMessage());
            if(respDTO == null) {
                respDTO = new InquiryServiceResultDTO();
                respDTO.setReasonCode("500");
                respDTO.setReasonDesc("General Invalid Data");
            }

        } catch(WebServerException r) {
            // handle error from bank web service
            String faultString = r.getMessage();
            if(respDTO == null) {
                respDTO = new InquiryServiceResultDTO();
                if(faultString != null && (faultString.indexOf("java.net.SocketTimeoutException") > -1
                        || faultString.indexOf("Connection timed out") > -1 )) {
                    // bank timeout
                    respDTO.setReasonCode("503");
                    respDTO.setReasonDesc("Error timeout");
                } else {
                    // bank general error
                    respDTO.setReasonCode("504");
                    respDTO.setReasonDesc("Internal Application Error");
                }
            }
        }
        catch(Exception e) {
            log.error("inquiry exception", e);
            if(respDTO == null || (respDTO != null && respDTO.getReasonCode() == null)) {
                respDTO = new InquiryServiceResultDTO();
                respDTO.setReasonCode("504");
                respDTO.setReasonDesc("Internal Application Error");
            }
        }
        return respDTO;
    }
}
