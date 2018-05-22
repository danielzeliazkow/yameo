package pl.yameo.recruitmenttask.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import pl.yameo.recruitmenttask.dto.FileStatisticsDetails;

@Component
public class SaxFileHandler extends DefaultHandler {
	
	private static final int SCALE = 3;
	
	private static final String ELEMENT_ROW = "row";
	private static final String ATTRIBUTE_CREATION_DATE = "CreationDate";
	private static final String ATTRIBUTE_SCORE="Score";	
			
	private LocalDateTime firstPostDate;
	private LocalDateTime lastPostDate;
	private long totalPostsCount;
	private long scoreSum;

	public FileStatisticsDetails generateStatisticsForFile() {
		return new FileStatisticsDetails(firstPostDate, lastPostDate, totalPostsCount, calculateAvarageScore());
	}
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equalsIgnoreCase(ELEMENT_ROW)) {
			totalPostsCount++;
			analyzeCreationDate(attributes.getValue(ATTRIBUTE_CREATION_DATE));
			scoreSum += Long.valueOf(attributes.getValue(ATTRIBUTE_SCORE));
		}
	}
	
	private void analyzeCreationDate(String stringDate) {
		LocalDateTime date = LocalDateTime.parse(stringDate);
		
		if(totalPostsCount==1) {
			firstPostDate = date;
			lastPostDate = date;
		}
		else {
			if(date.isBefore(firstPostDate)) {
				firstPostDate = date;
			}
			if(date.isAfter(lastPostDate)) {
				lastPostDate = date;
			}
		}
	}
	
	private BigDecimal calculateAvarageScore() {
		return BigDecimal.valueOf(scoreSum).divide(BigDecimal.valueOf(totalPostsCount),SCALE,RoundingMode.HALF_UP);
	}
}
