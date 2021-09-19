package com.gapfyl.razorpay.services.contacts;

import com.gapfyl.razorpay.dto.contacts.ContactActivateRequest;
import com.gapfyl.razorpay.dto.contacts.ContactRequest;
import com.gapfyl.razorpay.dto.contacts.ContactResponse;

import java.util.List;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

public interface ContactService {

    void createContact(ContactRequest contactRequest);

    ContactResponse updateContact(String contactId, ContactRequest contactRequest);

    void activateContact(String contactId, ContactActivateRequest activateRequest);

    ContactResponse fetchContact(String contactId);

    List<ContactResponse> fetchContacts();

}
