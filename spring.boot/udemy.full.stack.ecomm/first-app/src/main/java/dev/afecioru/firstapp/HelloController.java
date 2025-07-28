package dev.afecioru.firstapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HelloController {

    @GetMapping("hello/{name}")
    public ResponseEntity<MessageResponse> hello(@PathVariable String name) {
        return ResponseEntity.ok(new MessageResponse("Hello " + name + "!"));
    }

    @PostMapping("hello")
    public ResponseEntity<MessageResponse> helloPost(@RequestBody String name) {
        return ResponseEntity.ok(new MessageResponse("Hello " + name + "!"));
    }
}
