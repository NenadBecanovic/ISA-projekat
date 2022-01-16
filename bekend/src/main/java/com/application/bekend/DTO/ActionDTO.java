package com.application.bekend.DTO;

public class ActionDTO {

    private Long userId;
    private Long entityId;

    public ActionDTO(Long userId, Long entityId) {
        this.userId = userId;
        this.entityId = entityId;
    }

    public ActionDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}
