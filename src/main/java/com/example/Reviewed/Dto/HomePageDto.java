package com.example.Reviewed.Dto;

import com.example.Reviewed.model.ContentEntity;

import java.util.List;
import java.util.Map;

public class HomePageDto {

    private List<Map<String,List<ContentEntity>>> contentList;

    public List<Map<String, List<ContentEntity>>> getContentList() {
        return contentList;
    }

    public void setContentList(List<Map<String, List<ContentEntity>>> contentList) {
        this.contentList = contentList;
    }

        private Map<String,List<ContentDto>> topRatedMovie;

    private Map<String,List<ContentDto>> Movies;

    private Map<String,List<ContentDto>> series;

    private Map<String,List<ContentDto>> games;

    private Map<String,List<ContentDto>> recentReleases;

    public Map<String, List<ContentDto>> getTopRatedMovie() {
        return topRatedMovie;
    }

    public void setTopRatedMovie(Map<String, List<ContentDto>> topRatedMovie) {
        this.topRatedMovie = topRatedMovie;
    }

    public Map<String, List<ContentDto>> getMovies() {
        return Movies;
    }

    public void setMovies(Map<String, List<ContentDto>> movies) {
        Movies = movies;
    }

    public Map<String, List<ContentDto>> getSeries() {
        return series;
    }

    public void setSeries(Map<String, List<ContentDto>> series) {
        this.series = series;
    }

    public Map<String, List<ContentDto>> getGames() {
        return games;
    }

    public void setGames(Map<String, List<ContentDto>> games) {
        this.games = games;
    }

    public Map<String, List<ContentDto>> getRecentReleases() {
        return recentReleases;
    }

    public void setRecentReleases(Map<String, List<ContentDto>> recentReleases) {
        this.recentReleases = recentReleases;
    }
}
