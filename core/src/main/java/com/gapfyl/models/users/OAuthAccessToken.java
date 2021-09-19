package com.gapfyl.models.users;

import com.gapfyl.util.OAuthSerializationUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

@Document(collection = "oauth2_access_token")
@Data
@EqualsAndHashCode(callSuper = false)
public class OAuthAccessToken {

    @Id
    private String id;
    private String tokenId;
    private OAuth2AccessToken token;
    private String authenticationId;
    private String username;
    private String clientId;
    private String authentication;
    private String refreshToken;


    public OAuth2Authentication getAuthentication() {
        return OAuthSerializationUtil.deserialize(authentication);
    }

    public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = OAuthSerializationUtil.serialize(authentication);
    }
}
