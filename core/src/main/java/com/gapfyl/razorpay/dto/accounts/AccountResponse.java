package com.gapfyl.razorpay.dto.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Getter
@Setter
public class AccountResponse {

    @JsonProperty("id")
    public String id;

    @JsonProperty("entity")
    public String entity;

    @JsonProperty("contact_id")
    public String contactId;

    @JsonProperty("account_type")
    public String accountType;

    @JsonProperty("bank_account")
    public BankAccountResponse bankAccount;

    @JsonProperty("vpa")
    public VPAAccountResponse vpa;

    @JsonProperty("active")
    public boolean active;

    @JsonProperty("batch_id")
    public String batchId;

    @JsonProperty("created_at")
    public Date createdAt;
}
