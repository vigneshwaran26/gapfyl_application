package com.gapfyl.dto.checkouts.orders;

import com.gapfyl.enums.common.Currency;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author vignesh
 * Created on 03/07/21
 **/

@Getter
@Setter
public class OrderRequest {

    @NotNull
    public Double amount;

    @NotNull
    public Currency currency;

    public String receipt;

    public Map notes;

    public boolean partialPayment;

    @NotEmpty
    public List<String> productIds;

}
