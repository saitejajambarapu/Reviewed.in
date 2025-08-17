package com.example.Reviewed.Dto;

import com.example.Reviewed.model.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ReivewRepliesDto extends BaseEntity {
    private UserDto userDto;
    private String reply;
    private Long id;
}
