package com.gapfyl.repository.custom;

import com.gapfyl.dto.payouts.BankAccountRequest;
import com.gapfyl.dto.payouts.VPAAccountRequest;
import com.gapfyl.enums.accounts.AccountType;
import com.gapfyl.models.payouts.PayoutAccountEntity;
import com.gapfyl.models.accounts.BankAccountEntity;
import com.gapfyl.models.accounts.VPAAccountEntity;
import com.gapfyl.models.users.UserEntity;

import java.util.List;

/**
 * @author vignesh
 * Created on 08/08/21
 **/

public interface ICustomPayoutAccountRepository {

    List<PayoutAccountEntity> fetchPayoutAccounts(String accountId, String userId);

    PayoutAccountEntity addBankAccount(String payoutId, BankAccountEntity bankAccount, UserEntity userEntity);

    PayoutAccountEntity updateBankAccount(String payoutId, String accountId,
                                          BankAccountRequest bankAccount, UserEntity userEntity);

    PayoutAccountEntity deleteBankAccount(String payoutAccountId, String bankAccountId, UserEntity userEntity);

    PayoutAccountEntity addVpaAccount(String payoutId, VPAAccountEntity vpaAccount, UserEntity userEntity);

    PayoutAccountEntity updateVpaAccount(String payoutId, String accountId,
                                         VPAAccountRequest vpaAccountRequest, UserEntity userEntity);

    PayoutAccountEntity deleteVpaAccount(String payoutAccountId, String vpaAccountId, UserEntity userEntity);

    PayoutAccountEntity setPayoutAccount(String payoutAccountId, AccountType accountType, String accountId,
                                         UserEntity userEntity);
}
