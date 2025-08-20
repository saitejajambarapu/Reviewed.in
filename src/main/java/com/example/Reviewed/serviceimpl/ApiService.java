package com.example.Reviewed.serviceimpl;

import com.example.Reviewed.Dto.ContentDto;
import com.example.Reviewed.Dto.ContentDtoWithUserInteractions;
import com.example.Reviewed.Dto.PaginatedContentMono;
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

    public List<ContentDtoWithUserInteractions> fetchMovieByTitle(String title) {
        int pageNumber = 1;
        while(true){
            int finalPageNumber = pageNumber;
            Mono<PaginatedContentMono> contentMono =  webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("s", title)
                            .queryParam("page", finalPageNumber)
                            .queryParam("apikey", apiKey)
                            .build())
                    .retrieve()
                    .bodyToMono(PaginatedContentMono.class);
            PaginatedContentMono paginatedContentMono = contentMono.block();
            if(paginatedContentMono.getTotalResults()!=null && ((Integer.parseInt(paginatedContentMono.getTotalResults()))-(paginatedContentMono.getContentDtoList().size()*pageNumber)<=0)){
                contentEntity.saveEntity(paginatedContentMono);
                break;
            }else{
                if (paginatedContentMono.getTotalResults()!=null)  contentEntity.saveEntity(paginatedContentMono);

        }
            pageNumber+=1;
        }
        return  contentEntity.fetchContentAfterSaving(title);
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
