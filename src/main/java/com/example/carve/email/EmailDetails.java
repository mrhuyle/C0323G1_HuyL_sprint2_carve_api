package com.example.carve.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {
    private String username;
    private String msgBody;
    private String subject;
    private String attachment;
    private Map<String, Object> templateModel;
}
