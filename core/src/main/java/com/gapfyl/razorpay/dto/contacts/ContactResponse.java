package com.gapfyl.razorpay.dto.contacts;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactResponse {

    public String id;

    public String entity;

    public String name;

    public String contact;

    public String email;

    public String type;

    @JsonProperty("reference_id")
    public String referenceId;

    @JsonProperty("batch_id")
    public String batchId;

    public boolean active;

    public List<Map<String, Object>> notes;

    @JsonProperty("created_at")
    public Date createdAt;
}
