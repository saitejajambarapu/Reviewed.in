package com.example.Reviewed.Dto;

import com.example.Reviewed.model.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class ReivewRepliesDto extends BaseEntity {
    private UserDto userDto;
    private String reply;
    private Long id;
//    List<ReivewRepliesDto> commentReplies;


    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
