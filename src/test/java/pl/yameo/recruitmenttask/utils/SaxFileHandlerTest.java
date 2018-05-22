package pl.yameo.recruitmenttask.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import pl.yameo.recruitmenttask.dto.FileStatisticsDetails;
import pl.yameo.recruitmenttask.util.SaxFileHandler;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
public class SaxFileHandlerTest {
	
	private static final String ELEMENT_ROW = "row";
	private static final String ATTRIBUTE_CREATION_DATE = "CreationDate";
	private static final String ATTRIBUTE_SCORE="Score";	
	
	private static final String FIRST_DATE = "2015-01-05T20:21:41.083";
	private static final String LAST_DATE = "2018-03-05T20:21:46.083";
	
	@Mock
	Attributes attributes;
		
	@Test
	public void testGenerateStatistics() throws SAXException {
		SaxFileHandler saxFileHandler = new SaxFileHandler();
		List<TestRow> testRows = generateTestRows();
			
		for(TestRow row : testRows) {
			when(attributes.getValue(ATTRIBUTE_CREATION_DATE)).thenReturn(row.creationDate);
			when(attributes.getValue(ATTRIBUTE_SCORE)).thenReturn(row.score);
			saxFileHandler.startElement(null, null, ELEMENT_ROW, attributes);
		}
		
		FileStatisticsDetails statistics = saxFileHandler.generateStatisticsForFile();
		assertEquals(testRows.size(), statistics.getTotalPosts());
		assertEquals(0, new BigDecimal("4.6670").compareTo(statistics.getAvgScore()));
		assertEquals(LocalDateTime.parse(FIRST_DATE), statistics.getFirstPostDate());
		assertEquals(LocalDateTime.parse(LAST_DATE), statistics.getLastPostDate());	
	}
	
	private List<TestRow> generateTestRows() {
		List<TestRow> dateList = new LinkedList<>();
		dateList.add(new TestRow ("2018-03-02T21:21:34.083","3"));
		dateList.add(new TestRow ("2016-01-05T20:21:24.083","4"));
		dateList.add(new TestRow (LAST_DATE,"1"));
		dateList.add(new TestRow ("2018-01-05T20:21:24.083","8"));
		dateList.add(new TestRow (FIRST_DATE,"6"));
		dateList.add(new TestRow ("2018-01-05T20:21:24.083","6"));
		return dateList;
	}
	
	private class TestRow{
		private String creationDate;
		private String score;
		
		private TestRow(String creationDate, String score){
			this.creationDate = creationDate;
			this.score = score;
		}
	}	
}
