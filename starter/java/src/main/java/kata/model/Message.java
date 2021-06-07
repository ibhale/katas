package kata.model;

import java.time.LocalDateTime;

public class Message {
    private String messageText;
    private LocalDateTime messageTime;

    public Message(String messageText) {
        this.messageText = messageText;
        this.messageTime = LocalDateTime.now();
    }

    public String getMessageText() {
        return this.messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDateTime getMessageTime() {
        return this.messageTime;
    }

    public void setMessageTime(LocalDateTime messageTime) {
        this.messageTime = messageTime;
    }
}
