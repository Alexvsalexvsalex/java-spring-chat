package com.chat.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.chat.domain.Room;
import com.chat.domain.dto.MessageDto;
import com.chat.domain.dto.MessageStatisticDto;
import com.chat.repository.MessageRepository;
import com.chat.repository.RoomRepository;
import com.chat.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 */
@RestController
@RequestMapping("/api")
public class ApiController {


//    @PostMapping("/{roomId}/add_user")

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @GetMapping("/{roomId}/messages/{username}")
    public Map<String, List<String>> getAllMessagesFromRoomByUser(@PathVariable Long roomId,
            @PathVariable String username)
    {
        Optional<Room> roomO = roomRepository.findById(roomId);
        if (roomO.isEmpty()) {
            return null;
        }
        List<MessageDto> messages = messageRepository.findAllMessagesByRoom(roomO.get());
        messages = messages.stream().filter(m -> m.getAuthor().getUsername().equals(username))
                .collect(Collectors.toList());
        List<String> result = messages.stream().map(MessageDto::getText).collect(Collectors.toList());
        return Map.of("messages", result);
    }

    @GetMapping("/{roomId}/stats/top")
    public Map<String, List<MessageStatisticDto>> getTop10ActiveUsersInChat(@PathVariable Long roomId) {
        Optional<Room> roomO = roomRepository.findById(roomId);
        if (roomO.isEmpty()) {
            return null;
        }
        List<MessageDto> messages = messageRepository.findAllMessagesByRoom(roomO.get());
        Map<String, Long> countByUser = new HashMap<>();
        for (var msg : messages) {
            String username = msg.getAuthor().getUsername();
            countByUser.put(username, countByUser.getOrDefault(username, 0L) + 1);
        }

        List<String> users =
                countByUser.keySet().stream().sorted(Comparator.comparing(countByUser::get).reversed()).limit(10)
                        .collect(Collectors.toList());

        List<MessageStatisticDto> result =
                users.stream().map(u -> new MessageStatisticDto(u, countByUser.get(u))).collect(
                        Collectors.toList());
        return Map.of("top", result);
    }

    @GetMapping("/{roomId}/all-messages")
    public List<MessageDto> get(@PathVariable Long roomId, @RequestBody Date start, @RequestBody Date end) {
        Optional<Room> roomO = roomRepository.findById(roomId);
        if (roomO.isEmpty()) {
            return null;
        }
        List<MessageDto> messages = messageRepository.findAllMessagesByRoom(roomO.get());
        return null;
    }

}
