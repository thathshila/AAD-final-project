package org.example.netwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDTO {
    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime timestamp;
    private MessageType type;

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}