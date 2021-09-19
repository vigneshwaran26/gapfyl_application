package com.gapfyl.dto.payouts;

import com.gapfyl.enums.accounts.AccountType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author vignesh
 * Created on 08/08/21
 **/

@Getter
@Setter
public class PayoutAccountResponse {

    public String id;

    public String accountId;

    public String userId;

    public AccountType payoutAccountType;

    public String payoutAccountId;

    public List<BankAccountResponse> bankAccounts;

    public List<VPAAccountResponse> vpaAccounts;
}
