package sdi.limit.gateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class LimitController {
    @ResponseBody
    @RequestMapping("/a")
    public String apiATest() {
        return "this is a controller";
    }

    @ResponseBody
    @RequestMapping("/b")
    public String apiBTest() {
        return "this is b controller";
    }
}
