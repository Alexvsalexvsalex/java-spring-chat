package com.chat.controllers;

import java.util.Map;

import com.chat.domain.Room;
import com.chat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author
 */
@Controller
public class HomePageController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        Iterable<Room> users = roomRepository.findAll();
        model.put("users", users);
        return "home";
    }

    @GetMapping("restricted")
    public String restricted() {
        return "";
    }


    @GetMapping("/create_room/send_message")
    public String createRoom(Model model) {
        return "create_room";
    }

    @PostMapping("/create_room")
    public String createPostRoom(@RequestParam String title, Model model) {
        Room room = new Room(title);
        roomRepository.save(room);
        return "redirect:/";
    }

    @GetMapping("/create_room")
    public String createRoom() {
        return "create_room";
    }
}
