package com.gapfyl.razorpay.services.accounts;

import com.gapfyl.razorpay.dto.accounts.AccountRequest;
import com.gapfyl.razorpay.dto.accounts.AccountResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Value("${razorpay.url:no-url}")
    private String razorpayUrl;

    @Value("${razorpay.key_id:no-id}")
    private String keyId;

    @Value("${razorpay.key_secret:no-secret}")
    private String keySecret;

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
    public AccountResponse createAccount(AccountRequest accountRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = createHeaders(keyId, keySecret);
        HttpEntity<AccountRequest> request = new HttpEntity<>(accountRequest, httpHeaders);
        ResponseEntity<AccountResponse> response = restTemplate.postForEntity(razorpayUrl + "/fund_accounts", request, AccountResponse.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        return null;
    }

    @Override
    public void activateAccount(String accountId) {

    }

    @Override
    public AccountResponse fetchAccount(String accountId) {
        return new AccountResponse();
    }

    @Override
    public List<AccountResponse> fetchAccounts() {
        return new ArrayList<>();
    }
}
