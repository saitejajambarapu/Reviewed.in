package com.example.Reviewed.serviceimpl;

import com.example.Reviewed.Dto.*;
import com.example.Reviewed.model.ContentEntity;
import com.example.Reviewed.model.UserContentInteraction;
import com.example.Reviewed.repository.ContentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiService {
    private final WebClient webClient;
    @Value("${apikey}")
    private String apiKey;

    @Autowired
    ContentEntityImpl contentEntity;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ContentRepository contentRepository;


    public ApiService(WebClient.Builder builder,
                       @Value("${omdb.api.url}") String apiUrl) {
        this.webClient = builder.baseUrl(apiUrl).build();
    }

    public PaginatedDto fetchMovieByTitle(ContentRequestDto contentRequestDto) {
        PaginatedContentMono paginatedContentMono = fetchContentList(contentRequestDto);
        PaginatedDto paginatedDto = new PaginatedDto();
        paginatedDto.setApi(true);
        if(paginatedContentMono.getContentDtoList()!=null){
            paginatedDto.setTotalResults(Long.parseLong(paginatedContentMono.getTotalResults()));
            List<ContentDtoWithUserInteractions> contentDtoWithUserInteractions = contentEntity.fetchContentAfterSaving(contentRequestDto.getTitle());
            paginatedDto.setContents(contentDtoWithUserInteractions);
        }


        return  paginatedDto;
    }

    public PaginatedContentMono fetchContentList(ContentRequestDto contentRequestDto){
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
            return paginatedContentMono;
        }else{
            if (paginatedContentMono.getTotalResults()!=null)  contentEntity.saveEntity(paginatedContentMono);
        }
        return paginatedContentMono;
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

    public ContentDto getContentByNameAndYear(String name, String year){
        ContentDtoWithUserInteractions contentDtoWithUserInteractions = new ContentDtoWithUserInteractions();
        Mono<ContentDto> contentMono =  webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("t", name)
                        .queryParam("y", year)
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(ContentDto.class);
        ContentDto contentDto = contentMono.block();
        ContentEntity contentEntity = contentRepository.findByImdbID(contentDto.getImdbID());
        ContentEntity content = new ContentEntity();
        if(contentEntity==null) {
            modelMapper.typeMap(ContentDto.class, ContentEntity.class)
                    .addMappings(mapper -> mapper.skip(ContentEntity::setId));
            contentEntity = modelMapper.map(contentDto, ContentEntity.class);
            content = contentRepository.save(contentEntity);
            contentEntity.setId(content.getId());
        }
        return contentDto;
    }

}
