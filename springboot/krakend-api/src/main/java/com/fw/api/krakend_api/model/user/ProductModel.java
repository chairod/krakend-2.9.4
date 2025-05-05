package com.fw.api.krakend_api.model.user;

import java.math.BigDecimal;

import com.fw.api.krakend_api.iServiceModel;

import lombok.Getter;
import lombok.Setter;

public class ProductModel implements iServiceModel{
    @Getter @Setter
    private int productId;

    @Getter @Setter
    private String productName;

    @Getter @Setter
    private BigDecimal unitPrice;
}
