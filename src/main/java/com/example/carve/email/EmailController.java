package com.example.carve.email;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    // Sending a simple Email
    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody EmailDetails details)
    {
        String status = emailService.sendSimpleEmail(details);
        if (status.equals("Success")) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(status);
        }

    }

    // Sending email with attachment
    @PostMapping("/send-attachment")
    public ResponseEntity<String> sendMailWithAttachment(
            @RequestBody EmailDetails details)
    {
        String status = emailService.sendMailWithAttachment(details);
        if (status.equals("Success")) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(status);
        }
    }

    @PostMapping("/send-template")
    public ResponseEntity<String> sendMessageUsingFreemarkerTemplate(
            @RequestBody EmailDetails details)
    {
        String status = emailService.sendMessageUsingFreemarkerTemplate(details);
        if (status.equals("Success")) {
            return ResponseEntity.ok(status);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(status);
        }
    }
}
