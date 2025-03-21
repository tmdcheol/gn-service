package com.gn.gn_repair.post.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/posts")
public class PostWebController {

    @GetMapping
    public String view() {
        return "posts/postList";
    }

}
