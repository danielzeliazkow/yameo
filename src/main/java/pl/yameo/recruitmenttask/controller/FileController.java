package pl.yameo.recruitmenttask.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.yameo.recruitmenttask.dto.FileStatisticsDetails;
import pl.yameo.recruitmenttask.service.FileStatisticsService;
import pl.yameo.recruitmenttask.dto.FileStatistics;

@RestController 
public class FileController {
	
	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileStatisticsService fileStatisticsService;	
	
	@PostMapping("analyze")
	public FileStatistics showStatiscics(@RequestParam("url") String url) {
		logger.info("Receiving request to analyze file {}", url);
		FileStatisticsDetails statiscics = fileStatisticsService.getStatistics(url);
		logger.info("File processed");
		return new FileStatistics(statiscics);
	}
}
