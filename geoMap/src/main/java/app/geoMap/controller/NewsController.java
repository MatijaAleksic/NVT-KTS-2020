package app.geoMap.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.geoMap.dto.NewsDTO;
import app.geoMap.helper.NewsMapper;
import app.geoMap.model.News;
import app.geoMap.service.NewsService;


@RestController
@RequestMapping(value =  "/api/news", produces = MediaType.APPLICATION_JSON_VALUE)
public class NewsController {
	
	@Autowired
    private NewsService NewsService;

    private NewsMapper NewsMapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        List<News> news = NewsService.findAll();

        return new ResponseEntity<>(toNewsDTOList(news), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<NewsDTO> getNews(@PathVariable Long id){
    	News news = NewsService.findOne(id);
        if(news == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(NewsMapper.toDto(news), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsDTO> createNews(@RequestBody NewsDTO newsDTO){
    	News news;
        try {
        	news = NewsService.create(NewsMapper.toEntity(newsDTO));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(NewsMapper.toDto(news), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewsDTO> updateNews(@RequestBody NewsDTO newsDTO, @PathVariable Long id){
    	News news;
        try {
        	news = NewsService.update(NewsMapper.toEntity(newsDTO), id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(NewsMapper.toDto(news), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteNews(@PathVariable Long id){
        try {
        	NewsService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public NewsController() {
        NewsMapper = new NewsMapper();
    }

    private List<NewsDTO> toNewsDTOList(List<News> newss){
        List<NewsDTO> newsDTO = new ArrayList<>();
        for (News news : newss) {
        	newsDTO.add(NewsMapper.toDto(news));
        }
        return newsDTO;
    }
}
