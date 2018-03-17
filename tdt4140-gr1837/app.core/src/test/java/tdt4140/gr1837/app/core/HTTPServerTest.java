package tdt4140.gr1837.app.core;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class HTTPServerTest {
	
	public static final int BAD_REQUEST = 400;
	public static final int NOT_FOUND = 404;
	public static final int CREATED = 201;
	public static final int OK = 200;
	
	@Before
	public void setUpServer() {
		try {
			HTTPServer.initialize();
		} catch (Exception e) {
			e.printStackTrace();
			// fail();
		}
	}
	
	@Test
	public void testCreateSession() throws ClientProtocolException, IOException {
		String       postUrl       = "http://localhost:8000/session";
		Gson         gson          = new Gson();
		HttpClient   httpClient    = HttpClientBuilder.create().build();
		HttpPost     post          = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity("clientID=4&date=2017-10-10&note=me", "utf-8");
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse  response = httpClient.execute(post);
		
		String s = EntityUtils.toString(response.getEntity());
		assertTrue(Integer.parseInt(s) > 0);
		
		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(CREATED, statusCode);
	}
	
	@Test
	public void testCreateSessionWrongClientId() throws ClientProtocolException, IOException {
		String       postUrl       = "http://localhost:8000/session";
		Gson         gson          = new Gson();
		HttpClient   httpClient    = HttpClientBuilder.create().build();
		HttpPost     post          = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity("clientID=kevin&date=2017-10-10&note=me", "utf-8");
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse  response = httpClient.execute(post);
		
		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(BAD_REQUEST, statusCode);
	}
	
	@Test
	public void testGetSession() throws ClientProtocolException, IOException {
		String       getUrl       = "http://localhost:8000/session/5";
		HttpClient   httpClient   = HttpClientBuilder.create().build();
		HttpGet      get          = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse  response = httpClient.execute(get);
		
		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(OK, statusCode);
		String s = EntityUtils.toString(response.getEntity());
		assertEquals(5, Integer.parseInt(extractInfoFromResponse(s)[1]));
		System.out.println(s);
	}

	@Test
	public void testGetSessionWithNonexcistingID() throws ClientProtocolException, IOException {
		String       getUrl       = "http://localhost:8000/session/750";
		HttpClient   httpClient    = HttpClientBuilder.create().build();
		HttpGet      get          = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse  response = httpClient.execute(get);
		
		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(NOT_FOUND, statusCode);
	}
	
	// Hittil er det lovlig aa legge til negative id'er i databasen, men det er ikke "lov"..
	@Test
	public void testGetSessionWithIllegalID() throws ClientProtocolException, IOException {
		String       getUrl       = "http://localhost:8000/session/-69";
		HttpClient   httpClient    = HttpClientBuilder.create().build();
		HttpGet      get          = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse  response = httpClient.execute(get);
		
		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(NOT_FOUND, statusCode);
	}
	
	
	// Returnerer en liste med info fra objektet vi faar gjennom HTTP. Forst notatet, id'en og datoen.
	// Alle verdier er en String
	private String[] extractInfoFromResponse(String s) {
		String[] response = s.split(",");
		String note = response[0].split(":")[1];
		String id = response[1].split(":")[1];
		String date = response[2].split(":")[1];
		String[] info = {note, id, date};
		return info;
	}

	@Test
	public void testGetSessionWithNonexcistingID() throws ClientProtocolException, IOException {
		String       getUrl       = "http://localhost:8000/session/750";
		HttpClient   httpClient    = HttpClientBuilder.create().build();
		HttpGet      get          = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse  response = httpClient.execute(get);
		
		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(NOT_FOUND, statusCode);
	}
	
	// Hittil er det lovlig aa legge til negative id'er i databasen, men det er ikke "lov"..
	@Test
	public void testGetSessionWithIllegalID() throws ClientProtocolException, IOException {
		String       getUrl       = "http://localhost:8000/session/-69";
		HttpClient   httpClient    = HttpClientBuilder.create().build();
		HttpGet      get          = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse  response = httpClient.execute(get);
		
		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(NOT_FOUND, statusCode);
	}
	
	
	// Returnerer en liste med info fra objektet vi faar gjennom HTTP. Forst notatet, id'en og datoen.
	// Alle verdier er en String
	private String[] extractInfoFromResponse(String s) {
		String[] response = s.split(",");
		String note = response[0].split(":")[1];
		String id = response[1].split(":")[1];
		String date = response[2].split(":")[1];
		String[] info = {note, id, date};
		return info;
	}

	@After
	public void tearDownServer() {
		HTTPServer.tearDown();
	}
}
