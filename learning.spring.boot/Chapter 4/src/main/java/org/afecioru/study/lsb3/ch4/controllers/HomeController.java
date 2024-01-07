package org.afecioru.study.lsb3.ch4.controllers;

import lombok.Data;

import org.afecioru.study.lsb3.ch4.config.oauth2.google.YouTubeExchange;
import org.afecioru.study.lsb3.ch4.controllers.dto.SearchDTO;
import org.afecioru.study.lsb3.ch4.controllers.dto.VideoDTO;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.afecioru.study.lsb3.ch4.services.VideoService;
import org.springframework.web.bind.annotation.PostMapping;


@Data
@Controller
public class HomeController {

    private final VideoService videoService;
    private final YouTubeExchange youTube;

    @GetMapping("/")
    public String index(Model model, CsrfToken csrfToken) {
        model.addAttribute("videos", videoService.getVideos());
        return "index";
    }

    @GetMapping("/youtube")
    public String youTubeVideos(Model model) {
        model.addAttribute(
            "channelVideos",
            youTube.channelVideos(
                "UCjukbYOd6pjrMpNMFAOKYyw",
                10, YouTubeExchange.Sort.VIEW_COUNT)
        );

        return "youtube";
    }

    @PostMapping("/create")
    public String createVideo(@ModelAttribute VideoDTO videoDTO) {
        videoService.createVideo(videoDTO);
        return "redirect:/";
    }

    @PostMapping("/search")
    public String searchVideo(@ModelAttribute SearchDTO searchDTO,
                              Model model,
                              CsrfToken csrfToken) {
        model.addAttribute("videos", videoService.searchVideo(searchDTO));
        return "index";
    }
}
