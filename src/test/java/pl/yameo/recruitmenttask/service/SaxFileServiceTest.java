package pl.yameo.recruitmenttask.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import pl.yameo.recruitmenttask.dto.FileStatisticsDetails;
import pl.yameo.recruitmenttask.exception.FileAnalyzingException;
import pl.yameo.recruitmenttask.exception.XMLParsingException;
import pl.yameo.recruitmenttask.util.FileStreamUtil;
import pl.yameo.recruitmenttask.util.SaxFileHandler;

@RunWith(SpringJUnit4ClassRunner.class)
public class SaxFileServiceTest {

	private static final String URL = "url";
	
	@Mock
	private FileStreamUtil fileStreamUtil;
	
	@Mock
	private SaxFileHandler dummySaxFileHandler ;
	
	@Mock
	private SAXParser dummySaxParser ;
	
	@Mock
	private InputStream dummyStream ;
	
	@InjectMocks
	private SaxFileService saxFileService = new SaxFileService();
	
	@Test
	public void testParse() throws SAXException, IOException {		
		FileStatisticsDetails expectedStatisticsDetails = new FileStatisticsDetails();
		
		when(fileStreamUtil.createInputStream(URL)).thenReturn(dummyStream);
		when(dummySaxFileHandler.generateStatisticsForFile()).thenReturn(expectedStatisticsDetails);
		
		FileStatisticsDetails returnedStatisticsDetails;
		returnedStatisticsDetails = saxFileService.parse(URL, dummySaxFileHandler, dummySaxParser);
		
		verify(dummySaxParser).parse(dummyStream, dummySaxFileHandler);
		assertEquals(expectedStatisticsDetails, returnedStatisticsDetails);
	}
	
	@Test(expected=FileAnalyzingException.class)
	public void testIOExceptionHandling() throws Exception {
		testExceptionHandling(IOException.class);
	}
	
	@Test(expected=XMLParsingException.class)
	public void testSAXExceptionHandling() throws Exception {
		testExceptionHandling(SAXException.class);
	}
	
	private void testExceptionHandling(Class<? extends Exception> exceptionClass) throws InstantiationException, IllegalAccessException, SAXException, IOException {
		when(fileStreamUtil.createInputStream(URL)).thenReturn(dummyStream);

		doThrow(exceptionClass.newInstance()).when(dummySaxParser).
			parse(any(InputStream.class),any(SaxFileHandler.class));
		
		saxFileService.parse(URL, dummySaxFileHandler, dummySaxParser);
	}
}
