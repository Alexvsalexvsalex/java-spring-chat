package com.chat.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.chat.domain.Message;
import com.chat.domain.Room;
import com.chat.domain.User;
import com.chat.domain.dto.MessageDto;
import com.chat.repository.MessageRepository;
import com.chat.repository.RoomRepository;
import com.chat.services.RoomService;
import com.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author
 */
@Controller
public class RoomPageController {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;
//    @GetMapping("/room")
//    public String room(Map<String, Object> model) {
//        Iterable<Message> messages = messageRepository.findAll();
//        model.put("messages", messages);
//        return "room";
//    }

    @GetMapping("/enter_room/{room}")
    public String enterRoom(@PathVariable String room) {

        return "redirect:/room/" + room;
    }

    @GetMapping("/room/{room}")
    public String getRoom(Map<String, Object> model, @PathVariable String room,
            @ModelAttribute("error") String error)
    {
        Room currentRoom = roomService.getRoomById(room);
        List<MessageDto> messagesByRoom = messageRepository.findAllMessagesByRoom(currentRoom);
        model.put("messages", messagesByRoom);
        model.put("roomId", room);
        model.put("admin", roomService.getRoomAdmin(currentRoom).getUsername());
        model.put("is_admin", roomService.getRoomAdmin(currentRoom).getId().equals(userService.getCurrentUser().getId()));
        model.put("users", roomService.getUsers(currentRoom));

//        model.put("error", exception);
        return "room";
    }

//    @MessageMapping("/chat.sendMessage")
//    @SendTo("/topic/public")

    @PostMapping("/{room}/send_message")
    public String addMessage(
            @RequestParam String text,
            @PathVariable String room,
            Model model,
            HttpServletRequest request)
    {
        User user = userService.getCurrentUser();
        var currentRoom = roomService.getRoomById(room);
        Message message = new Message(text, user, currentRoom);
        messageRepository.save(message);
        return "redirect:/room/{room}";
    }

    @PostMapping("/{roomId}/add_user")
    public String addUser(@PathVariable String roomId, @RequestParam  String username, Map<String, Object> model, RedirectAttributes redirectAttributes) {
        try {
            User user = (User) userService.loadUserByUsername(username);
            Room room = roomService.getRoomById(roomId);
            user.addRoom(room);
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "Нет такого пользователя");

        }
        return "redirect:/room/" + roomId;
    }
}
