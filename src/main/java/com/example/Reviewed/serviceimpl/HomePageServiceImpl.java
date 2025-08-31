package com.example.Reviewed.serviceimpl;

import com.example.Reviewed.Dto.HomePageDto;
import com.example.Reviewed.model.ContentEntity;
import com.example.Reviewed.model.HomePageSectionsEntity;
import com.example.Reviewed.repository.ContentRepository;
import com.example.Reviewed.repository.DynamicQueryRepository;
import com.example.Reviewed.repository.HomePageSectionRepo;
import com.example.Reviewed.service.HomePageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomePageServiceImpl implements HomePageService {

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    DynamicQueryRepository dynamicQueryRepository;

    @Autowired
    HomePageSectionRepo homePageSectionRepo;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public HomePageDto getHomePageDetails() {
        HomePageDto homePageDto = new HomePageDto();
        List<Map<String,List<ContentEntity>>> finalPage = new ArrayList<>();
        List<HomePageSectionsEntity> homePageSectionsEntities = homePageSectionRepo.findAll();
        for(HomePageSectionsEntity homePageSectionsEntity : homePageSectionsEntities){
            try{
                List<ContentEntity> contentEntityList = new ArrayList<>();
                Map<String,List<ContentEntity>> mappingList= new HashMap<>();
                contentEntityList = dynamicQueryRepository.executeDynamicUserQuery(homePageSectionsEntity.getQuery());
                mappingList.put(homePageSectionsEntity.getColumnName(),contentEntityList);
                finalPage.add(mappingList);
            }catch (Exception ex){
                System.out.println(ex);
            }

        }
        homePageDto.setContentList(finalPage);
        return homePageDto;
    }
}
