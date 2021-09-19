package com.gapfyl.dto.payouts;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

/**
 * @author vignesh
 * Created on 08/08/21
 **/

@Getter
@Setter
public class BankAccountResponse {

    private String id;

    private String name;

    private String ifsc;

    private String accountNumber;

}
