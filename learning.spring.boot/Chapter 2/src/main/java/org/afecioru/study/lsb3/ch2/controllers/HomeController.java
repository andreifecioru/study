package org.afecioru.study.lsb3.ch2.controllers;

import lombok.Data;

import org.afecioru.study.lsb3.ch2.models.Video;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.afecioru.study.lsb3.ch2.services.VideoService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@Data
public class HomeController {

    private final VideoService videoService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("videos", videoService.getVideos());
        return "index";
    }

    @PostMapping("/new-video")
    public String newVideo(@ModelAttribute Video newVideo) {
        videoService.createVideo(newVideo);
        return "redirect:/";
    }
}
