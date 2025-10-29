package com.schoolconnect.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.schoolconnect.app.entity.ChatRoom;
import com.schoolconnect.app.entity.ChatRoomUser;
import com.schoolconnect.app.entity.User;

@Repository
public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, UUID> {
    
    @Query("SELECT cru FROM ChatRoomUser cru WHERE cru.chatRoom.id = :roomId")
    List<ChatRoomUser> findByChatRoomId(@Param("roomId") UUID roomId);
    
    @Query("SELECT cru.user FROM ChatRoomUser cru WHERE cru.chatRoom.id = :roomId")
    List<User> findUsersByChatRoomId(@Param("roomId") UUID roomId);
    
    @Query("SELECT COUNT(cru) > 0 FROM ChatRoomUser cru WHERE cru.chatRoom.id = :roomId AND cru.user.id = :userId")
    boolean existsByChatRoomIdAndUserId(@Param("roomId") UUID roomId, @Param("userId") Long userId);
    
    Optional<ChatRoomUser> findByChatRoomIdAndUserId(UUID chatRoomId, Long userId);
    
    @Query("SELECT cru.chatRoom FROM ChatRoomUser cru WHERE cru.user.id = :userId")
    List<ChatRoom> findChatRoomsByUserId(@Param("userId") Long userId);
}
