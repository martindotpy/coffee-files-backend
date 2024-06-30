package xyz.cupscoffee.backend.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/folders")
@AllArgsConstructor
public class FolderController {
    private final HttpSession session;
}
