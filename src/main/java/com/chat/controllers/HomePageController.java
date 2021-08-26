package com.chat.controllers;

import java.util.List;
import java.util.Map;

import com.chat.config.WebSecurityConfig;
import com.chat.domain.Room;
import com.chat.domain.User;
import com.chat.repository.RoomRepository;
import com.chat.repository.UserDetailsRepository;
import com.chat.services.RoomService;
import com.chat.services.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
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

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String index() {
        return "greeting";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/main")
    public String home(Map<String, Object> model) {
        Iterable<Room> rooms = roomRepository.findAll();
        model.put("rooms", rooms);

        model.put("user", userService.getCurrentUser().getUsername());

        return "/main";
    }

    @PostMapping("/change_name")
    public String change_name(@RequestParam String name) {
        userService.changeUsername(name);
        return "redirect:/main";
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
        List<User> userLst = List.of(userService.getCurrentUser());
        Room room = new Room(title, userService.getCurrentUser(), userLst);
        roomRepository.save(room);

        User user = userService.getCurrentUser();
        user.getRooms().add(room);
        return "redirect:/main";
    }

    @GetMapping("/create_room")
    public String createRoom() {
        return "create_room";
    }
}
