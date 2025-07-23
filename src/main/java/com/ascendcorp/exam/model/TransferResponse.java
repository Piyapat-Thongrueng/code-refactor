package com.ascendcorp.exam.model;

public class TransferResponse {


    private String responseCode;
    private String description;
    private String referenceCode1;
    private String referenceCode2;
    private String amount;
    private String bankTransactionID;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferenceCode1() {
        return referenceCode1;
    }

    public String getReferenceCode2() {
        return referenceCode2;
    }

    public String getBalance() {
        return amount;
    }

    public String getBankTransactionID() {
        return bankTransactionID;
    }

    public void setReferenceCode1(String referenceCode1) {
        this.referenceCode1 = referenceCode1;
    }

    public void setReferenceCode2(String referenceCode2) {
        this.referenceCode2 = referenceCode2;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setBankTransactionID(String bankTransactionID) {
        this.bankTransactionID = bankTransactionID;
    }
}
