package tdt4140.gr1837.app.core;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HTTPServer {

	static HttpServer server;
	
	public static void main(String[] args) {
		while(true) {
			try {
				int port = initialize();
				System.out.println("HTTPServeren kjorer naa paa port: " + port);
				break;
			} catch(Exception e) {
				System.out.println("Klarte ikke aa koble til, prover igjen");
			}
		}
	}
	
	public static int initialize() throws Exception {
		Random generator = new Random();
		int port = generator.nextInt(10000)+10000;
		server = HttpServer.create(new InetSocketAddress(port), 0);
		server.createContext("/session", new SessionHandler());
		server.createContext("/clients", new ClientHandler());
		server.createContext("/exercise/endurance", new EnduranceExerciceHandler());
		server.createContext("/exercise/strength", new StrengthExerciseHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
		return port;
	}

	public static void tearDown() {
		server.stop(0);
	}

	public static Map<String, String> getParams(HttpExchange ex) {
		Map<String, String> params = new HashMap<>();
		try {
			String bodyString = IOUtils.toString(ex.getRequestBody());
			for (String s : bodyString.split("&")) {
				String[] tokens = s.split("=");
				params.put(tokens[0], tokens[1]);
			}
			return params;
		} catch (IOException e) {
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
		} catch (IOException e) {
			return;
		}
	}

	public static void sendResponse(HttpExchange ex, String response) {
		sendResponse(ex, response, 200);
	}

	private static String changeNorwegianLetters(String response) {
		return response.replaceAll("\u00e6", "ae").replaceAll("\u00f8", "o").replaceAll("\u00e5", "aa")
				.replaceAll("\u00c6", "AE").replaceAll("\u00d8", "O").replaceAll("\u00c5", "AA");
	}

	static class ClientHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange ex) {
			if (ex.getRequestMethod().equals("GET")) {
				get(ex);
			} else if (ex.getRequestMethod().equals("POST")) {
				post(ex);
			} else if (ex.getRequestMethod().equals("PUT")) {
				update(ex);
			} else if (ex.getRequestMethod().equals("DELETE")) {
				delete(ex);
			}
		}

		private void post(HttpExchange ex) {
			// Lager en ny client. Kalles om man faar en http request POST /clients
			Map<String, String> params = getParams(ex);
			try {
				int clientID = SQLConnector.createUser(params.get("name"), params.get("phone"),
						Integer.parseInt(params.get("age")), params.get("motivation"),
						Integer.parseInt(params.get("trainerID")), Integer.parseInt(params.get("distancegoal")));
				sendResponse(ex, Integer.toString(clientID), 201);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig verdi", 400);
			} catch (SQLException e) {
				sendResponse(ex, e.getMessage(), 503);
			}
		}

		private void get(HttpExchange ex) {
			// Faar en eksisterene klient. Kalles ved GET /clients/id
			String clientId = ex.getRequestURI().toString().split("/")[2];

			try {
				User user = SQLConnector.getUser(Integer.parseInt(clientId));
				Gson gson = new Gson();
				String response = gson.toJson(user); // gson.tojson() converts your pojo to json
				response = changeNorwegianLetters(response);
				sendResponse(ex, response);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig klientid", 400);
			} catch (IllegalArgumentException e) {
				sendResponse(ex, e.getMessage(), 404);
			} catch (SQLException e) {
				sendResponse(ex, e.getMessage(), 503);
			}
		}

		private void update(HttpExchange ex) {
			// Oppdaterer en klient. Kalles ved PUT /clients/id
			String clientId = ex.getRequestURI().toString().split("/")[2];

			Map<String, String> params = getParams(ex);
			try {
				SQLConnector.updateUser(Integer.parseInt(clientId), params.get("name"), params.get("phone"),
						Integer.parseInt(params.get("age")), params.get("motivation"),
						Integer.parseInt(params.get("trainerID")), Integer.parseInt(params.get("distancegoal")));
				sendResponse(ex, clientId);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig verdi", 400);
			} catch (SQLException e) {
				sendResponse(ex, e.getMessage(), 503);
			}
		}

		private void delete(HttpExchange ex) {
			// Sletter en klient. Kalles ved DELETE /clients/id
			String clientId = ex.getRequestURI().toString().split("/")[2];
			try {
				SQLConnector.deleteUser(Integer.parseInt(clientId));
				sendResponse(ex, clientId, 200);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig client-id", 400);
			} catch (SQLException e) {
				sendResponse(ex, e.getMessage(), 503);
			}
		}
	}

	static class SessionHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange ex) {
			if (ex.getRequestMethod().equals("GET")) {
				get(ex);
			} else if (ex.getRequestMethod().equals("POST")) {
				post(ex);
			} else if (ex.getRequestMethod().equals("DELETE")) {
				delete(ex);
			}
		}

		private void post(HttpExchange ex) {
			// Lager en ny okt. Kalles om man faar en http request POST /session
			Map<String, String> params = getParams(ex);
			try {
				int sessionID = SQLConnector.createSession(Integer.parseInt(params.get("clientID")), params.get("date"),
						params.get("note"));
				sendResponse(ex, Integer.toString(sessionID), 201);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig klient-id", 400);
			} catch (SQLException e) {
				sendResponse(ex, e.getMessage(), 503);
			}
		}

		private void get(HttpExchange ex) {
			// Faar en eksisterene okt. Kalles ved GET /session/id
			String sessionId = ex.getRequestURI().toString().split("/")[2];
			try {
				Session session = SQLConnector.getSession(Integer.parseInt(sessionId));
				Gson gson = new Gson();
				String response = gson.toJson(session);
				response = changeNorwegianLetters(response);
				sendResponse(ex, response);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig okt-id", 400);
			} catch (IllegalArgumentException e) {
				sendResponse(ex, e.getMessage(), 404);
			} catch (SQLException e) {
				sendResponse(ex, e.getMessage(), 503);
			}
		}
		
		private void delete(HttpExchange ex) {
			// Sletter en okt. Kalles ved DELETE /session/id
			String exerciseId = ex.getRequestURI().toString().split("/")[2];
			try {
				SQLConnector.deleteSession(Integer.parseInt(exerciseId));
				sendResponse(ex, exerciseId, 200);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig okt-id", 400);
			} catch (SQLException e) {
				sendResponse(ex, e.getMessage(), 503);
			}
		}
	}

	static class EnduranceExerciceHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange ex) throws IOException {
			if (ex.getRequestMethod().equals("GET")) {
				get(ex);
			} else if (ex.getRequestMethod().equals("POST")) {
				post(ex);
			} else if (ex.getRequestMethod().equals("PUT")) {
				update(ex);
			} else if (ex.getRequestMethod().equals("DELETE")) {
				delete(ex);
			}
		}

		private void get(HttpExchange ex) {
			// Faar en eksisterene ovelse. Kalles ved GET /exercise/endurance/session_id
			String sessionId = ex.getRequestURI().toString().split("/")[3];
			try {
				List<Exercise> exercises = SQLConnector.getAllExercises(Integer.parseInt(sessionId), false);
				Gson gson = new Gson();
				String response = gson.toJson(exercises);
				response = changeNorwegianLetters(response);
				sendResponse(ex, response);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig okt-id", 400);
			} catch (IllegalArgumentException e) {
				sendResponse(ex, e.getMessage(), 404);
			} catch (SQLException e) {
				sendResponse(ex, e.getMessage(), 503);
			}
		}

		private void post(HttpExchange ex) {
			// Lager en ny ovelse. Kalles om man faar en http request POST /exercise/endurance
			Map<String, String> params = getParams(ex);
			try {
				int exerciseId = SQLConnector.createEnduranceExercise(Integer.parseInt(params.get("distance")),
						new Time(Integer.parseInt(params.get("time"))), params.get("note"),
						Integer.parseInt(params.get("sessionId")), Integer.parseInt(params.get("exerciseId")));
				sendResponse(ex, Integer.toString(exerciseId), 201);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig verdi", 400);
			} catch (SQLException e) {
				sendResponse(ex, e.getMessage(), 503);
			}
		}

		private void update(HttpExchange ex) {
			// Oppdaterer en ovelse. Kalles ved PUT /exercise/endurance/id
			Map<String, String> params = getParams(ex);
			String enduranceExerciseId = ex.getRequestURI().toString().split("/")[3];
			try {
				SQLConnector.updateEnduranceExercise(Integer.parseInt(params.get("distance")),
						new Time(Integer.parseInt(params.get("time"))), params.get("note"),
						Integer.parseInt(params.get("exerciseId")), Integer.parseInt(enduranceExerciseId));
				sendResponse(ex, enduranceExerciseId, 200);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig verdi", 400);
			} catch (SQLException e) {
				sendResponse(ex, e.getMessage(), 503);
			}
		}

		private void delete(HttpExchange ex) {
			// Sletter en ovelse. Kalles ved DELETE /exercise/endurance/id
			String exerciseId = ex.getRequestURI().toString().split("/")[3];
			try {
				SQLConnector.deleteEnduranceExercise(Integer.parseInt(exerciseId));
				sendResponse(ex, exerciseId, 200);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig exercise-id", 400);
			} catch (SQLException e) {
				sendResponse(ex, e.getMessage(), 503);
			}
		}

	}

	static class StrengthExerciseHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange ex) {
			if (ex.getRequestMethod().equals("GET")) {
				get(ex);
			} else if (ex.getRequestMethod().equals("POST")) {
				post(ex);
			} else if (ex.getRequestMethod().equals("PUT")) {
				update(ex);
			} else if (ex.getRequestMethod().equals("DELETE")) {
				delete(ex);
			}
		}

		private void get(HttpExchange ex) {
			// Faar oveler tilknyttet en okt. Kalles ved GET /exercise/strength/session_id
			String sessionId = ex.getRequestURI().toString().split("/")[3];
			try {
				List<Exercise> exercises = SQLConnector.getAllExercises(Integer.parseInt(sessionId), true);
				Gson gson = new Gson();
				String response = gson.toJson(exercises);
				response = changeNorwegianLetters(response);
				sendResponse(ex, response);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig okt-id", 400);
			} catch (IllegalArgumentException e) {
				sendResponse(ex, e.getMessage(), 404);
			} catch (SQLException e) {
				sendResponse(ex, e.getMessage(), 503);
			}

		}

		private void post(HttpExchange ex) {
			// Lager en ny ovelse. Kalles om man faar en http request POST /exercise/strength
			Map<String, String> params = getParams(ex);
			try {
				int exerciseId = SQLConnector.createStrengthExercise(Integer.parseInt(params.get("reps")),
						Integer.parseInt(params.get("sett")), Integer.parseInt(params.get("weight")),
						params.get("note"), Integer.parseInt(params.get("sessionId")),
						Integer.parseInt(params.get("exerciseId")));
				sendResponse(ex, Integer.toString(exerciseId), 201);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig verdi", 400);
			} catch (SQLException e) {
				sendResponse(ex, e.getMessage(), 503);
			}
		}

		private void update(HttpExchange ex) {
			// Oppdaterer en ovelse. Kalles ved PUT /exercise/strength/id
			Map<String, String> params = getParams(ex);
			String exerciseId = ex.getRequestURI().toString().split("/")[3];
			try {
				SQLConnector.updateStrengthExercise(Integer.parseInt(params.get("reps")),
						Integer.parseInt(params.get("sett")), Integer.parseInt(params.get("weight")),
						params.get("note"), Integer.parseInt(params.get("exercise")),
						Integer.parseInt(exerciseId));
				sendResponse(ex, exerciseId, 200);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig verdi", 400);
			} catch (SQLException e) {
				sendResponse(ex, e.getMessage(), 503);
			}
		}

		private void delete(HttpExchange ex) {
			// Sletter en ovelse. Kalles ved DELETE /exercise/strength/id
			String exerciseId = ex.getRequestURI().toString().split("/")[3];
			try {
				SQLConnector.deleteStrengthExercise(Integer.parseInt(exerciseId));
				sendResponse(ex, exerciseId, 200);
			} catch (NumberFormatException e) {
				sendResponse(ex, "Ugyldig exercise-id", 400);
			} catch (SQLException e) {
				sendResponse(ex, e.getMessage(), 503);
			}
		}
	}
}
