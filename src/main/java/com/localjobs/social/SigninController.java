package com.localjobs.social;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.localjobs.utils.SecurityUtils;

@Controller
public class SigninController {

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signin() throws Exception {
        String username = SecurityUtils.getCurrentLoggedInUsername();

        if (StringUtils.hasLength(username) && !"anonymousUser".equals(username)) {
            return "redirect:/home";
        }
        return "signin";

    }
}