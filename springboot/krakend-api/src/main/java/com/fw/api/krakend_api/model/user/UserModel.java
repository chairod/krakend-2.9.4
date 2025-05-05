package com.fw.api.krakend_api.model.user;

import com.fw.api.krakend_api.iServiceModel;

import lombok.Getter;
import lombok.Setter;

public class UserModel implements iServiceModel {
    @Getter @Setter
    private int userId;

    @Getter @Setter
    private String userName;

    @Getter @Setter
    private String citizenId;
}
