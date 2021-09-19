package com.gapfyl.repository.impl;

import com.gapfyl.dto.payouts.BankAccountRequest;
import com.gapfyl.dto.payouts.VPAAccountRequest;
import com.gapfyl.enums.accounts.AccountType;
import com.gapfyl.models.payouts.PayoutAccountEntity;
import com.gapfyl.models.accounts.BankAccountEntity;
import com.gapfyl.models.accounts.VPAAccountEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.repository.custom.ICustomPayoutAccountRepository;
import com.gapfyl.util.Common;
import com.mongodb.client.model.FindOneAndDeleteOptions;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


/**
 * @author vignesh
 * Created on 08/08/21
 **/

@Slf4j
@Service
public class PayoutAccountRepositoryImpl implements ICustomPayoutAccountRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<PayoutAccountEntity> fetchPayoutAccounts(String accountId, String userId) {
        Query query = new Query();
        Criteria criteria = Criteria.where("account_id").is(accountId).and("user_id").is(userId);

        query.addCriteria(criteria);
        return mongoTemplate.find(query, PayoutAccountEntity.class);
    }

    @Override
    public PayoutAccountEntity addBankAccount(String payoutId, BankAccountEntity bankAccount, UserEntity userEntity) {
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(payoutId);

        query.addCriteria(criteria);

        Update update = new Update();
        update.addToSet("bank_accounts", bankAccount);
        update.set("modified_by", userEntity);
        update.set("modified_date", Common.getCurrentUTCDate());

        log.debug("adding bank account {} to payout account with query {}", bankAccount, query);

        FindAndModifyOptions returnNew = new FindAndModifyOptions().returnNew(true);
        return mongoTemplate.findAndModify(query, update, returnNew, PayoutAccountEntity.class);
    }

    @Override
    public PayoutAccountEntity updateBankAccount(String payoutId, String accountId,
                                          BankAccountRequest bankAccount, UserEntity userEntity) {
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(payoutId)
                .and("bank_accounts.id").is(new ObjectId(accountId));

        query.addCriteria(criteria);

        Update update = new Update();
        update.set("bank_accounts.$.account_number", bankAccount.getAccountNumber());
        update.set("bank_accounts.$.ifsc", bankAccount.getIfsc());
        update.set("bank_accounts.$.name", bankAccount.getName());
        update.set("modified_by", userEntity);
        update.set("modified_date", Common.getCurrentUTCDate());

        FindAndModifyOptions returnNew = new FindAndModifyOptions().returnNew(true);
        return  mongoTemplate.findAndModify(query, update, returnNew, PayoutAccountEntity.class);
   }

   @Override
    public PayoutAccountEntity deleteBankAccount(String payoutAccountId, String bankAccountId, UserEntity userEntity) {
       Query query = new Query();
       Criteria criteria = Criteria.where("id").is(payoutAccountId);

       query.addCriteria(criteria);

       Update update = new Update();
       update.pull("bank_accounts", Collections.singletonMap("id", new ObjectId(bankAccountId)));
       update.set("modified_by", userEntity);
       update.set("modified_date", Common.getCurrentUTCDate());

       FindAndModifyOptions returnNew = new FindAndModifyOptions().returnNew(true);
       return mongoTemplate.findAndModify(query, update, returnNew, PayoutAccountEntity.class);
    }

    @Override
    public PayoutAccountEntity addVpaAccount(String payoutId, VPAAccountEntity vpaAccount, UserEntity userEntity) {
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(payoutId);

        Update update = new Update();
        update.addToSet("vpa_accounts", vpaAccount);
        update.set("modified_by", userEntity);
        update.set("modified_date", Common.getCurrentUTCDate());

        query.addCriteria(criteria);
        log.debug("adding vpa account {} to payout account with query {}", vpaAccount, query);

        FindAndModifyOptions returnNew = new FindAndModifyOptions().returnNew(true);
        return mongoTemplate.findAndModify(query, update, returnNew, PayoutAccountEntity.class);
    }

    @Override
    public PayoutAccountEntity updateVpaAccount(String payoutId, String accountId,
                                         VPAAccountRequest vpaAccountRequest, UserEntity userEntity) {
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(payoutId)
                .and("vpa_accounts.id").is(new ObjectId(accountId));

        query.addCriteria(criteria);

        Update update = new Update();
        update.set("vpa_accounts.$.address", vpaAccountRequest.getAddress());
        update.set("modified_by", userEntity);
        update.set("modified_date", Common.getCurrentUTCDate());

        FindAndModifyOptions returnNew = new FindAndModifyOptions().returnNew(true);
        return  mongoTemplate.findAndModify(query, update, returnNew, PayoutAccountEntity.class);
    }

    @Override
    public PayoutAccountEntity deleteVpaAccount(String payoutAccountId, String vpaAccountId, UserEntity userEntity) {
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(payoutAccountId);

        query.addCriteria(criteria);

        Update update = new Update();
        update.pull("vpa_accounts", Collections.singletonMap("id", new ObjectId(vpaAccountId)));
        update.set("modified_by", userEntity);
        update.set("modified_date", Common.getCurrentUTCDate());

        FindAndModifyOptions returnNew = new FindAndModifyOptions().returnNew(true);
        return mongoTemplate.findAndModify(query, update, returnNew, PayoutAccountEntity.class);
    }

    @Override
    public PayoutAccountEntity setPayoutAccount(String payoutAccountId, AccountType accountType, String accountId,
                                                UserEntity userEntity) {
        Query query = new Query();
        Criteria criteria = Criteria.where("id").is(payoutAccountId);

        query.addCriteria(criteria);

        Update update = new Update();
        update.set("payout_account_type", accountType);
        update.set("payout_account_id", accountId);
        update.set("modified_by", userEntity);
        update.set("modified_date", Common.getCurrentUTCDate());

        FindAndModifyOptions returnNew = new FindAndModifyOptions().returnNew(true);
        return mongoTemplate.findAndModify(query, update, returnNew, PayoutAccountEntity.class);
    }
}
