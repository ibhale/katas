package kata.service.impl;

import kata.model.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageServiceImpl{
    private Map<String, List<Message>> messageMap;

    public MessageServiceImpl() {
        messageMap = new HashMap<>();
    }

    public boolean addMessage(String memberId, String messageText) {
        List<Message> messageList = messageMap.getOrDefault(memberId, new ArrayList<>());
        Message message = new Message(messageText);
        messageList.add(message);
        messageMap.put(memberId, messageList);
        return true;
    }

    public List<Message> getMessages(String memberId) {
        return messageMap.getOrDefault(memberId, new ArrayList<>());
    }
}
