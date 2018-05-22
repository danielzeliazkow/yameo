package pl.yameo.recruitmenttask.service;

import java.io.IOException;

import java.io.InputStream;

import javax.xml.parsers.SAXParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import pl.yameo.recruitmenttask.dto.FileStatisticsDetails;
import pl.yameo.recruitmenttask.exception.XMLParsingException;
import pl.yameo.recruitmenttask.exception.FileAnalyzingException;
import pl.yameo.recruitmenttask.util.FileStreamUtil;
import pl.yameo.recruitmenttask.util.SaxFileHandler;

@Component
public class SaxFileService {
	
	private static final Logger logger = LoggerFactory.getLogger(SaxFileService.class);
	
	private static final String IO_EXCPETION_MESSAGE = "Can't obtain file";
	private static final String PARSING_EXCEPTION_MESSAGE = "Problem with parsing file";
	
	@Autowired
	private FileStreamUtil fileStreamUtil;
	
	public FileStatisticsDetails parse(String url, SaxFileHandler saxHandler, SAXParser saxParser) {
		try (InputStream inputStream = fileStreamUtil.createInputStream(url)){
			saxParser.parse(inputStream, saxHandler);
		} catch (IOException e) {
			logger.error(IO_EXCPETION_MESSAGE);
			throw new FileAnalyzingException(IO_EXCPETION_MESSAGE, e);
		} catch (SAXException e) {
			logger.error(PARSING_EXCEPTION_MESSAGE);
			throw new XMLParsingException(PARSING_EXCEPTION_MESSAGE, e);
		}    
		return saxHandler.generateStatisticsForFile();
	}

}
