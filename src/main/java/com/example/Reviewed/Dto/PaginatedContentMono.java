package com.example.Reviewed.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PaginatedContentMono {

    @JsonProperty("Search")
    private List<ContentDto> contentDtoList;
    private String totalResults;
    @JsonProperty("Response")
    private Boolean Response;

    public List<ContentDto> getContentDtoList() {
        return contentDtoList;
    }

    public void setContentDtoList(List<ContentDto> contentDtoList) {
        this.contentDtoList = contentDtoList;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public Boolean getResponse() {
        return Response;
    }

    public void setResponse(Boolean response) {
        Response = response;
    }


}
