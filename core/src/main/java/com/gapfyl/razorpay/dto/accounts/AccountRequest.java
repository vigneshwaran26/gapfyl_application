package com.gapfyl.razorpay.dto.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gapfyl.razorpay.enums.accounts.AccountType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Getter
@Setter
public class AccountRequest {

    @JsonProperty("contact_id")
    public String contactId;

    @JsonProperty("account_type")
    public AccountType accountType;

    @JsonProperty("bank_account")
    public BankAccountRequest bankAccount;

    @JsonProperty("vpa")
    public VPAAccountRequest vpa;
}
