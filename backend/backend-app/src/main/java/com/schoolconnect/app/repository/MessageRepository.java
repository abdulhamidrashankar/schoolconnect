package com.schoolconnect.app.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.schoolconnect.app.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    
    // Room timeline (roots only)
    @Query("SELECT m FROM Message m WHERE m.chatRoom.id = :roomId AND m.threadRoot IS NULL " +
           "ORDER BY m.createdAt DESC")
    Page<Message> findRoomTimeline(@Param("roomId") UUID roomId, Pageable pageable);
    
    // All messages in a room (including threads)
    @Query("SELECT m FROM Message m WHERE m.chatRoom.id = :roomId ORDER BY m.createdAt DESC")
    Page<Message> findAllRoomMessages(@Param("roomId") UUID roomId, Pageable pageable);
    
    // Replies to root
    @Query("SELECT m FROM Message m WHERE m.threadRoot.id = :rootId ORDER BY m.createdAt ASC")
    List<Message> findThreadReplies(@Param("rootId") UUID rootId);
    
    // Private chat between two users
    @Query("SELECT m FROM Message m WHERE m.subtype = 'PRIVATE' AND " +
           "((m.sender.id = :u1 AND m.receiver.id = :u2) OR (m.sender.id = :u2 AND m.receiver.id = :u1)) " +
           "ORDER BY m.createdAt ASC")
    List<Message> findPrivateChat(@Param("u1") Long user1, @Param("u2") Long user2);
    
    @Query("SELECT m FROM Message m WHERE m.subtype = 'PRIVATE' AND " +
           "((m.sender.id = :u1 AND m.receiver.id = :u2) OR (m.sender.id = :u2 AND m.receiver.id = :u1)) " +
           "ORDER BY m.createdAt DESC")
    Page<Message> findPrivateChatPaginated(@Param("u1") Long user1, @Param("u2") Long user2, Pageable pageable);
    
    // Count unread messages
    @Query("SELECT COUNT(m) FROM Message m WHERE m.chatRoom.id = :roomId AND m.receiver.id = :userId " +
           "AND m.status != 'READ'")
    long countUnreadMessages(@Param("roomId") UUID roomId, @Param("userId") Long userId);
    
    // All messages in a room ordered by creation time ascending
    @Query("SELECT m FROM Message m WHERE m.chatRoom.id = :roomId ORDER BY m.createdAt ASC")
    List<Message> findAllRoomMessagesAsc(@Param("roomId") UUID roomId);
}
