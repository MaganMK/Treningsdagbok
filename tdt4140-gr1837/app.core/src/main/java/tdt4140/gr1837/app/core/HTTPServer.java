package tdt4140.gr1837.app.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.entity.StringEntity;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HTTPServer {
	
	static HttpServer server;
	
	 public static void initialize() throws Exception {
	        server = HttpServer.create(new InetSocketAddress(8000), 0);
	        server.createContext("/session", new SessionHandler());
	        server.createContext("/clients", new ClientHandler());
	        server.createContext("/exercise", new ExerciseHandler());
	        server.setExecutor(null); // creates a default executor
	        server.start();
	 }
	 
	 public static void tearDown() {
		 server.stop(0);
	 }
	 
	 public static Map<String, String> getParams(HttpExchange ex) {
		 Map<String, String> params = new HashMap<>();
		 try {
			 String bodyString = IOUtils.toString(ex.getRequestBody());
			 for(String s:bodyString.split("&")) {
				 String[] tokens = s.split("=");
				 params.put(tokens[0], tokens[1]);
			 }
			 return params;
		 }catch(IOException e) {
			 return new HashMap<String, String>();
		 }
	 }
	 
	 public static void sendResponse(HttpExchange ex, String response, int statusCode) {
         try {
        	 ex.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
			 ex.sendResponseHeaders(statusCode, response.length());
	         OutputStream os = ex.getResponseBody();
	         os.write(response.getBytes());
	         os.close();
         } catch(IOException e) {
        	 return;
         }
	 }
	 public static void sendResponse(HttpExchange ex, String response) {
		 sendResponse(ex, response, 200);
	 }
	 
	 

    static class ClientHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) {
            System.out.println(ex.getRequestURI().toString().split("/")[2]);
            HTTPServer.sendResponse(ex, "This is the response");
        }
        
        private void post(HttpExchange ex) {
        	// Faar en eksisterene klient. Kalles ved POST /client
        }
        
        private void get(HttpExchange ex) {
        	// Lager en ny client. Kalles om man faar en http request GET /client/id
        }
        
        private void update(HttpExchange ex) {
        	// Oppdaterer en klient. Kalles ved UPDATE /client/id
        }
    }
    static class SessionHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) {
        	System.out.println("E");
        	if(ex.getRequestMethod().equals("GET")) {
        	    get(ex);
        	} else if(ex.getRequestMethod().equals("POST")) {
        		post(ex);
        	} else if(ex.getRequestMethod().equals("UPDATE")) {
        		update(ex);
        	}

        }
        
        private void post(HttpExchange ex) {
        	// Lager en ny session. Kalles om man faar en http request POST /session
        	Map<String, String> params = getParams(ex);
        	try {
				int sessionID = SQLConnector.createSession(Integer.parseInt(params.get("clientID")), params.get("date"), params.get("note"));
				sendResponse(ex, Integer.toString(sessionID), 201);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig klient-id", 400);
			} catch (SQLException e) {
				sendResponse(ex, "Kunne ikke koble til databasen", 503);
			}
        }
        
        private void get(HttpExchange ex) {
        	// Faar en eksisterene klient. Kalles ved POST /exercise/session_id
        	String sessionId = ex.getRequestURI().toString().split("/")[2];
        	try {
				Session session = SQLConnector.getSession(Integer.parseInt(sessionId));
				Gson gson = new Gson();
				String response = gson.toJson(session); // gson.tojson() converts your pojo to json
				System.out.println(response);
				response = response.replaceAll("[æ]", "ae");
				response = response.replaceAll("[ø]", "o");
				response = response.replaceAll("[å]", "aa");
				System.out.println(response);
				sendResponse(ex, response);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig format på okt-id", 400);
			} catch (IllegalArgumentException e) {
				sendResponse(ex, e.getMessage(), 404);
				e.printStackTrace();
			} catch (SQLException e) {
				sendResponse(ex, "Kunne ikke koble til databasen", 503);
			}
        }
        
        private void update(HttpExchange ex) {
        	// Oppdaterer en klient. Kalles ved UPDATE /client/id
        }
    }
    
    static class ExerciseHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange ex) {
            System.out.println(ex.getRequestURI().toString().split("/")[2]);
            HTTPServer.sendResponse(ex, "This is the response");
        }
        
        private void post(HttpExchange ex) {
        	// Faar en eksisterene klient. Kalles ved POST /exercise/session_id
        }
        
        private void get(HttpExchange ex) {
        	// Lager en ny client. Kalles om man faar en http request GET /exercise/id
        }
        
        private void update(HttpExchange ex) {
        	// Oppdaterer en klient. Kalles ved UPDATE /client/id
        }
    }
}
