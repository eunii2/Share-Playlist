package com.msp.playlist.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PlaylistRequestDto {
    public static String name;
    private String description;
    private Long userID;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void setId(Object id) {
    }

    public void setName(Object name) {
    }

    public void setUserId(Object userId) {
    }

    public Object getName() {
        return null;
    }

    public Object getUserId() {
        return null;
    }

/*    public Object getName() {
        return null;
    }

    public Object getDescription() {
        return null;
    }

    public Object getUserId() {
        return null;
    }

    public void setDescription(Object description) {
    }

    public void setCreatedAt(Object createdAt) {
    }

    public void setUpdatedAt(Object updatedAt) {
    }
}*/

    //Getters and Setters
}

