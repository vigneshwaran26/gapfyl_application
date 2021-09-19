package com.gapfyl.services.payouts;

import com.gapfyl.dto.payouts.BankAccountRequest;
import com.gapfyl.dto.payouts.BankAccountResponse;
import com.gapfyl.dto.payouts.PayoutAccountResponse;
import com.gapfyl.dto.payouts.VPAAccountRequest;
import com.gapfyl.dto.payouts.VPAAccountResponse;
import com.gapfyl.enums.accounts.AccountType;
import com.gapfyl.models.payouts.PayoutAccountEntity;
import com.gapfyl.models.accounts.BankAccountEntity;
import com.gapfyl.models.accounts.VPAAccountEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.PayoutAccountRepository;
import com.gapfyl.util.Common;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author vignesh
 * Created on 08/08/21
 **/

@Slf4j
@Service
public class PayoutAccountServiceImpl implements PayoutAccountService {

    @Autowired
    PayoutAccountRepository payoutAccountRepository;

    private BankAccountResponse bankEntityToResponse(BankAccountEntity bankAccountEntity) {
        BankAccountResponse bankAccountResponse = new BankAccountResponse();
        bankAccountResponse.setId(bankAccountEntity.getId().toString());
        bankAccountResponse.setAccountNumber(bankAccountEntity.getAccountNumber());
        bankAccountResponse.setIfsc(bankAccountEntity.getIfsc());
        bankAccountResponse.setName(bankAccountEntity.getName());
        return bankAccountResponse;
    }

    private VPAAccountResponse vpaEntityToResponse(VPAAccountEntity vpaAccountEntity) {
        VPAAccountResponse vpaAccountResponse = new VPAAccountResponse();
        vpaAccountResponse.setId(vpaAccountEntity.getId().toString());
        vpaAccountResponse.setAddress(vpaAccountEntity.getAddress());
        return vpaAccountResponse;
    }

    private List<BankAccountResponse> getBankAccountResponses(List<BankAccountEntity> bankAccountEntities) {
        if (Common.isNullOrEmpty(bankAccountEntities)) return Collections.EMPTY_LIST;
        return bankAccountEntities.stream().map(item -> bankEntityToResponse(item)).collect(Collectors.toList());
    }

    private List<VPAAccountResponse> getVpaAccountResponses(List<VPAAccountEntity> vpaAccountEntities) {
        if (Common.isNullOrEmpty(vpaAccountEntities)) return Collections.EMPTY_LIST;
        return vpaAccountEntities.stream().map(item -> vpaEntityToResponse(item)).collect(Collectors.toList());
    }

    private PayoutAccountResponse entityToResponse(PayoutAccountEntity payoutAccountEntity) {
        PayoutAccountResponse payoutAccountResponse = new PayoutAccountResponse();
        payoutAccountResponse.setId(payoutAccountEntity.getId());
        payoutAccountResponse.setAccountId(payoutAccountEntity.getAccountId());
        payoutAccountResponse.setUserId(payoutAccountEntity.getUserId());
        payoutAccountResponse.setPayoutAccountId(payoutAccountEntity.getPayoutAccountId());
        payoutAccountResponse.setPayoutAccountType(payoutAccountEntity.getPayoutAccountType());
        payoutAccountResponse.setBankAccounts(getBankAccountResponses(payoutAccountEntity.getBankAccounts()));
        payoutAccountResponse.setVpaAccounts(getVpaAccountResponses(payoutAccountEntity.getVpaAccounts()));
        return payoutAccountResponse;
    }

    public PayoutAccountEntity fetchByUserId(String userId) {
        return payoutAccountRepository.findOneByUserId(userId);
    }

    public PayoutAccountEntity createPayoutAccount(String accountId, String userId, UserEntity userEntity) {
        log.info("user {} [{}] creating a payout account", userEntity.getName(), userEntity.getId());
        PayoutAccountEntity payoutAccount = new PayoutAccountEntity();
        payoutAccount.setAccountId(accountId);
        payoutAccount.setUserId(userId);
        return payoutAccountRepository.save(payoutAccount);
    }

    @Override
    public BankAccountResponse addBankAccount(BankAccountRequest bankAccount, UserEntity userEntity) {
        log.info("user {} [{}] adding bank account", userEntity.getName(), userEntity.getId());
        PayoutAccountEntity payoutAccount = fetchByUserId(userEntity.getId());
        if (payoutAccount == null) {
            log.debug("payout account not found for user {} [{}]", userEntity.getName(), userEntity.getId());
            payoutAccount = createPayoutAccount(userEntity.getAccount().getId(), userEntity.getId(), userEntity);
            log.debug("payout account created for user {} [{}]", userEntity.getName(), userEntity.getId());
        }

        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setId(new ObjectId());
        bankAccountEntity.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountEntity.setIfsc(bankAccount.getIfsc());
        bankAccountEntity.setName(bankAccount.getName());

        PayoutAccountEntity updatedPayoutAccount = payoutAccountRepository
                .addBankAccount(payoutAccount.getId(), bankAccountEntity, userEntity);

        if (updatedPayoutAccount == null) {
            log.error("failed to add bank account");
            return null;
        }

        try {
            List<BankAccountEntity> updatedBankAccounts = updatedPayoutAccount.getBankAccounts();
            BankAccountEntity updatedBankAccount = updatedBankAccounts.stream()
                    .reduce((first, second) -> second).orElse(null);
            return bankEntityToResponse(updatedBankAccount);
        } catch (Exception e) {
            log.error("failed to convert entity to response {}", e);
            return null;
        }
   }

    @Override
    public void updateBankAccount(String payoutAccountId, String bankAccountId, BankAccountRequest bankAccountRequest,
                                  UserEntity userEntity) {
        log.info("user {} [{}] deleting bank account {} {}", userEntity.getName(), userEntity.getId(), payoutAccountId);
        payoutAccountRepository.updateBankAccount(payoutAccountId, bankAccountId, bankAccountRequest, userEntity);
    }

    @Override
    public void deleteBankAccount(String payoutAccountId, String bankAccountId, UserEntity userEntity) {
        log.info("user {} [{}] deleting bank account {} {}", userEntity.getName(), userEntity.getId());
        payoutAccountRepository.deleteBankAccount(payoutAccountId, bankAccountId, userEntity);
        log.info("user {} [{}] deleted bank account {} {}", userEntity.getName(), userEntity.getId());
    }

    @Override
    public VPAAccountResponse addVPAAccount(VPAAccountRequest vpaAccountRequest, UserEntity userEntity) {
        log.info("user {} [{}] adding vpa account", userEntity.getName(), userEntity.getId());
        PayoutAccountEntity payoutAccount = fetchByUserId(userEntity.getId());
        if (payoutAccount == null) {
            payoutAccount = createPayoutAccount(userEntity.getAccount().getId(), userEntity.getId(), userEntity);
        }

        VPAAccountEntity vpaAccountEntity = new VPAAccountEntity();
        vpaAccountEntity.setId(new ObjectId());
        vpaAccountEntity.setAddress(vpaAccountRequest.getAddress());

        PayoutAccountEntity updatedPayoutAccount = payoutAccountRepository
                .addVpaAccount(payoutAccount.getId(), vpaAccountEntity, userEntity);

        if (updatedPayoutAccount == null) {
            log.error("failed to add vpa account");
            return null;
        }

        try {
            List<VPAAccountEntity> updatedVpaAccounts = updatedPayoutAccount.getVpaAccounts();
            VPAAccountEntity updatedVpaAccount = updatedVpaAccounts.stream()
                    .reduce((first, second) -> second).orElse(null);
            return vpaEntityToResponse(updatedVpaAccount);
        } catch (Exception e) {
            log.error("failed to convert entity to response {}", e);
            return null;
        }
    }

    @Override
    public void updateVPAAccount(String payoutAccountId, String vpaAccountId, VPAAccountRequest vpaAccountRequest, UserEntity userEntity) {
        log.info("user {} [{}] updating vpa account {} {}", userEntity.getName(), userEntity.getId(), payoutAccountId, vpaAccountId);
        payoutAccountRepository.updateVpaAccount(payoutAccountId, vpaAccountId, vpaAccountRequest, userEntity);
        log.info("user {} [{}] updated vpa account {} {}", userEntity.getName(), userEntity.getId(), payoutAccountId, vpaAccountId);
    }

    @Override
    public void deleteVPAAccount(String payoutAccountId, String vpaAccountId, UserEntity userEntity) {
        log.info("user {} [{}] deleting vpa account {} {}", userEntity.getName(), userEntity.getId(), payoutAccountId, vpaAccountId);
        payoutAccountRepository.deleteVpaAccount(payoutAccountId, vpaAccountId, userEntity);
        log.info("user {} [{}] deleted vpa account {} {}", userEntity.getName(), userEntity.getId(), payoutAccountId, vpaAccountId);
    }

    @Override
    public PayoutAccountResponse fetchPayoutAccountResponse(UserEntity userEntity) {
        log.info("fetching user {} [{}] payout account", userEntity.getName(), userEntity.getId());
        PayoutAccountEntity payoutAccount = fetchByUserId(userEntity.getId());
        if (payoutAccount == null) {
            log.debug("user {} [{}] doesn't have payout account", userEntity.getName(), userEntity.getId());
            return null;
        }

        return entityToResponse(payoutAccount);
    }

    public List<PayoutAccountEntity> fetchPayoutAccounts(UserEntity userEntity) {
        return payoutAccountRepository.fetchPayoutAccounts(userEntity.getAccount().getId(), userEntity.getId());
    }

    @Override
    public  void setPayoutAccount(String payoutAccountId, AccountType accountType, String accountId, UserEntity userEntity) {
        payoutAccountRepository.setPayoutAccount(payoutAccountId, accountType, accountId, userEntity);
    }

    public List<PayoutAccountResponse> fetchPayoutAccountsResponses(UserEntity userEntity) {
        List<PayoutAccountEntity> payoutAccounts = fetchPayoutAccounts(userEntity);
        return payoutAccounts.stream().map(item -> entityToResponse(item)).collect(Collectors.toList());
    }
}
