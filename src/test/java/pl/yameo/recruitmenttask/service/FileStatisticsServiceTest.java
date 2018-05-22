package pl.yameo.recruitmenttask.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import pl.yameo.recruitmenttask.dto.FileStatisticsDetails;
import pl.yameo.recruitmenttask.exception.CommonException;
import pl.yameo.recruitmenttask.util.SaxFileHandler;

@RunWith(SpringJUnit4ClassRunner.class)
public class FileStatisticsServiceTest {
	
	private static final String URL = "url";
		
	@Mock
	private SaxFileService saxFileService;
	
	@Mock
	private SAXParser saxParser;
	
	@InjectMocks
	private FileStatisticsService fileStatisticsService = new FileStatisticsService();
	
	@Test
	public void testGetStatistics() throws Exception {
		FileStatisticsDetails expectedDetails = new FileStatisticsDetails();
		when(saxFileService.parse(eq(URL), any(SaxFileHandler.class), eq(saxParser)))
			.thenReturn(expectedDetails);
		
		FileStatisticsDetails returnedDetails = fileStatisticsService.getStatistics(URL);
		assertEquals(expectedDetails, returnedDetails);		
	}
}
