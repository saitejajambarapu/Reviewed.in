package com.example.Reviewed.serviceimpl;

import com.example.Reviewed.Dto.*;
import com.example.Reviewed.aspect.LoggerAspect;
import com.example.Reviewed.model.ContentEntity;
import com.example.Reviewed.model.UserContentInteraction;
import com.example.Reviewed.model.UserEntity;
import com.example.Reviewed.repository.ContentRepository;
import com.example.Reviewed.repository.UserContentInteractionRepo;
import com.example.Reviewed.service.ContentEntityInterface;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContentEntityImpl implements ContentEntityInterface {

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    @Lazy
    ApiService apiService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserContentInteractionRepo userContentInteractionRepo;

    @Autowired
    AuthServiceImpl authService;

    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    public void saveEntity(PaginatedContentMono paginatedContentMonoRes) {
        List<ContentEntity> contentEntityList =new ArrayList<>();
        Set<String> imdbIds = contentRepository.getExistingImdbIds();
        Set<String> newSetImdbId = new HashSet<>();
        for (ContentDto contentDtoRes : paginatedContentMonoRes.getContentDtoList()) {
            modelMapper.typeMap(ContentDto.class, ContentEntity.class)
                    .addMappings(mapper -> mapper.skip(ContentEntity::setId));
            ContentEntity contentEntity = modelMapper.map(contentDtoRes, ContentEntity.class);
            if (contentEntity.getRatings() != null) {
                contentEntity.getRatings().forEach(rating -> rating.setContent(contentEntity));
            }
            if(!imdbIds.contains(contentEntity.getImdbID()) && !newSetImdbId.contains(contentEntity.getImdbID())){
                newSetImdbId.add(contentEntity.getImdbID());
                contentEntityList.add(contentEntity);
            }
        }
        if(contentEntityList.size()>0) saveContentEntity(contentEntityList);
    }

    public void saveContentEntity(List<ContentEntity> contentEntityList) {

        List<ContentEntity> contentEntity1 = contentRepository.saveAll(contentEntityList);
//        List<ContentDtoWithUserInteractions> contentDtos = contentEntity1.stream()
//                .map(entity -> modelMapper.map(entity, ContentDtoWithUserInteractions.class))
//                .toList();
//        System.out.println();
    }

    public Boolean fetchSkippedList(Long startingValue,ContentRequestDto requestDto){
       try {
           for(Long i=startingValue+1;i<=requestDto.getPageNumber();i++){
               ContentRequestDto requestDto1 = new ContentRequestDto();
               requestDto1.setIsApi(requestDto.getIsApi());
               requestDto1.setPageNumber(i);
               requestDto1.setTitle(requestDto.getTitle());
               apiService.fetchContentList(requestDto1);
           }
           return true;
       }catch (Exception ex){
           logger.info("Exception occured inn fetchSkippedList: {}", ex);
       }
       return false;
    }

    public PaginatedDto fetchcontentByName(ContentRequestDto contentRequestDto) {
        UserEntity user = authService.getCurrentUser();
        List<ContentDtoWithUserInteractions> contentDtoList = new ArrayList<>();
        PaginatedDto paginatedDto =new PaginatedDto();
        List<ContentEntity> contentEntity = new ArrayList<>();
        if(!contentRequestDto.getIsApi()){
           contentEntity = contentRepository.findByTitleLike(contentRequestDto.getTitle());
        }

        if (contentEntity.isEmpty()) {
             paginatedDto = apiService.fetchMovieByTitle(contentRequestDto);
            return paginatedDto;
        }
        modelMapper.typeMap(ContentEntity.class,ContentDtoWithUserInteractions.class)
                .addMappings(mapper -> mapper.skip(ContentDtoWithUserInteractions::setRating));
        mapContentEntityToDto(contentEntity, contentDtoList);
        contentDtoList = contentDtoList.stream()
                .map(this::checkContentInteractions)
                .collect(Collectors.toList());
        paginatedDto.setApi(false);
        paginatedDto.setTotalResults(1l);
        paginatedDto.setContents(contentDtoList);
        return paginatedDto;
    }

    public List<ContentDtoWithUserInteractions> fetchContentAfterSaving(String title) {
        UserEntity user = authService.getCurrentUser();
        List<ContentDtoWithUserInteractions> contentDtoList = new ArrayList<>();
        List<ContentEntity> contentEntity = contentRepository.findByTitleAsc(title);
        mapContentEntityToDto(contentEntity, contentDtoList);
        contentDtoList = contentDtoList.stream()
                .map(this::checkContentInteractions)
                .collect(Collectors.toList());
        return contentDtoList;
    }

    public List<ContentDtoWithUserInteractions> mapContentEntityToDto(List<ContentEntity> contentEntity, List<ContentDtoWithUserInteractions> contentDtoList) {
        contentEntity.forEach(content -> {
            ContentDtoWithUserInteractions contentDto = modelMapper.map(content, ContentDtoWithUserInteractions.class);
            contentDto.setRating(0);
            contentDtoList.add(contentDto);
        });
        return contentDtoList;
    }

    @Override
    public ContentDtoWithUserInteractions fetchContentByImdbId(String imdbId) {
        ContentEntity contentEntity = contentRepository.findByImdbID(imdbId);
        if (contentEntity.getActors() != null) {
            modelMapper.typeMap(ContentEntity.class,ContentDtoWithUserInteractions.class)
                    .addMappings(mapper -> mapper.skip(ContentDtoWithUserInteractions::setRating));
            ContentDtoWithUserInteractions contentDto = modelMapper.map(contentEntity, ContentDtoWithUserInteractions.class);
            contentDto.setRating(0);
            return checkContentInteractions(contentDto);
        }
        else {
            ContentEntity savedContentEntity =  apiService.fetchContentByImdbId(imdbId);
            modelMapper.typeMap(ContentEntity.class,ContentDtoWithUserInteractions.class)
                    .addMappings(mapper -> mapper.skip(ContentDtoWithUserInteractions::setRating));
            ContentDtoWithUserInteractions contentDto = modelMapper.map(savedContentEntity, ContentDtoWithUserInteractions.class);
            contentDto.setRating(0);
            return checkContentInteractions(contentDto);
        }
    }

    private ContentDtoWithUserInteractions checkContentInteractions(ContentDtoWithUserInteractions contentDtoWithUserInteractions) {
        UserEntity user = authService.getCurrentUser();
        UserContentInteraction userContentInteraction = userContentInteractionRepo.findByContentIdAndUserId(user.getId(), contentDtoWithUserInteractions.getId());
        if (userContentInteraction != null) {
            contentDtoWithUserInteractions.setRating(userContentInteraction.getRating());
            contentDtoWithUserInteractions.setLiked(userContentInteraction.isLiked());
            contentDtoWithUserInteractions.setWatched(userContentInteraction.isWatched());
            if (userContentInteraction.getReview() != null)
                contentDtoWithUserInteractions.setReview(userContentInteraction.getReview().getReview());
        }
        return contentDtoWithUserInteractions;
    }

    public ContentEntity updateContentDetails(ContentDto contentDto){
        ContentEntity contentEntity = contentRepository.findByImdbID(contentDto.getImdbID());
        modelMapper.typeMap(ContentDto.class, ContentEntity.class)
                .addMappings(mapper -> mapper.skip(ContentEntity::setId));
        ContentEntity contentEntity1 = modelMapper.map(contentDto, ContentEntity.class);
        contentEntity1.setId(contentEntity.getId());
        return contentRepository.save(contentEntity1);
    }
}
