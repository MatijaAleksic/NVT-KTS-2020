package app.geoMap.helper;

import  app.geoMap.dto.NewsDTO;
import  app.geoMap.model.News;


public class NewsMapper implements MapperInterface<News, NewsDTO>{

	@Override
    public News toEntity(NewsDTO dto) {
        return new News(dto.getTitle(), dto.getCreationDate());
    }
	
    @Override
    public NewsDTO toDto(News entity) {
        return new NewsDTO(entity.getId(), entity.getTitle(), entity.getCreationDate());
    }
}
