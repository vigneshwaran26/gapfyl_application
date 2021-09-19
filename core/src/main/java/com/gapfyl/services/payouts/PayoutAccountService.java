package com.gapfyl.services.payouts;

import com.gapfyl.dto.payouts.BankAccountRequest;
import com.gapfyl.dto.payouts.BankAccountResponse;
import com.gapfyl.dto.payouts.PayoutAccountResponse;
import com.gapfyl.dto.payouts.VPAAccountRequest;
import com.gapfyl.dto.payouts.VPAAccountResponse;
import com.gapfyl.enums.accounts.AccountType;
import com.gapfyl.exceptions.common.NotFoundException;
import com.gapfyl.models.payouts.PayoutAccountEntity;
import com.gapfyl.models.users.UserEntity;

/**
 * @author vignesh
 * Created on 08/08/21
 **/

public interface PayoutAccountService {

    BankAccountResponse addBankAccount(BankAccountRequest bankAccount, UserEntity userEntity);

    void updateBankAccount(String payoutAccountId, String bankAccountId, BankAccountRequest bankAccountRequest, UserEntity userEntity);

    void deleteBankAccount(String payoutAccountId, String bankAccountId, UserEntity userEntity);

    VPAAccountResponse addVPAAccount(VPAAccountRequest vpaAccountRequest, UserEntity userEntity);

    void updateVPAAccount(String payoutAccountId, String vpaAccountId, VPAAccountRequest vpaAccountRequest, UserEntity userEntity);

    void deleteVPAAccount(String payoutAccountId, String vpaAccountId, UserEntity userEntity);

    void setPayoutAccount(String payoutAccountId, AccountType accountType, String accountId, UserEntity userEntity);

    PayoutAccountResponse fetchPayoutAccountResponse(UserEntity userEntity);

}
