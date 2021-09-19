package com.gapfyl.services.geographical;

import com.gapfyl.dto.users.GeographicalDataRequest;
import com.gapfyl.exceptions.users.AccountConflictException;
import com.gapfyl.exceptions.users.UserConflictException;
import com.gapfyl.models.geodata.GeodataEntity;
import com.gapfyl.repository.GeographyicalRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GeoServiceImpl implements GeoService{
    @Autowired
    GeographyicalRepository geographyicalRepository;

    @Override
    public void UserGeography(GeographicalDataRequest geographicalDataRequest) throws UserConflictException, AccountConflictException {
        GeodataEntity geodataEntity = new GeodataEntity();
        geodataEntity.setIPv4(geographicalDataRequest.getIpv4());
        geodataEntity.setCity(geographicalDataRequest.getCity());
        geodataEntity.setCountryCode(geographicalDataRequest.getCountryCode());
        geodataEntity.setCountryName(geographicalDataRequest.getCountryName());
        geodataEntity.setLatitude(geographicalDataRequest.getLatitude());
        geodataEntity.setLongitude(geographicalDataRequest.getLongitude());
        geodataEntity.setPostal(geographicalDataRequest.getPostal());
        geodataEntity.setState(geographicalDataRequest.getState());
        geographyicalRepository.save(geodataEntity);
    }
}
