package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * A class which represents an implementation of a HTTP server
 * 
 * @author Vito Sabalic
 *
 */
public class SmartHttpServer {

	private String address;
	private String domainName;
	private int port;
	private int workerThreads;
	private int sessionTimeout;
	private Map<String, String> mimeTypes;
	private ServerThread serverThread;
	private ExecutorService threadPool;
	private Path documentRoot;
	private Map<String, IWebWorker> workersMap;
	private Map<String, SessionMapEntry> sessions;

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid argument length, expected one argument: path to server.properties");
			System.exit(0);
		}

		new SmartHttpServer(args[0]).start();
	}

	/**
	 * A simple constructor, assigns and initializes all values
	 * 
	 * @param pathToServerProperties
	 */
	public SmartHttpServer(String pathToServerProperties) {
		mimeTypes = new HashMap<>();
		serverThread = new ServerThread();
		workersMap = new HashMap<>();
		sessions = new HashMap<>();
		startCleanerThread();
		readServerProperties(pathToServerProperties);
	}

	/**
	 * Starts the server
	 */
	protected synchronized void start() {
		threadPool = Executors.newFixedThreadPool(workerThreads);

		if (!serverThread.isAlive()) {
			serverThread.start();
		}
	}

	/**
	 * Stops the server
	 */
	protected synchronized void stop() {

		serverThread.stop = true;
		threadPool.shutdown();
	}

	/**
	 * Reads all server properties from the provided path
	 * 
	 * @param pathToServerProperties The provided path
	 */
	private void readServerProperties(String pathToServerProperties) {
		try (InputStream is = Files.newInputStream(Paths.get(pathToServerProperties))) {

			Properties serverProps = new Properties();

			serverProps.load(is);

			this.address = serverProps.getProperty("server.address");
			this.domainName = serverProps.getProperty("server.domainName");
			this.port = Integer.parseInt(serverProps.getProperty("server.port"));
			this.workerThreads = Integer.parseInt(serverProps.getProperty("server.workerThreads"));
			this.documentRoot = Paths.get(serverProps.getProperty("server.documentRoot"));
			this.documentRoot = documentRoot.toAbsolutePath().normalize();
			this.sessionTimeout = Integer.parseInt(serverProps.getProperty("session.timeout"));

			readMimeTypes(Paths.get(serverProps.getProperty("server.mimeConfig")));
			readWorkers(Paths.get(serverProps.getProperty("server.workers")));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Reads the worker properties located at the provided path. The path should
	 * point to workers.properties file.
	 * 
	 * @param path The provided path
	 */
	private void readWorkers(Path path) {

		try (InputStream is = Files.newInputStream(path)) {
			Properties workerProps = new Properties();

			workerProps.load(is);

			for (String key : workerProps.stringPropertyNames()) {
				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(workerProps.getProperty(key));
				Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
				IWebWorker iww = (IWebWorker) newObject;

				workersMap.put(key, iww);
			}
		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException exc) {
			exc.printStackTrace();
		}

	}

	/**
	 * Reads the mime types located at the provided path. The path should point to
	 * mime.properties file
	 * 
	 * @param path The provided path
	 */
	private void readMimeTypes(Path path) {

		try (InputStream is = Files.newInputStream(path)) {

			Properties mimeProps = new Properties();
			mimeProps.load(is);

			for (String key : mimeProps.stringPropertyNames()) {
				mimeTypes.put(key, mimeProps.getProperty(key));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A method which starts a thread which iterates through saved sessions every 5
	 * minutes, removes them if they are expired
	 */
	private void startCleanerThread() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Thread.sleep(300000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				for (String key : sessions.keySet()) {
					if (sessions.get(key).getValidUntil() < System.currentTimeMillis() / 1000) {
						sessions.remove(key);
					}
				}
			}
		});

		t.setDaemon(true);
		t.start();

	}

	/**
	 * A class which extends the {@link Thread} class. Listens on the socket for
	 * users, when a user arrives, creates a thread from them and serves them.
	 * 
	 * @author Vito Sabalic
	 *
	 */
	protected class ServerThread extends Thread {

		private volatile boolean stop = false;

		@Override
		public void run() {
			ServerSocket serverSocket = null;

			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));
			} catch (IOException exc) {
				exc.printStackTrace();
			}

			while (true) {
				if (stop) {
					try {
						serverSocket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}

				Socket client = null;
				try {
					client = serverSocket.accept();
				} catch (IOException e) {
					e.printStackTrace();
				}

				ClientWorker cw = new ClientWorker(client);
				threadPool.submit(cw);

			}
		}
	}

	/**
	 * A class which represents a session. Used as a value in sessions map.
	 * 
	 * @author Vito Sabalic
	 *
	 */
	private static class SessionMapEntry {
		String sid;
		String host;
		long validUntil;
		Map<String, String> map;

		/**
		 * Simple constructor, assigns all the values
		 * 
		 * @param sid
		 * @param host
		 * @param validUntil
		 * @param map
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {

			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}

		/**
		 * A getter for the session id
		 * 
		 * @return
		 */
		@SuppressWarnings("unused")
		public String getSid() {
			return sid;
		}

		/**
		 * A getter for the host value
		 * 
		 * @return
		 */
		public String getHost() {
			return host;
		}

		/**
		 * A getter for the validUntil value
		 * 
		 * @return
		 */
		public long getValidUntil() {
			return validUntil;
		}

		/**
		 * Sets the validUntil value to the provided value
		 * 
		 * @param validUntil
		 */
		public void setValidUntil(long validUntil) {
			this.validUntil = validUntil;
		}
	}

	/**
	 * A class which represents a worker that serves a single client
	 * 
	 * @author Vito Sabalic
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		private Socket csocket;
		private InputStream iStream;
		private OutputStream oStream;
		private String version;
		private String method;
		private String host;
		private Map<String, String> params = new HashMap<String, String>();
		private Map<String, String> tempParams = new HashMap<String, String>();
		private Map<String, String> permParams = new HashMap<String, String>();
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		private String SID;
		private RequestContext requestContext = null;

		/**
		 * A simple constructor, assigns and initalizes all values
		 * 
		 * @param csocket
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
			this.version = new String();
			this.method = new String();
			this.host = new String();
		}

		@Override
		public void run() {
			try {
				iStream = new BufferedInputStream(csocket.getInputStream());
				oStream = new BufferedOutputStream(csocket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}

			Optional<byte[]> request = null;
			try {
				request = readRequest();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String requestString = new String(request.get(), StandardCharsets.US_ASCII);

			List<String> header = extractHeader(requestString);
			String[] firstLine = header.isEmpty() ? null : header.get(0).split(" ");
			if (firstLine == null || firstLine.length != 3) {
				sendEmptyResponse(400, "Bad request");
				return;
			}

			method = firstLine[0];
			version = firstLine[2];

			if (!(method.equalsIgnoreCase("GET")
					&& (version.equalsIgnoreCase("HTTP/1.0") || version.equalsIgnoreCase("HTTP/1.1")))) {
				sendEmptyResponse(400, "Bad request");
				return;
			}

			header.forEach(h -> {
				if (h.contains("Host:")) {
					host = h.split(":")[1].trim();
				}
			});

			if (host.isEmpty()) {
				host = domainName;
			}

			checkSession(header);

			String requestedPath = firstLine[1];
			String path = new String();
			String paramString = new String();

			if (requestedPath.indexOf("?") >= 0) {
				String[] pom = requestedPath.split("\\?");
				path = pom[0].trim();
				paramString = pom[1].trim();
				parseParameters(paramString);
			} else {
				path = requestedPath;
			}

			try {
				internalDispatchRequest(path, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * An internal dispatch request, used for filtering server based calls and
		 * direct calls from the user
		 * 
		 * @param urlPath    The path of the file
		 * @param directCall The direct call flag, true if user caused this method to be
		 *                   called, false if implementation called
		 * @throws Exception
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {

			if (urlPath.startsWith("/private/") || urlPath.equals("/private")) {
				if (directCall) {
					sendEmptyResponse(404, "Access not allowed");
					return;
				}
			}

			if (urlPath.startsWith("/ext/")) {
				String defaultPath = "hr.fer.zemris.java.webserver.workers.";
				String fqcn = urlPath.substring(5);
				Class<?> referenceToClass = null;
				Object newObject = null;
				try {
					referenceToClass = this.getClass().getClassLoader().loadClass(defaultPath + fqcn);
					newObject = referenceToClass.getDeclaredConstructor().newInstance();
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException | NoSuchMethodException
						| SecurityException exc) {
					sendEmptyResponse(404, "Not found");
					return;
				}

				checkRequestContextValid();

				IWebWorker iww = (IWebWorker) newObject;
				iww.processRequest(requestContext);

				oStream.flush();
				csocket.close();
				return;

			}

			if (workersMap.containsKey(urlPath)) {
				checkRequestContextValid();

				workersMap.get(urlPath).processRequest(requestContext);
				oStream.flush();
				csocket.close();
				return;
			}

			Path requestedFilePath = documentRoot.resolve(urlPath.substring(1)).toAbsolutePath().normalize();

			if (!requestedFilePath.toString().startsWith(documentRoot.toString())) {
				sendEmptyResponse(403, "Forbidden");
				return;
			}

			if (!(Files.exists(requestedFilePath) && Files.isRegularFile(requestedFilePath)
					&& Files.isReadable(requestedFilePath))) {
				sendEmptyResponse(404, "File not found");
				return;
			}

			String extension = requestedFilePath.toString()
					.substring(requestedFilePath.toString().lastIndexOf(".") + 1);

			if (extension.equalsIgnoreCase("smscr")) {
				sendSmartScript(requestedFilePath);
				return;
			}

			String mimeValue = mimeTypes.get(extension);

			if (mimeValue == null) {
				mimeValue = "application/octet-stream";
			}

			try {
				sendResponse(requestedFilePath, mimeValue, 200);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/**
		 * Checks whether a session is recorded in the header of the received HTTP
		 * message
		 * 
		 * @param header The header as a list of Strings
		 */
		private synchronized void checkSession(List<String> header) {

			String sidCandidate = new String();

			for (String headerLine : header) {

				if (headerLine.startsWith("Cookie:")) {
					headerLine = headerLine.substring(7).trim();
					String[] cookies = headerLine.split(";");
					for (String cookie : cookies) {

						String[] nameValue = cookie.split("=");
						if (nameValue[0].trim().equals("sid")) {
							sidCandidate = nameValue[1].trim().substring(1, nameValue[1].length() - 1);
						}
					}
				}

			}

			if (sidCandidate.isEmpty()) {
				initializeSession();
				return;
			}

			if (!(sessions.containsKey(sidCandidate))) {
				initializeSession();
				return;
			}
			SessionMapEntry potentialSessionEntry = sessions.get(sidCandidate);

			if (!(potentialSessionEntry.getHost().equals(host))
					|| potentialSessionEntry.getValidUntil() < System.currentTimeMillis() / 1000
					|| potentialSessionEntry == null) {
				initializeSession();
				return;
			}

			potentialSessionEntry.setValidUntil(System.currentTimeMillis() / 1000 + sessionTimeout);
			this.SID = sidCandidate;
			this.permParams = potentialSessionEntry.map;

			return;
		}

		/**
		 * Initalizes a new session, called only if a session is not found in the header
		 */
		private void initializeSession() {

			this.SID = generateSID();

			SessionMapEntry entry = new SessionMapEntry(SID, host, System.currentTimeMillis() / 1000 + sessionTimeout,
					new ConcurrentHashMap<>());

			RCCookie cookie = new RCCookie("sid", SID, null, host, "/");
			outputCookies.add(cookie);
			sessions.put(SID, entry);
			this.permParams = entry.map;

		}

		/**
		 * Generates a SID. SID consists of 20 characters from A-Z randomly generated
		 * 
		 * @return
		 */
		private String generateSID() {
			int leftLimit = 65;
			int rightLimit = 90;
			int targetStringLength = 20;
			Random random = new Random();

			String generatedString = random.ints(leftLimit, rightLimit + 1).limit(targetStringLength)
					.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
			return generatedString;
		}

		/**
		 * Sends a HTTP response. The data to be sent with the response is located in
		 * the provided path.
		 * 
		 * @param requestedPath The path of the data to be sent
		 * @param mimeType      The required mime type
		 * @param statusCode    The status code of the message
		 * @throws IOException
		 */
		private void sendResponse(Path requestedPath, String mimeType, int statusCode) throws IOException {
			checkRequestContextValid();

			requestContext.setMimeType(mimeType);
			requestContext.setContentLength(requestedPath.toFile().length());
			requestContext.setStatusCode(statusCode);

			try (InputStream fis = Files.newInputStream(requestedPath)) {
				byte[] buf = new byte[1024];
				while (true) {
					int r = fis.read(buf);
					if (r < 1)
						break;
					requestContext.write(buf, 0, r);
				}

				oStream.flush();
				csocket.close();
			}
		}

		/**
		 * Sends an empty response, used when an error occurred
		 * 
		 * @param status        The status of the message
		 * @param statusMessage The message the HTTP response contains
		 */
		private void sendEmptyResponse(int status, String statusMessage) {
			checkRequestContextValid();

			requestContext.setStatusCode(status);
			requestContext.setStatusText(statusMessage);
			requestContext.setContentLength(0L);

			try {
				requestContext.write(new byte[0]);
				oStream.flush();
				csocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		/**
		 * Sends a HTTP message which contains a Smart Script file, executes the Smart
		 * Script file and writes it with the HTTP message
		 * 
		 * @param requestedFilePath The path of the Smart Script File
		 * @throws IOException
		 */
		private void sendSmartScript(Path requestedFilePath) throws IOException {

			checkRequestContextValid();

			String documentString = Files.readString(requestedFilePath);
			requestContext.setStatusCode(200);
			requestContext.setMimeType("text/html");
			requestContext.setStatusText("OK");
			new SmartScriptEngine(new SmartScriptParser(documentString).getDocumentNode(), requestContext).execute();

			oStream.flush();
			csocket.close();
		}

		/**
		 * Parses parameters from a URL from the provided String
		 * 
		 * @param paramString The provided String
		 */
		private void parseParameters(String paramString) {

			String[] keyValue = paramString.split("&");

			for (String key : keyValue) {
				params.put(key.split("=")[0].trim(), key.split("=")[1].trim());
			}
		}

		/**
		 * Extracts a header from the provided String.
		 * 
		 * @param requestString The HTTP request converted to a String
		 * @return
		 */
		private List<String> extractHeader(String requestString) {
			List<String> headers = new ArrayList<String>();
			String currentLine = null;
			for (String s : requestString.split("\n")) {
				if (s.isEmpty())
					break;
				char c = s.charAt(0);
				if (c == 9 || c == 32) {
					currentLine += s;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}
					currentLine = s;
				}
			}
			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}
			return headers;
		}

		/**
		 * Reads the request and returns it as a byte array
		 * 
		 * @return Returns the request as a byte array
		 * @throws IOException
		 */
		private Optional<byte[]> readRequest() throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = iStream.read();
				if (b == -1) {
					if (bos.size() != 0) {
						throw new IOException("Incomplete header received.");
					}
					return Optional.empty();
				}
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return Optional.of(bos.toByteArray());
		}

		/**
		 * Checks if a request context has been initialized, if not initializes it
		 */
		private void checkRequestContextValid() {
			if (requestContext == null) {
				requestContext = new RequestContext(oStream, params, permParams, outputCookies, tempParams, this, SID);
				return;
			}

		}
	}

}
