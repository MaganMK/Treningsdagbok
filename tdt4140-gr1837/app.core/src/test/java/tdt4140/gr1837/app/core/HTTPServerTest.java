package tdt4140.gr1837.app.core;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class HTTPServerTest {

	public static final int BAD_REQUEST = 400;
	public static final int NOT_FOUND = 404;
	public static final int CREATED = 201;
	public static final int OK = 200;
	
	private int port;

	@Before
	public void setUpServer() {
		while(true) {
			try {
				port = HTTPServer.initialize();
				break;
			} catch (Exception e) {
				System.out.println("hey");
				//fail();
			}
		}
	}
	
	// Okttester
	
	@Test
	public void testCreateGetDeleteSession() throws ClientProtocolException, IOException {
		int id = testCreateSession();
		testGetSession(id);
		testDeleteSession(id);
		testGetSessionWithNonexcistingID(id);
		
	}

	private int testCreateSession() throws ClientProtocolException, IOException {
		String postUrl = String.format("http://localhost:%s/session", port);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity("clientID=4&date=2017-10-10&note=me", "utf-8");
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(CREATED, statusCode);
		
		return Integer.parseInt(EntityUtils.toString(response.getEntity()));
	}
	
	private void testDeleteSession(int id) throws ClientProtocolException, IOException {
		String deleteUrl = String.format("http://localhost:%s/session/%s",port,id);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpDelete delete = new HttpDelete(deleteUrl);
		HttpResponse response = httpClient.execute(delete);
		System.out.println(response.getEntity());
		assertEquals(OK, response.getStatusLine().getStatusCode());
	}

	@Test
	public void testCreateSessionWrongClientId() throws ClientProtocolException, IOException {
		String postUrl = String.format("http://localhost:%s/session", port);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity("clientID=kevin&date=2017-10-10&note=me", "utf-8");
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(BAD_REQUEST, statusCode);
	}

	private void testGetSession(int id) throws ClientProtocolException, IOException {
		String getUrl = String.format("http://localhost:%s/session/%s", port, id);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(OK, statusCode);
		String s = EntityUtils.toString(response.getEntity());
		assertEquals(id, Integer.parseInt(extractInfoFromResponse(s).get("id")));
	}

	private void testGetSessionWithNonexcistingID(int id) throws ClientProtocolException, IOException {
		String getUrl = String.format("http://localhost:%s/session/%s",port,id);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(NOT_FOUND, statusCode);
	}

	@Test
	public void testGetSessionWithIllegalID() throws ClientProtocolException, IOException {
		String getUrl = String.format("http://localhost:%s/session/-69", port);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(NOT_FOUND, statusCode);
	}
	
	
	
	// Klienttester

	@Test
	public void testCreateGetUpdateDeleteClient() throws ClientProtocolException, IOException {
		int id = testCreateClient();
		testGetClient(id);
		testUpdateClient(id);
		testDeleteClient(id);
	}
	
	private int testCreateClient() throws ClientProtocolException, IOException {
		String postUrl = String.format("http://localhost:%s/clients", port);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity(
				"name=Lise&phone=12345678&age=17&motivation=sugardaddy&trainerID=2&distancegoal=200", "utf-8");

		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(CREATED, statusCode);
		
		return Integer.parseInt(EntityUtils.toString(response.getEntity()));
	}

	private void testGetClient(int id) throws ClientProtocolException, IOException {
		String getUrl = String.format("http://localhost:%s/clients/%s", port, id);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(OK, statusCode);

		String s = EntityUtils.toString(response.getEntity());
		assertEquals(id, Integer.parseInt(extractInfoFromResponse(s).get("id")));
	}

	@Test
	public void testGetClientWithNonexistingID() throws ClientProtocolException, IOException {
		String getUrl = String.format("http://localhost:%s/clients/12000000", port);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(NOT_FOUND, statusCode);
	}

	@Test
	public void testGetClientWithIllegalID() throws ClientProtocolException, IOException {
		String getUrl = String.format("http://localhost:%s/clients/seks", port);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(BAD_REQUEST, statusCode);
	}

	private void testUpdateClient(int id) throws ClientProtocolException, IOException {
		String putUrl = String.format("http://localhost:%s/clients/%s", port, id);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPut put = new HttpPut(putUrl);
		StringEntity puttingString = new StringEntity(
				"name=Lise&phone=12345678&age=17&motivation=sugardaddy&trainerID=2&distancegoal=2000", "utf-8");

		put.setEntity(puttingString);
		put.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(put);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(OK, statusCode);
	}
	
	private void testDeleteClient(int id) throws ClientProtocolException, IOException {
		String deleteUrl = String.format("http://localhost:%s/clients/%s", port, id);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpDelete delete = new HttpDelete(deleteUrl);
		HttpResponse response = httpClient.execute(delete);
		assertEquals(OK, response.getStatusLine().getStatusCode());
	}

	
	// Styrkeovelsetester
	
	@Test
	public void testCreateGetUpdateDeleteStrengthExercise() throws ClientProtocolException, IOException {
		int sessionId = 1;
		int count = testGetStrengthExercises(sessionId);
		int id = testCreateStrengthExercise(sessionId);
		assertEquals(count + 1, testGetStrengthExercises(sessionId));
		testUpdateStrengthExercise(id);
		testDeleteStrengthExercise(id);
		assertEquals(count, testGetStrengthExercises(sessionId));
	}
	
	private void testDeleteStrengthExercise(int id) throws ClientProtocolException, IOException {
		String deleteUrl = String.format("http://localhost:%s/exercise/strength/%s", port, id);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpDelete delete = new HttpDelete(deleteUrl);
		HttpResponse response = httpClient.execute(delete);
		assertEquals(OK, response.getStatusLine().getStatusCode());
	}

	private int testCreateStrengthExercise(int sessionId) throws ClientProtocolException, IOException {
		String postUrl = String.format("http://localhost:%s/exercise/strength", port);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity("reps=4&sett=3&weight=17&note=dritbra&sessionId="+sessionId+"&exerciseId=6",
				"utf-8");
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(CREATED, statusCode);
		
		return Integer.parseInt(EntityUtils.toString(response.getEntity()));
	}

	private int testGetStrengthExercises(int sessionId) throws ClientProtocolException, IOException {
		String getUrl = String.format("http://localhost:%s/exercise/strength/%s", port, sessionId);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(OK, statusCode);
		return EntityUtils.toString(response.getEntity()).split("}").length;
	}
	
	private void testUpdateStrengthExercise(int id) throws ClientProtocolException, IOException {
		String putUrl = String.format("http://localhost:%s/exercise/strength/%s", port, id);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPut put = new HttpPut(putUrl);
		StringEntity puttingString = new StringEntity(
				"reps=4&sett=3&weight=17&note=dritbra&exercise=6", "utf-8");

		put.setEntity(puttingString);
		put.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(put);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(OK, statusCode);
	}
	
	
	// Utholdenhetstester
	
	@Test
	public void testCreateGetUpdateDeleteEnduranceExercise() throws ClientProtocolException, IOException {
		int sessionId = 425;
		int count = testGetEnduranceExercise(sessionId);
		int id = testCreateEnduranceExercise(sessionId);
		assertEquals(count + 1, testGetEnduranceExercise(sessionId));
		testUpdateEnduranceExercise(id);
		testDeleteEnduranceExercise(id);
		System.out.println(count + "  " + testGetEnduranceExercise(sessionId));
		assertEquals(count, testGetEnduranceExercise(sessionId));
	}
	
	private int testCreateEnduranceExercise(int sessionId) throws ClientProtocolException, IOException {
		String postUrl = String.format("http://localhost:%s/exercise/endurance", port);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(postUrl);
		StringEntity postingString = new StringEntity("distance=4&time=3000&note=yahoo&sessionId="+sessionId+"&exerciseId=1",
				"utf-8");

		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(CREATED, statusCode);
		
		return Integer.parseInt(EntityUtils.toString(response.getEntity()));
	}
	
	private int testGetEnduranceExercise(int sessionId) throws ClientProtocolException, IOException {
		String getUrl = String.format("http://localhost:%s/exercise/endurance/%s", port, sessionId);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(OK, statusCode);
		
		return EntityUtils.toString(response.getEntity()).split("}").length;
	}

	@Test 
	public void testGetEnduranceExerciseWithIllegalId() throws ClientProtocolException, IOException {
		String getUrl = String.format("http://localhost:%s/exercise/endurance/seks", port);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet(getUrl);
		get.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(get);
		
		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(BAD_REQUEST, statusCode);
	}
	
	private void testDeleteEnduranceExercise(int id) throws ClientProtocolException, IOException {
		String deleteUrl = String.format("http://localhost:%s/exercise/endurance/%s", port, id);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpDelete delete = new HttpDelete(deleteUrl);
		HttpResponse response = httpClient.execute(delete);
		assertEquals(OK, response.getStatusLine().getStatusCode());
	}
	
	private void testUpdateEnduranceExercise(int id) throws ClientProtocolException, IOException {
		String putUrl = String.format("http://localhost:%s/exercise/endurance/%s", port, id);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPut put = new HttpPut(putUrl);
		StringEntity puttingString = new StringEntity(
				"distance=50&time=250&note=Ny+verdensrekord!+Woho&exerciseId=1", "utf-8");

		put.setEntity(puttingString);
		put.setHeader("Content-type", "application/json");
		HttpResponse response = httpClient.execute(put);

		int statusCode = response.getStatusLine().getStatusCode();
		assertEquals(OK, statusCode);
	}

	
	// Konverterer fra Json til map, der nokkel er variabelnavn
	private Map<String, String> extractInfoFromResponse(String json) {
		Gson gson = new Gson();
		Type stringStringMap = new TypeToken<Map<String, String>>() {
		}.getType();
		Map<String, String> map = gson.fromJson(json, stringStringMap);
		return map;
	}

	@After
	public void tearDownServer() {
		HTTPServer.tearDown();
	}
}