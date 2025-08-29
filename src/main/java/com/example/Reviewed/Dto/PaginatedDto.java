package com.example.Reviewed.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedDto {

    private List<ContentDtoWithUserInteractions> contents;

    private Long totalResults;

    private Boolean isApi;
}
