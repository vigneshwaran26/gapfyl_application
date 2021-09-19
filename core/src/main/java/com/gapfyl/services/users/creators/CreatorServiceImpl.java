package com.gapfyl.services.users.creators;

import com.gapfyl.dto.users.creators.CreatorRequest;
import com.gapfyl.dto.users.creators.CreatorResponse;
import com.gapfyl.dto.users.creators.Education;
import com.gapfyl.dto.users.creators.Work;
import com.gapfyl.enums.common.LookupFilterCode;
import com.gapfyl.enums.users.ProfileType;
import com.gapfyl.exceptions.common.NotFoundException;
import com.gapfyl.models.users.creators.CreatorEntity;
import com.gapfyl.models.users.creators.CreatorStatus;
import com.gapfyl.models.users.creators.EducationEntity;
import com.gapfyl.models.users.creators.WorkEntity;
import com.gapfyl.models.users.UserEntity;
import com.gapfyl.models.users.UserRole;
import com.gapfyl.razorpay.dto.contacts.ContactRequest;
import com.gapfyl.razorpay.enums.contacts.ContactType;
import com.gapfyl.razorpay.services.accounts.AccountService;
import com.gapfyl.razorpay.services.contacts.ContactService;
import com.gapfyl.repository.CreatorRepository;
import com.gapfyl.services.lookup.LookupService;
import com.gapfyl.services.users.UserService;
import com.gapfyl.util.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Slf4j
@Service
public class CreatorServiceImpl implements CreatorService {

    @Autowired
    CreatorRepository creatorRepository;

    @Autowired
    ContactService contactService;

    @Autowired
    AccountService accountService;

    @Autowired
    LookupService lookupService;

    @Autowired
    UserService userService;

    @Async("gapFylTaskExecutor")
    private void addCreatorContact(String name, String email, String mobile) {
        ContactRequest contactRequest = new ContactRequest();
        contactRequest.setName(name);
        contactRequest.setEmail(email);
        contactRequest.setContact(mobile);
        contactRequest.setType(ContactType.vendor);
        contactService.createContact(contactRequest);
    }

    @Async("gapFylTaskExecutor")
    private void AddCreatorAccount() {

    }

    @Override
    public CreatorEntity fetchById(String creatorId) {
        return creatorRepository.findById(creatorId).orElse(null);
    }

    public CreatorEntity fetchByUserId(String userId) { return  creatorRepository.findOneByUserId(userId); }

    private CreatorEntity createCreator(CreatorRequest createRequest, UserEntity userEntity) {
        CreatorEntity creatorEntity = new CreatorEntity();
        creatorEntity.setAccountId(userEntity.getAccount().getId());
        creatorEntity.setUserId(userEntity.getId());
        creatorEntity.setStatus(CreatorStatus.registered);
        creatorEntity.setAbout(createRequest.getAbout());
        creatorEntity.setExpertise(createRequest.getExpertise());
        creatorEntity.setInterests(createRequest.getInterests());
        creatorEntity.setLanguages(createRequest.getLanguages());
        creatorEntity.setLinkedInUrl(createRequest.getLinkedProfile());
        creatorEntity.setEducations(getEducations(createRequest.getEducations()));
        creatorEntity.setWorks(getWorks(createRequest.getWorks()));
        creatorEntity.setCreatedBy(userEntity);
        creatorEntity.setCreatedDate(Common.getCurrentUTCDate());
        creatorEntity.setModifiedBy(userEntity);
        creatorEntity.setModifiedDate(Common.getCurrentUTCDate());
        creatorEntity = creatorRepository.save(creatorEntity);
        return creatorEntity;
    }

    private CreatorEntity updateCreator(String creatorId, CreatorRequest creatorRequest, UserEntity userEntity) {
        return creatorRepository.updateCreator(creatorId, creatorRequest, getEducations(creatorRequest.getEducations()),
                getWorks(creatorRequest.getWorks()), userEntity);
    }

    private List<EducationEntity> getEducations(List<Education> educations) {
        if (Common.isNullOrEmpty(educations)) return Collections.EMPTY_LIST;
        return educations.stream().map(item -> createEducation(item)).collect(Collectors.toList());
    }

    private List<WorkEntity> getWorks(List<Work> works) {
        if (Common.isNullOrEmpty(works)) return Collections.EMPTY_LIST;
        return works.stream().map(item -> createWork(item)).collect(Collectors.toList());
    }

    private EducationEntity createEducation(Education education) {
        EducationEntity educationEntity = new EducationEntity();
        educationEntity.setStudy(education.getStudy());
        educationEntity.setSchool(education.getSchool());
        return educationEntity;
    }

    private WorkEntity createWork(Work work) {
        WorkEntity workEntity = new WorkEntity();
        workEntity.setJob(work.getJob());
        workEntity.setCompany(work.getCompany());
        return workEntity;
    }

    @Override
    public CreatorResponse createOrUpdateCreator(CreatorRequest creatorRequest, UserEntity userEntity)
            throws NotFoundException {

        CreatorEntity existing = fetchByUserId(userEntity.getId());
        CreatorEntity creatorEntity = (existing == null) ? createCreator(creatorRequest, userEntity) :
                updateCreator(existing.getId(), creatorRequest, userEntity);

        if (existing == null) {
            // adding contact to razorpay
            addCreatorContact(userEntity.getName(), userEntity.getEmail(), userEntity.getMobile());

            // adding creator to lookup filter
            lookupService.createLookupFilter(LookupFilterCode.creator, userEntity.getId(), userEntity.getName(),true, userEntity);
        }

        // updating user role as creator
        userService.updateRoles(userEntity.getEmail(), Arrays.asList(UserRole.ROLE_CREATOR.name()),
                Arrays.asList(UserRole.ROLE_LEARNER.name(), UserRole.ROLE_ORGANIZATION.name(),
                        UserRole.ROLE_SCHOOL.name()), userEntity);

        // update user profile type as creator
        userService.changeProfileType(userEntity.getId(), ProfileType.creator, userEntity);

        return entityToResponse(creatorEntity);
    }

    @Override
    public CreatorResponse fetchUserCreatorProfile(UserEntity userEntity) {
        log.debug("fetching user {} [{}] creator profile");
        CreatorEntity creatorEntity = fetchByUserId(userEntity.getId());
        if (creatorEntity == null) return null;

        return entityToResponse(creatorEntity);
    }


    private Education createEducation(EducationEntity educationEntity) {
        Education education = new Education();
        education.setSchool(educationEntity.getSchool());
        education.setStudy(educationEntity.getStudy());
        return education;
    }

    private Work createWork(WorkEntity workEntity) {
        Work work = new Work();
        work.setCompany(workEntity.getCompany());
        work.setJob(workEntity.getJob());
        return work;
    }

    private List<Education> getEducationsResponses(List<EducationEntity> educations) {
        if (Common.isNullOrEmpty(educations)) return Collections.EMPTY_LIST;
        return educations.stream().map(item -> createEducation(item)).collect(Collectors.toList());
    }

    private List<Work> getWorksResponses(List<WorkEntity> works) {
        if (Common.isNullOrEmpty(works)) return Collections.EMPTY_LIST;
        return works.stream().map(item -> createWork(item)).collect(Collectors.toList());
    }

    private CreatorResponse entityToResponse(CreatorEntity creatorEntity) {
        CreatorResponse creatorResponse = new CreatorResponse();
        creatorResponse.setId(creatorEntity.getId());
        creatorResponse.setAbout(creatorEntity.getAbout());
        creatorResponse.setExpertise(creatorEntity.getExpertise());
        creatorResponse.setInterests(creatorEntity.getInterests());
        creatorResponse.setLanguages(creatorEntity.getLanguages());
        creatorResponse.setLinkedInUrl(creatorEntity.getLinkedInUrl());
        creatorResponse.setEducations(getEducationsResponses(creatorEntity.getEducations()));
        creatorResponse.setWorks(getWorksResponses(creatorEntity.getWorks()));
        creatorResponse.setStatus(creatorEntity.getStatus());
        return creatorResponse;
    }
}
