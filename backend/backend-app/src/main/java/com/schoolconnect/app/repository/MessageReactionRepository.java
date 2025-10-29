package com.schoolconnect.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.schoolconnect.app.entity.MessageReaction;

@Repository
public interface MessageReactionRepository extends JpaRepository<MessageReaction, UUID> {
    
    @Query("SELECT mr FROM MessageReaction mr WHERE mr.message.id = :messageId")
    List<MessageReaction> findByMessageId(@Param("messageId") UUID messageId);
    
    @Query("SELECT mr FROM MessageReaction mr WHERE mr.message.id = :messageId AND mr.user.id = :userId " +
           "AND mr.reaction = :reaction")
    Optional<MessageReaction> findByMessageIdAndUserIdAndReaction(
        @Param("messageId") UUID messageId, 
        @Param("userId") Long userId, 
        @Param("reaction") String reaction
    );
    
    void deleteByMessageIdAndUserIdAndReaction(UUID messageId, Long userId, String reaction);
}
