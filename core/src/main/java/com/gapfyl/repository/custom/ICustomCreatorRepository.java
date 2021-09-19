package com.gapfyl.repository.custom;

import com.gapfyl.dto.payouts.BankAccountRequest;
import com.gapfyl.dto.payouts.VPAAccountRequest;
import com.gapfyl.dto.users.creators.CreatorRequest;
import com.gapfyl.enums.accounts.AccountType;
import com.gapfyl.filter.creators.CreatorsFilterCriteria;
import com.gapfyl.models.users.creators.CreatorEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.models.users.creators.EducationEntity;
import com.gapfyl.models.users.creators.WorkEntity;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

public interface ICustomCreatorRepository {

    CreatorEntity updateCreator(String creatorId, CreatorRequest creatorRequest, List<EducationEntity> educations,
                                List<WorkEntity> works, UserEntity userEntity);

    // razorpay - update contactId
    UpdateResult updateRazorpayContactId(String email, String contactId);

    // razorpay - update accountId
    UpdateResult updateRazorpayAccountId(String email, String accountId);
}
