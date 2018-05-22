package pl.yameo.recruitmenttask;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.HttpStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import pl.yameo.recruitmenttask.RecrutimentTaskApplication;
import pl.yameo.recruitmenttask.dto.FileStatisticsDetails;
import pl.yameo.recruitmenttask.dto.FileStatistics;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.BinaryBody.binary;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RecrutimentTaskApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {
	
	private static final int MOCK_SERVER_PORT = 1080;
	private static final String MOCKED_SERVER_PATH_TO_FILE = "/getTestFile";
	private static final String MOCKED_SERVER_URL = "http://localhost:1080";
	private static final String FILE_NAME = "Posts.xml";	
	
	@Autowired
	private ResourceLoader resourceLoader;
		
	@LocalServerPort
	private int port;

	private TestRestTemplate restTemplate = new TestRestTemplate();	
	
	@Before
	public void setUpMockedServer() throws IOException {
		startClientAndServer(1080);		
		setUpExpectations(FILE_NAME);	 
	}
	
	@Test
	public void testFileController() {			
		ResponseEntity<FileStatistics> response = restTemplate.exchange(
				createURLWithPort("/analyze"), HttpMethod.POST, createHttpEntity(), FileStatistics.class);		
		
		assertEquals(HttpStatus.OK ,response.getStatusCode());
		
		FileStatisticsDetails statiscicsRespone = response.getBody().getDetails();		
		assertEquals(new BigDecimal("2.849"), statiscicsRespone.getAvgScore());
		
		LocalDateTime expectedFirstPostDate = LocalDateTime.parse("2017-08-02T19:22:36.567");		
		assertEquals(expectedFirstPostDate, statiscicsRespone.getFirstPostDate());
		
		LocalDateTime expectedLastPostDate = LocalDateTime.parse("2018-03-07T20:21:34.083");		
		assertEquals(expectedLastPostDate, statiscicsRespone.getLastPostDate());
	}
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
	
	@SuppressWarnings("resource")
	private void setUpExpectations(String fileName) throws IOException {
		Resource resource =	resourceLoader.getResource("classpath:"+fileName);
		byte[] bytes = IOUtils.toByteArray(resource.getInputStream());
			
		new MockServerClient("localhost", MOCK_SERVER_PORT)
		    .when(
		        request()
		            .withPath(MOCKED_SERVER_PATH_TO_FILE)
		    )
		    .respond(
		        response()
		            .withStatusCode(HttpStatusCode.OK_200.code())
		            .withBody(binary(bytes))
		    );
	}
	
	private HttpEntity<MultiValueMap<String, String>> createHttpEntity() {
		MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
		map.add("url", MOCKED_SERVER_URL+MOCKED_SERVER_PATH_TO_FILE);
		return new HttpEntity<>(map, new HttpHeaders());
	}
}
