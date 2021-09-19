package com.gapfyl.services.payouts;

import com.gapfyl.repository.PayoutAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vignesh
 * Created on 08/08/21
 **/

@Slf4j
@Service
public class PayoutServiceImpl implements PayoutService {

    @Autowired
    PayoutAccountRepository payoutAccountRepository;
}
