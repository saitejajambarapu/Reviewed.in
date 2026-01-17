package com.example.Reviewed.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PaginatedDto {

    private List<ContentDtoWithUserInteractions> contents;

    private Long totalResults;

    private Boolean isApi;

    public List<ContentDtoWithUserInteractions> getContents() {
        return contents;
    }

    public void setContents(List<ContentDtoWithUserInteractions> contents) {
        this.contents = contents;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public Boolean getApi() {
        return isApi;
    }

    public void setApi(Boolean api) {
        isApi = api;
    }


}
