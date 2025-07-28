package dev.afecioru.annotations;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);

        Manager manager = appContext.getBean("manager", Manager.class);
        System.out.println(manager);
    }
}
