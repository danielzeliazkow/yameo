package pl.yameo.recruitmenttask.service;

import javax.xml.parsers.SAXParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.yameo.recruitmenttask.dto.FileStatisticsDetails;
import pl.yameo.recruitmenttask.util.SaxFileHandler;

@Component
public class FileStatisticsService{
			
	@Autowired
	private SAXParser saxParser;
		
	@Autowired
	private SaxFileService saxFileService;
	
	public FileStatisticsDetails getStatistics(String url)  {
		SaxFileHandler saxHandler  = new SaxFileHandler();	
		return saxFileService.parse(url, saxHandler, saxParser);
	}
	
	
	
}
