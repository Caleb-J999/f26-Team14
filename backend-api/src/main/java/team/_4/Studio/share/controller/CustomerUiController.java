package team._4.Studio.share.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerUiController {

    @GetMapping("/")
    public String home() {
        return "index";
    }
}