package com.schoolconnect.app.dto.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatMessageDTO {
    
    private String version = "1.0";
    private UUID id;
    private String type;
    private String subType;
    private ContextDTO context;
    private SenderDTO sender;
    private ReceiverDTO receiver;
    private ContentDTO content;
    private AuditDTO audit;
    private ExtensionsDTO extensions;
    
    public ChatMessageDTO() {}
    
    public static class ContextDTO {
        private UUID chatRoomId;
        private UUID threadRoot;
        private UUID replyTo;
        
        public ContextDTO() {}
        
        public UUID getChatRoomId() { return chatRoomId; }
        public void setChatRoomId(UUID chatRoomId) { this.chatRoomId = chatRoomId; }
        public UUID getThreadRoot() { return threadRoot; }
        public void setThreadRoot(UUID threadRoot) { this.threadRoot = threadRoot; }
        public UUID getReplyTo() { return replyTo; }
        public void setReplyTo(UUID replyTo) { this.replyTo = replyTo; }
    }
    
    public static class SenderDTO {
        private Long id;
        private String displayName;
        
        public SenderDTO() {}
        
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getDisplayName() { return displayName; }
        public void setDisplayName(String displayName) { this.displayName = displayName; }
    }
    
    public static class ReceiverDTO {
        private Long id;
        private String displayName;
        
        public ReceiverDTO() {}
        
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getDisplayName() { return displayName; }
        public void setDisplayName(String displayName) { this.displayName = displayName; }
    }
    
    public static class ContentDTO {
        private String type;
        private String body;
        private MetadataDTO metadata;
        
        public ContentDTO() {}
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getBody() { return body; }
        public void setBody(String body) { this.body = body; }
        public MetadataDTO getMetadata() { return metadata; }
        public void setMetadata(MetadataDTO metadata) { this.metadata = metadata; }
    }
    
    public static class MetadataDTO {
        private String fileUrl;
        private String mimeType;
        private Long size;
        private String thumbnailUrl;
        private Integer duration;
        
        public MetadataDTO() {}
        
        public String getFileUrl() { return fileUrl; }
        public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
        public String getMimeType() { return mimeType; }
        public void setMimeType(String mimeType) { this.mimeType = mimeType; }
        public Long getSize() { return size; }
        public void setSize(Long size) { this.size = size; }
        public String getThumbnailUrl() { return thumbnailUrl; }
        public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
        public Integer getDuration() { return duration; }
        public void setDuration(Integer duration) { this.duration = duration; }
    }
    
    public static class AuditDTO {
        private Instant timestamp;
        private String status;
        private boolean edited;
        private boolean deleted;
        
        public AuditDTO() {}
        
        public Instant getTimestamp() { return timestamp; }
        public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public boolean isEdited() { return edited; }
        public void setEdited(boolean edited) { this.edited = edited; }
        public boolean isDeleted() { return deleted; }
        public void setDeleted(boolean deleted) { this.deleted = deleted; }
    }
    
    public static class ExtensionsDTO {
        private List<String> reactions;
        private Map<String, Object> custom;
        
        public ExtensionsDTO() {}
        
        public List<String> getReactions() { return reactions; }
        public void setReactions(List<String> reactions) { this.reactions = reactions; }
        public Map<String, Object> getCustom() { return custom; }
        public void setCustom(Map<String, Object> custom) { this.custom = custom; }
    }
    
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getSubType() { return subType; }
    public void setSubType(String subType) { this.subType = subType; }
    public ContextDTO getContext() { return context; }
    public void setContext(ContextDTO context) { this.context = context; }
    public SenderDTO getSender() { return sender; }
    public void setSender(SenderDTO sender) { this.sender = sender; }
    public ReceiverDTO getReceiver() { return receiver; }
    public void setReceiver(ReceiverDTO receiver) { this.receiver = receiver; }
    public ContentDTO getContent() { return content; }
    public void setContent(ContentDTO content) { this.content = content; }
    public AuditDTO getAudit() { return audit; }
    public void setAudit(AuditDTO audit) { this.audit = audit; }
    public ExtensionsDTO getExtensions() { return extensions; }
    public void setExtensions(ExtensionsDTO extensions) { this.extensions = extensions; }
}