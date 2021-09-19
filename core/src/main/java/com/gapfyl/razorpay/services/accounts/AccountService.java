package com.gapfyl.razorpay.services.accounts;

import com.gapfyl.razorpay.dto.accounts.AccountRequest;
import com.gapfyl.razorpay.dto.accounts.BankAccountResponse;
import com.gapfyl.razorpay.dto.accounts.AccountResponse;

import java.util.List;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

public interface AccountService {

    AccountResponse createAccount(AccountRequest accountRequest);

    void activateAccount(String accountId);

    AccountResponse fetchAccount(String accountId);

    List<AccountResponse> fetchAccounts();
}
