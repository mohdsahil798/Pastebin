package com.example.pastebin.controller;

import org.springframework.ui.Model;
import com.example.pastebin.model.Paste;
import com.example.pastebin.service.PasteRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PasteViewController {

    private final PasteRedisService redisService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/p/{id}")
    public String view(@PathVariable String id, Model model) {
        Paste paste = redisService.fetchPaste(id, System.currentTimeMillis());
        model.addAttribute("content", paste.getContent());
        return "paste";
    }
}

