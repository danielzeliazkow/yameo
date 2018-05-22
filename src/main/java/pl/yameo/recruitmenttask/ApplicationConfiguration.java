package pl.yameo.recruitmenttask;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.xml.sax.SAXException;

import pl.yameo.recruitmenttask.exception.CommonException;

@Configuration
public class ApplicationConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);
	
	@Bean
	public SAXParserFactory saxParserFactory() {
		return SAXParserFactory.newInstance();
	}
	
	@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "prototype")
	@Bean
	public SAXParser saxParser(SAXParserFactory saxParserFactory) {
		try {
			return saxParserFactory.newSAXParser();
		} catch (ParserConfigurationException | SAXException e) {
			logger.error("Couldn't create SAXParser");
			throw new CommonException(e);
		}
	}
}
