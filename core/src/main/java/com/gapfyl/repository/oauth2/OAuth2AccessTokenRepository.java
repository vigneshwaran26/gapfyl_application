package com.gapfyl.repository.oauth2;


import com.gapfyl.models.users.OAuthAccessToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface OAuth2AccessTokenRepository extends MongoRepository<OAuthAccessToken, String> {

    List<OAuthAccessToken> findByClientId(String clientId);

    List<OAuthAccessToken> findByClientIdAndUsername(String clientId, String username);

    Optional<OAuthAccessToken> findByTokenId(String tokenId);

    Optional<OAuthAccessToken> findByRefreshToken(String refreshToken);

    Optional<OAuthAccessToken> findByAuthenticationId(String authenticationId);
}
