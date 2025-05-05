package com.fw.api.krakend_api.model.user;

import java.math.BigDecimal;
import java.util.List;

import com.fw.api.krakend_api.iServiceModel;

import lombok.Getter;
import lombok.Setter;

public class UserBuyModel implements iServiceModel{
    @Getter @Setter
    private String userName;
    
    @Getter @Setter
    private int userId;

    @Getter @Setter
    private List<ProductModel> products;

    @Getter @Setter
    private BigDecimal buyTotal;
}
