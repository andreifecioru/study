package org.afecioru.study.lsb3.ch5.controllers;

import lombok.Data;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.afecioru.study.lsb3.ch5.services.BookService;
import org.afecioru.study.lsb3.ch5.controllers.dtos.BookDTO;


@Data
@Controller
@RequestMapping("/")
public class HomeController {
    private final BookService bookService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookService.allBooks());
        return "index";
    }

    @PostMapping("add")
    public String addBook(@ModelAttribute BookDTO bookDTO) {
        bookService.addBook(bookDTO);
        return "redirect:/";
    }

    @PostMapping("delete/{bookId}")
    public String deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/";
    }
}
