package org.afecioru.study.lsb3.ch3.controllers;

import lombok.Data;
import org.afecioru.study.lsb3.ch3.services.VideoService;
import org.afecioru.study.lsb3.ch3.services.dto.SearchDTO;
import org.afecioru.study.lsb3.ch3.services.dto.VideoDTO;
import org.afecioru.study.lsb3.ch3.services.dto.MultiFieldSearchDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Data
@Controller
public class HomeController {
    private final VideoService videoService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("videos", videoService.getVideos());
        return "index";
    }

    @PostMapping("/new-video")
    public String createVideo(@ModelAttribute VideoDTO videoDTO) {
        videoService.addVideo(videoDTO);
        return "redirect:/";
    }

    @PostMapping("/multi-field-search")
    public String multiFieldSearch(@ModelAttribute MultiFieldSearchDTO multiFieldSearchDTO,
                                   Model model) {
        var videoDTOs = videoService.multiFieldSearch(multiFieldSearchDTO);
        model.addAttribute("videos", videoDTOs);
        return "index";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute SearchDTO searchDTO,
                         Model model) {
        var videoDTOs = videoService.search(searchDTO);
        model.addAttribute("videos", videoDTOs);
        return "index";
    }
}
