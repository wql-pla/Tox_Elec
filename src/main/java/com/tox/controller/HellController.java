package com.tox.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wHolmes on 2017/9/4.
 */
@RestController
public class HellController {
        @RequestMapping("/hello")
        public String index() {
            return "Hello World";
        }
}
