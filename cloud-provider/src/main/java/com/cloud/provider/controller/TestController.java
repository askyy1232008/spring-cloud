package com.cloud.provider.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.provider.domain.User;
import com.cloud.provider.service.UserService;
import com.cloud.provider.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private UserService userService;
    /**
     * 模拟测试CsvUtils.CsvWrite
     *
     * @return
     */
    @GetMapping(value = "/TestCsvUtilsCsvWrite")
    public String TestCsvUtilsCsvWrite() throws Exception {
        List<Map<String, Object>> maps = this.userService.queryUsersListMap();
        int len = maps.size();
        List<String[]> contexts = new ArrayList<String[]>();
        for (Map<String, Object> map : maps) {
            String[] context = new String[len];
            int i = 0;
            for (Object value : map.values()) {
                context[i] = value.toString();
                i += 1;
            }
            contexts.add(context);
        }
        CsvUtils.csvWrite("D://test.csv", new String[]{"id", "name", "age"}, contexts);
        return "OK";
    }

    /**
     * 模拟测试CsvUtils.CsvRead
     * @return
     */
    @GetMapping(value = "/TestCsvUtilsCsvRead")
    public Object TestCsvUtilsCsvRead(){
        JSONObject jobj = new JSONObject();
        ResultBuilder<JSONObject> resultBuilder;
        jobj.put("r", CsvUtils.csvRead("D://test.csv",User.class));
        return new ResultBuilder<>(jobj, StatusCode.SUCCESS);
    }
}
