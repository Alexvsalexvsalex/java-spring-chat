package com.chat.services;

import java.util.List;

import com.chat.domain.Room;
import com.chat.domain.User;
import com.chat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 */
@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public Room getRoomById(String roomName) {
        return roomRepository.findById(Long.valueOf(roomName)).orElseThrow(IllegalStateException::new);
    }

    public User getRoomAdmin(Room room) {
        return room.getUserAdmin();
    }

    public List<User> getUsers(Room room) {
        return room.getUsers();
    }
}
