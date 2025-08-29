package com.example.Reviewed.serviceimpl;

import com.example.Reviewed.Dto.*;
import com.example.Reviewed.model.ContentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ApiService {
    private final WebClient webClient;
    @Value("${apikey}")
    private String apiKey;

    @Autowired
    ContentEntityImpl contentEntity;


    public ApiService(WebClient.Builder builder,
                       @Value("${omdb.api.url}") String apiUrl) {
        this.webClient = builder.baseUrl(apiUrl).build();
    }

    public PaginatedDto fetchMovieByTitle(ContentRequestDto contentRequestDto) {
            Mono<PaginatedContentMono> contentMono =  webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("s", contentRequestDto.getTitle())
                            .queryParam("page", contentRequestDto.getPageNumber())
                            .queryParam("apikey", apiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(PaginatedContentMono.class);
            PaginatedContentMono paginatedContentMono = contentMono.block();
            if(paginatedContentMono.getResponse()){
                contentEntity.saveEntity(paginatedContentMono);
            }else{
                if (paginatedContentMono.getTotalResults()!=null)  contentEntity.saveEntity(paginatedContentMono);

        }
            PaginatedDto paginatedDto = new PaginatedDto();
            paginatedDto.setIsApi(true);
            paginatedDto.setTotalResults(Long.parseLong(paginatedContentMono.getTotalResults()));
            List<ContentDtoWithUserInteractions> contentDtoWithUserInteractions = contentEntity.fetchContentAfterSaving(contentRequestDto.getTitle());
            paginatedDto.setContents(contentDtoWithUserInteractions);
        return  paginatedDto;
    }

    public ContentEntity fetchContentByImdbId(String imdbId) {
            Mono<ContentDto> contentMono =  webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("i", imdbId)
                            .queryParam("apikey", apiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(ContentDto.class);
            ContentDto contentDto = contentMono.block();
        return  contentEntity.updateContentDetails(contentDto);
    }


}
