package com.gapfyl.models.payouts;

import com.gapfyl.constants.Collections;
import com.gapfyl.enums.accounts.AccountType;
import com.gapfyl.models.common.BaseAuditEntity;
import com.gapfyl.models.accounts.BankAccountEntity;
import com.gapfyl.models.accounts.VPAAccountEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * @author vignesh
 * Created on 08/08/21
 **/

@Data
@Document(collection = Collections.PAYOUT_ACCOUNTS)
@EqualsAndHashCode(callSuper = false)
public class PayoutAccountEntity extends BaseAuditEntity {

    @Field("account_id")
    private String accountId;

    @Field("user_id")
    private String userId;

    @Field("payout_account_type")
    private AccountType payoutAccountType;

    @Field("payout_account_id")
    private String payoutAccountId;

    @Field("bank_accounts")
    private List<BankAccountEntity> bankAccounts;

    @Field("vpa_accounts")
    private List<VPAAccountEntity> vpaAccounts;

}
