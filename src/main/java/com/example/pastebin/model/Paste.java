package com.example.pastebin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paste {
    private String content;
    private Long expiresAt;
    private Integer remainingViews;
}
