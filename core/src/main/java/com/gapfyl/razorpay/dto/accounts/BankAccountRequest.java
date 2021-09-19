package com.gapfyl.razorpay.dto.accounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Getter
@Setter
public class BankAccountRequest {

    @JsonProperty("name")
    public String name;

    @JsonProperty("ifsc")
    public String ifsc;

    @JsonProperty("account_number")
    public String accountNumber;

}
