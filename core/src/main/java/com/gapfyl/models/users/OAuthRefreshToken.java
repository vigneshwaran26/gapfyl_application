package com.gapfyl.models.users;

import com.gapfyl.util.OAuthSerializationUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

@Document(collection = "oauth2_refresh_token")
@Data
@EqualsAndHashCode(callSuper = false)
public class OAuthRefreshToken {

    @Id
    private String id;
    private String tokenId;
    private OAuth2RefreshToken token;
    private String authentication;

    public OAuth2Authentication getAuthentication() {
        return OAuthSerializationUtil.deserialize(authentication);
    }

    public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = OAuthSerializationUtil.serialize(authentication);
    }
}
