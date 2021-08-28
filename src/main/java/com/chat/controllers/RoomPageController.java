package com.chat.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.chat.domain.Message;
import com.chat.domain.Room;
import com.chat.domain.User;
import com.chat.domain.dto.MessageDto;
import com.chat.repository.MessageRepository;
import com.chat.repository.RoomRepository;
import com.chat.repository.UserDetailsRepository;
import com.chat.services.RoomService;
import com.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private RoomService roomService;
//    @GetMapping("/room")
//    public String room(Map<String, Object> model) {
//        Iterable<Message> messages = messageRepository.findAll();
//        model.put("messages", messages);
//        return "room";
//    }

    @GetMapping("/enter_room/{room}")
    public String enterRoom(@PathVariable String room, RedirectAttributes attributes) {
        Room currentRoom = roomService.getRoomById(room);
        if (!currentRoom.getPrivate() || currentRoom.getUsers().stream().map(User::getId).collect(Collectors.toList()).contains(userService.getCurrentUser().getId())) {
            return "redirect:/room/" + room;
        } else {
            attributes.addFlashAttribute("error", "Приватная комната, у вас нет доступа");
            return "redirect:/myerror";
        }
    }

    @PostMapping("/{roomId}/change")
    public String change(@PathVariable String roomId) {
        Room room = roomService.getRoomById(roomId);
        room.setPrivate(!room.getPrivate());

        roomRepository.save(room);
        return "redirect:/room/" + roomId;
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
        model.put("is_admin",
                roomService.getRoomAdmin(currentRoom).getId().equals(userService.getCurrentUser().getId()));
        model.put("users", roomService.getUsers(currentRoom));
        model.put("switched_type", currentRoom.getPrivate() ? "Публичная" : "Приватная");
        model.put("type", currentRoom.getPrivate() ? "Приватная" : "Публичная");
        model.put("room_name", currentRoom.getTitle());
//        model.put("error", exception);
        return "room";
    }

    @PostMapping("/{room}/send_message")
    public String addMessage(
            @RequestParam String text,
            @PathVariable String room)
    {
        User user = userService.getCurrentUser();
        var currentRoom = roomService.getRoomById(room);

        Date date = new Date();
        Message message = new Message(text, user, currentRoom, date);
        messageRepository.save(message);
        return "redirect:/room/{room}";
    }

    @PostMapping("/{roomId}/add_user")
    public String addUser(@PathVariable String roomId, @RequestParam String username, Map<String, Object> model,
            RedirectAttributes redirectAttributes)
    {
        try {
            User user = (User) userService.loadUserByUsername(username);
            Room room = roomService.getRoomById(roomId);

            roomService.addUser(room, user);

        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "Нет такого пользователя");

        }
        return "redirect:/room/" + roomId;
    }

    @PostMapping("/{roomId}/change_name")
    public String changeName(@PathVariable String roomId, @RequestParam String name) {
        Room room = roomService.getRoomById(roomId);
        room.setTitle(name);
        roomRepository.save(room);
        return "redirect:/room/" + roomId;
    }
}
