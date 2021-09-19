package com.gapfyl.razorpay.services.contacts;

import com.gapfyl.razorpay.dto.contacts.ContactActivateRequest;
import com.gapfyl.razorpay.dto.contacts.ContactRequest;
import com.gapfyl.razorpay.dto.contacts.ContactResponse;
import com.gapfyl.repository.CreatorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Slf4j
@Service
public class ContactServiceImpl implements ContactService {

    @Value("${razorpay.key_id:no-id}")
    private String keyId;

    @Value("${razorpay.key_secret:no-secret}")
    private String keySecret;

    @Value("${razorpay.url:no-url}")
    private String razorpayUrl;

    @Autowired
    CreatorRepository creatorRepository;


    private HttpHeaders createHeaders(String userName, String password) {
        return new HttpHeaders() {
            {
                String authKey = new StringBuilder().append(userName).append(":").append(password).toString();
                byte[] encodedAuthKey = Base64.getEncoder().encode(authKey.getBytes(Charset.forName("US-ASCII")));
                String authKeyHeader = "Basic " + new String( encodedAuthKey );
                set( "Authorization", authKeyHeader );
            }
        };
    }

    @Override
    public void createContact(ContactRequest contactRequest) {
        log.info("razorpay invoke contact api to create");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = createHeaders(keyId, keySecret);
        HttpEntity<ContactRequest> request = new HttpEntity<>(contactRequest, httpHeaders);
        ResponseEntity<ContactResponse> response = restTemplate.exchange(razorpayUrl + "/contacts",
                HttpMethod.POST, request, new ParameterizedTypeReference<>() {});
        if (response.getStatusCode().is2xxSuccessful()) {
            ContactResponse contactResponse = response.getBody();
            log.info("razorpay: contact created {} for creator {}", contactResponse.getId(), contactResponse.getEmail());
            creatorRepository.updateRazorpayContactId(contactResponse.getEmail(), contactResponse.getId());
        }
    }

    @Override
    public ContactResponse updateContact(String contactId, ContactRequest contactRequest) {
        log.info("razorpay invoke contact api to create");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = createHeaders(keyId, keySecret);
        HttpEntity<ContactRequest> request = new HttpEntity<>(contactRequest, httpHeaders);
        ResponseEntity<ContactResponse> response = restTemplate.exchange(razorpayUrl + "/contacts/" + contactId,
                HttpMethod.PATCH, request, new ParameterizedTypeReference<>() {});
        if (response.getStatusCode().is2xxSuccessful()) {
            ContactResponse contactResponse = response.getBody();
            return contactResponse;
        }
        return null;
    }

    @Override
    public void activateContact(String contactId, ContactActivateRequest activateRequest) {
        log.info("razorpay invoke contact api to create");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = createHeaders(keyId, keySecret);
        HttpEntity<ContactActivateRequest> request = new HttpEntity<>(activateRequest, httpHeaders);
        ResponseEntity<ContactResponse> response = restTemplate.exchange(razorpayUrl + "/contacts/" + contactId,
                HttpMethod.PATCH, request, new ParameterizedTypeReference<>() {});
        if (response.getStatusCode().is2xxSuccessful()) {
            ContactResponse contactResponse = response.getBody();
        }
    }

    @Override
    public ContactResponse fetchContact(String contactId) {
        log.info("razorpay invoke contact api to create");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = createHeaders(keyId, keySecret);
        HttpEntity request = new HttpEntity<>(httpHeaders);
        ResponseEntity<ContactResponse> response = restTemplate.exchange(razorpayUrl + "/contacts/" + contactId,
                HttpMethod.GET, request, new ParameterizedTypeReference<>() {});
        if (response.getStatusCode().is2xxSuccessful()) {
            ContactResponse contactResponse = response.getBody();
            return contactResponse;
        }

        return null;
    }

    @Override
    public List<ContactResponse> fetchContacts() {
        log.info("razorpay invoke contact api to create");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = createHeaders(keyId, keySecret);
        HttpEntity request = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<ContactResponse>> response = restTemplate.exchange(razorpayUrl + "/contacts",
                HttpMethod.GET, request, new ParameterizedTypeReference<>() {});
        if (response.getStatusCode().is2xxSuccessful()) {
            List<ContactResponse> contacts = response.getBody();
            return contacts;
        }

        return null;
    }
}
