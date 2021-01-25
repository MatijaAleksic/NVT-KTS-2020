package app.geoMap.controller;

import java.util.ArrayList;
import java.util.List;

import app.geoMap.dto.ImageDTO;

public class ImageDTOList {
	private List<ImageDTO> images;

	    public ImageDTOList() {
	    	images = new ArrayList<>();
	    }

		public List<ImageDTO> getImages() {
			return images;
		}

		public void setImages(List<ImageDTO> images) {
			this.images = images;
		}
	
}
