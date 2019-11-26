package com.zsf.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zsf
 * @create 2019-10-25 11:52
 */
@Controller
public class TestController {

    @RequestMapping("hello")
    @ResponseBody
    public String test() {
        return "hello spring-boot";
    }
}
