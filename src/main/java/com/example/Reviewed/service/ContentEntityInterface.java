package com.example.Reviewed.service;

import com.example.Reviewed.Dto.ContentDtoWithUserInteractions;

public interface ContentEntityInterface {

    ContentDtoWithUserInteractions fetchContentByImdbId(String imdbId);
}
