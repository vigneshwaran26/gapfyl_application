package com.gapfyl.enums.common;

/**
 * @author vignesh
 * Created on 28/06/21
 **/

public enum Currency {
    INR("Indian Rupee", "INR", "₹",356),
    USD("US Dollar", "USD", "$",840);

    String currency;
    String code;
    String symbol;
    Integer number;

    Currency(String currency, String code, String symbol, Integer number) {
        this.currency = currency;
        this.code = code;
        this.symbol = symbol;
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public String getCode() {
        return code;
    }

    public String getCurrency() {
        return currency;
    }

    public String getSymbol() {
        return symbol;
    }
}
