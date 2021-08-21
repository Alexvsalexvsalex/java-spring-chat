package com.chat.controllers;

import java.util.Map;

import com.chat.domain.Message;
import com.chat.domain.Room;
import com.chat.repository.MessageRepository;
import com.chat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author
 */
@Controller
public class RoomPageController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/room")
    public String room(Map<String, Object> model) {
        Iterable<Message> messages = messageRepository.findAll();
        model.put("messages", messages);
        return "room";
    }

    @PostMapping("/send_message")
    public String addMessage(@RequestParam String text, Model model) {
        Message message = new Message(text);
        messageRepository.save(message);
        return "redirect:/room";
    }
}
