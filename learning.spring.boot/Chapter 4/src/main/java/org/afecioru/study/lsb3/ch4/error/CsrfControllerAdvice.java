package org.afecioru.study.lsb3.ch4.error;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;


@ControllerAdvice
public class CsrfControllerAdvice {
    @ModelAttribute
    public void addCsrfTokenToModel(Model model, CsrfToken csrfToken) {
//        model.addAttribute("_csrf", csrfToken);
    }
}
