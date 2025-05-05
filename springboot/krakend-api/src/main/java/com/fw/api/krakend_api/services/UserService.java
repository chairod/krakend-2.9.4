package com.fw.api.krakend_api.services;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fw.api.krakend_api.model.user.UserBuyModel;
import com.fw.api.krakend_api.model.user.UserModel;

import jakarta.ws.rs.core.MediaType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/user")
public class UserService {

    static Random _random = new Random();
    static BigDecimal _basePrice = new BigDecimal(100);
    static List<UserBuyModel> _userBuyDatas;
    static {
        _userBuyDatas = new ArrayList<UserBuyModel>();
        for (int ix = 0; ix < 50; ix++) {
            UserBuyModel model = new UserBuyModel();
            final float randomBuyVal = _random.nextFloat(100, 20000);
            final int randomUserId = _random.nextInt(1, 10);

            model.setUserId(randomUserId);
            model.setUserName(String.format("User %s", randomUserId));
            model.setBuyTotal(BigDecimal.valueOf(randomBuyVal).setScale(4, RoundingMode.UP));
            _userBuyDatas.add(model);
        }
    }

    @GetMapping(value = { "getAll/{prefix}", "getAll" }, produces = MediaType.APPLICATION_JSON)
    public @ResponseBody List<UserModel> getUsers(@PathVariable(name = "prefix", required = false) String prefix) {

        List<UserModel> users = new ArrayList<UserModel>();
        for (int ix = 0; ix < 10; ix++) {
            UserModel usrModel = new UserModel();
            usrModel.setUserId(ix);
            usrModel.setUserName(String.format("%sUserName %s", prefix == null ? "" : prefix, ix));
            usrModel.setCitizenId(UUID.randomUUID().toString());
            users.add(usrModel);
        }

        return users;
    }

    @GetMapping(value = {"/getAllRandomThrow/{prefix}", "/getAllRandomThrow"}, produces = MediaType.APPLICATION_JSON)
    public List<UserModel> getAllRamdonThrow(@PathVariable(name = "prefix", required = false) String prefix)
            throws Exception {
        final boolean isThrowError = _random.nextInt(1, 5) == 1;
        if (isThrowError)
            throw new Exception("Test Circuit break");

        List<UserModel> users = new ArrayList<UserModel>();
        for (int ix = 0; ix < 10; ix++) {
            UserModel usrModel = new UserModel();
            usrModel.setUserId(ix);
            usrModel.setUserName(String.format("%sUserName %s", prefix == null ? "" : prefix, ix));
            usrModel.setCitizenId(UUID.randomUUID().toString());
            users.add(usrModel);
        }

        return users;
    }

    @GetMapping(value = { "buyHistory/{userId}", "buyHistory" })
    public List<UserBuyModel> getUserByHistory(@PathVariable(name = "userId", required = false) Long userId) {
        if (null == userId)
            return _userBuyDatas.stream().sorted((a, b) -> {
                if (a.getUserId() > b.getUserId())
                    return 1;
                else if (a.getUserId() == b.getUserId())
                    return 0;
                return -1;
            }).collect(Collectors.toList());
        return _userBuyDatas.stream().filter(r -> r.getUserId() == userId).collect(Collectors.toList());
    }

}
