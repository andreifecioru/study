package dev.afecioru;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationConfig.xml");
        UserManager userManager = (UserManager) appContext.getBean("userManager");
        userManager.printUserDetails();
    }
}