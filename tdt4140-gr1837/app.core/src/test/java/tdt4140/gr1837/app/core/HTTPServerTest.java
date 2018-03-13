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
	public static final int CREATED = 201;
	public static final int OK = 200;
	
	@Before
	public void setUpServer() {
		try {
			HTTPServer.initialize();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testCreateSession() throws ClientProtocolException, IOException {
		String       postUrl       = "http://localhost:8000/session";// put in your url
		Gson         gson          = new Gson();
		HttpClient   httpClient    = HttpClientBuilder.create().build();
		HttpPost     post          = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity("clientID=4&date=2017-10-10&note=me", "utf-8");//gson.tojson() converts your pojo to json
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
		String       postUrl       = "http://localhost:8000/session";// put in your url
		Gson         gson          = new Gson();
		HttpClient   httpClient    = HttpClientBuilder.create().build();
		HttpPost     post          = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity("clientID=kevin&date=2017-10-10&note=me", "utf-8");//gson.tojson() converts your pojo to json
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse  response = httpClient.execute(post);
		
		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(BAD_REQUEST, statusCode);
	}
	
	@Test
	public void testGetSession() throws ClientProtocolException, IOException {
		String       getUrl       = "http://localhost:8000/session/5";// put in your url
		HttpClient   httpClient   = HttpClientBuilder.create().build();
		HttpGet      get          = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse  response = httpClient.execute(get);
		
		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(OK, statusCode);
//		System.out.println((response.getEntity()));
		String s = EntityUtils.toString(response.getEntity());
		System.out.println(s);
	}

	@After
	public void tearDownServer() {
		HTTPServer.tearDown();
	}
}
