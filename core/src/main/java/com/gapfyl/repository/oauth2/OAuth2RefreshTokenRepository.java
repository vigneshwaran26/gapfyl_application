package com.gapfyl.repository.oauth2;

import com.gapfyl.models.users.OAuthRefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OAuth2RefreshTokenRepository extends MongoRepository<OAuthRefreshToken, String> {

    Optional<OAuthRefreshToken> findByTokenId(String tokenId);
    
}

