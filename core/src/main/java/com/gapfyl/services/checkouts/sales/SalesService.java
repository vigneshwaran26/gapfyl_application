package com.gapfyl.services.checkouts.sales;

import com.gapfyl.dto.checkouts.sales.SalesRequest;
import com.gapfyl.exceptions.common.NotFoundException;
import com.gapfyl.models.users.UserEntity;

/**
 * @author vignesh
 * Created on 31/07/21
 **/

public interface SalesService {

    void addSales(SalesRequest salesRequest, UserEntity userEntity) throws NotFoundException;

}
