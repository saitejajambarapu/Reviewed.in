package com.example.Reviewed.Dto;

public class ContentRequestDto {
    private String title;
    private Long pageNumber;
    private Boolean isApi=false;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Boolean getIsApi() {
        return isApi;
    }
    public void setIsApi(Boolean isApi) {
        this.isApi = isApi;
    }
}
