package pl.yameo.recruitmenttask.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pl.yameo.recruitmenttask.exception.FileAnalyzingException;

@Component
public class FileStreamUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileStreamUtil.class);
	
	private static final String URL_MALFORMED = "File URL is malformed";
	private static final String IO_EXCPETION_MESSAGE = "Can't obtain file";
	
	public InputStream createInputStream(String stringUrl) {
		InputStream httpInputStream = null;
		try {
			URL url = new URL(stringUrl);
			httpInputStream = url.openStream();
		} 
		catch (MalformedURLException e) {
			logger.error(URL_MALFORMED);
			throw new FileAnalyzingException(URL_MALFORMED, e);
		}
		catch (IOException e) {
			logger.error(IO_EXCPETION_MESSAGE);
			throw new FileAnalyzingException(IO_EXCPETION_MESSAGE, e);
		} 
		return new BufferedInputStream(httpInputStream);
	}
}
