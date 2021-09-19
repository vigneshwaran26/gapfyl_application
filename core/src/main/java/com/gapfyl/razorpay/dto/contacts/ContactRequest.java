package com.gapfyl.razorpay.dto.contacts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gapfyl.razorpay.enums.contacts.ContactType;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Getter
@Setter
public class ContactRequest {

    public String name;

    public String email;

    public String contact;

    public ContactType type;

    /*
    @JsonProperty("reference_id")
    public String referenceId;

    public Map<String, Object> notes = new HashMap<>();
    */
}
