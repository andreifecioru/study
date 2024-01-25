package org.afecioru.study.mmc.astro.models;

import java.util.List;


public record AstroResponse(
    int number,
    String message,
    List<Assignment> people) {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

}
