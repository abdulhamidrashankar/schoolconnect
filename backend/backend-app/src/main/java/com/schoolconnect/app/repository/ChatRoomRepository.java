package com.schoolconnect.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.schoolconnect.app.entity.ChatRoom;
import java.util.Optional;
import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
    
    @Query("SELECT cr FROM ChatRoom cr WHERE cr.isGroup = false AND cr.id IN " +
           "(SELECT cru1.chatRoom.id FROM ChatRoomUser cru1 WHERE cru1.user.id = :userId1) AND cr.id IN " +
           "(SELECT cru2.chatRoom.id FROM ChatRoomUser cru2 WHERE cru2.user.id = :userId2)")
    Optional<ChatRoom> findPrivateChatRoom(@Param("userId1") Long userId1, @Param("userId2") Long userId2);
    
    @Query("SELECT cr FROM ChatRoom cr JOIN ChatRoomUser cru ON cr.id = cru.chatRoom.id " +
           "WHERE cru.user.id = :userId ORDER BY cr.createdAt DESC")
    List<ChatRoom> findUserChatRooms(@Param("userId") Long userId);
}
