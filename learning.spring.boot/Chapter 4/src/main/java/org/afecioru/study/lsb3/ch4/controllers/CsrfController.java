package org.afecioru.study.lsb3.ch4.controllers;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfController {
    @GetMapping("/csrf/token")
    public CsrfToken getToken(CsrfToken token) {
        return token;
    }
}
