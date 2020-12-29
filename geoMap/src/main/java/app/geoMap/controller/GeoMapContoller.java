package app.geoMap.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GeoMapContoller {
	
	@RequestMapping("/api/hi")
	public String hi() {
		return "<p> Hello world! <p>";
	}

}
