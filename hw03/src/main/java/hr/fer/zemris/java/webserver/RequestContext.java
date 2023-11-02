package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A class which stores and provides all values and necessary methods to save a
 * Http request, and create a http response
 * 
 * @author Vito Sabalic
 *
 */
public class RequestContext {

	private OutputStream outputStream;
	private Charset charset;
	private String encoding;
	private int statusCode;
	private String statusText;
	private String mimeType;
	private Long contentLength;
	private Map<String, String> parameters;
	private Map<String, String> temporaryParameters;
	private Map<String, String> persistentParameters;
	private List<RCCookie> outputCookies;
	private IDispatcher dispatcher;
	private boolean headerGenerated;
	private String SID;

	/**
	 * A private class which represents the cookie technology
	 * 
	 * @author Vito Sabalic
	 *
	 */
	public static class RCCookie {

		private String name;
		private String value;
		private String domain;
		private String path;
		private Integer maxAge;
		private boolean httpCookie;

		/**
		 * A simple constructor, initializes all valeus
		 * 
		 * @param name
		 * @param value
		 * @param maxAge
		 * @param domain
		 * @param path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.maxAge = maxAge;
			this.domain = domain;
			this.path = path;
			this.httpCookie = false;
		}

		/**
		 * A setter for the the httpCookie flag
		 * 
		 * @param flag The value of the flag
		 */
		public void setHttpCookie(boolean flag) {
			this.httpCookie = flag;
		}

		/**
		 * Returns true if cookie is httpCookie, false otherwise
		 * 
		 * @return
		 */
		public boolean isHttpCookie() {
			return this.httpCookie;
		}

		/**
		 * A getter for the cookie name
		 * 
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * A getter for the cookie value
		 * 
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * A getter for the cookie domain
		 * 
		 * @return the domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * A getter for the cookie path
		 * 
		 * @return the path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * A getter for the cookie maximum age of the cookie
		 * 
		 * @return the maximum age of the cookie
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			RCCookie other = (RCCookie) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

	}

	/**
	 * A constructor which assigns all values or initializes them
	 * 
	 * @param outputStream
	 * @param parameters
	 * @param persistentParameters
	 * @param outputCookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		super();
		if (outputStream == null)
			throw new NullPointerException("Output stream can't be null!");

		this.outputStream = outputStream;
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.temporaryParameters = new HashMap<>();
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;
		this.encoding = "UTF-8";
		this.statusCode = 200;
		this.statusText = "OK";
		this.mimeType = "text/html";
		this.contentLength = null;
		this.headerGenerated = false;
	}

	/**
	 * A more precise contructor, assigns all values or initializes them
	 * 
	 * @param outputStream
	 * @param parameters
	 * @param persistentParameters
	 * @param outputCookies
	 * @param temporaryParameters
	 * @param dispatcher
	 * @param SID
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher, String SID) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		this.temporaryParameters = temporaryParameters;
		this.dispatcher = dispatcher;
		this.SID = SID;
	}

	/**
	 * Sets the encoding of the HTTP response
	 * 
	 * @param encoding The provided encoding
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new IllegalArgumentException("Unable to change the value after the header has been generated");
		}

		if (encoding == null)
			throw new NullPointerException("Encoding can't be null!");

		this.encoding = encoding;
		this.charset = Charset.forName(encoding);
	}

	/**
	 * Sets the status code of the HTTP response
	 * 
	 * @param encoding The provided status code
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new IllegalArgumentException("Unable to change the value after the header has been generated");
		}

		this.statusCode = statusCode;
	}

	/**
	 * Sets the status text of the HTTP response
	 * 
	 * @param encoding The provided status text
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new IllegalArgumentException("Unable to change the value after the header has been generated");
		}
		this.statusText = statusText;
	}

	/**
	 * Sets the mime type of the data sent in the HTTP response
	 * 
	 * @param encoding The provided mime type
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new IllegalArgumentException("Unable to change the value after the header has been generated");
		}
		this.mimeType = mimeType;
	}

	/**
	 * Sets the content length of the data sent in the HTTP response
	 * 
	 * @param encoding The provided length
	 */
	public void setContentLength(Long contentLength) {
		if (headerGenerated) {
			throw new IllegalArgumentException("Unable to change the value after the header has been generated");
		}
		this.contentLength = contentLength;
	}

	/**
	 * A getter for a parameter stored under the provided name key
	 * 
	 * @param name the provided name key
	 * @return The parameter
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Returns all keys in the parameters map. Returns it as an unmodifiable map
	 * 
	 * @return The key set
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(new HashSet<>(parameters.keySet()));
	}

	/**
	 * A getter for a persistent parameter stored under the provided name key
	 * 
	 * @param name the provided name key
	 * @return The persistent parameter
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Returns all keys in the persistent parameters map. Returns it as an
	 * unmodifiable map
	 * 
	 * @return The key set
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(new HashSet<>(persistentParameters.values()));
	}

	/**
	 * Sets the provided value under the provided name key in the persistent
	 * parameter map
	 * 
	 * @param name  The provided key
	 * @param value The provided value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Removes the persistent parameter stored under the provided key name
	 * 
	 * @param name The provided key
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * A getter for the temporary parameter stored under the provided name key
	 * 
	 * @param name The provided key
	 * @return The stored value
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Returns all keys in the temporary parameters map. Returns it as an
	 * unmodifiable map
	 * 
	 * @return The key set
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(new HashSet<>(temporaryParameters.values()));
	}

	/**
	 * Sets the provided value under the provided name key in the temporary
	 * parameter map
	 * 
	 * @param name  The provided key
	 * @param value The provided value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Removes the persistent parameter stored under the provided key name
	 * 
	 * @param name The provided key
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Adds a cookie the RCCookie list
	 * 
	 * @param cookie The cookie to be added
	 */
	public void addRCCookie(RCCookie cookie) {
		if (headerGenerated) {
			throw new IllegalArgumentException("Unable to change the value after the header has been generated");
		}
		outputCookies.add(cookie);
	}

	/**
	 * Removes the cookie from the parameters list
	 * 
	 * @param cookie The provided cookie
	 */
	public void removeCookie(RCCookie cookie) {
		if (headerGenerated) {
			throw new IllegalArgumentException("Unable to change the value after the header has been generated");
		}
		outputCookies.remove(cookie);
	}

	/**
	 * Returns the current session ID
	 * 
	 * @return
	 */
	public String getSessionID() {
		return SID;
	}

	/**
	 * A getter for the message dispatcher
	 * 
	 * @return The dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Writes the provided data to the current output stream. Generates a header for
	 * the HTTP message, if necessary
	 * 
	 * @param data The provided data
	 * @return Returns this
	 * @throws IOException
	 */
	public RequestContext write(byte[] data) throws IOException {
		if (!headerGenerated) {
			writeHeader();
		}

		outputStream.write(data);

		return this;
	}

	/**
	 * Writes the provided data to the current output stream. Takes into account the
	 * provided restrictions Generates a header for the HTTP message, if necessary
	 * 
	 * @param data   The provided data
	 * @param offset
	 * @param len
	 * @return Returns this
	 * @throws IOException
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!headerGenerated) {
			writeHeader();
		}

		outputStream.write(data, offset, len);

		return this;
	}

	/**
	 * Writes the provided String to the current output stream. Generates a header
	 * if necessary
	 * 
	 * @param text The provided string
	 * @return Returns this
	 * @throws IOException
	 */
	public RequestContext write(String text) throws IOException {
		if (!headerGenerated) {
			writeHeader();
		}

		outputStream.write(text.getBytes(charset));

		return this;
	}

	/**
	 * Writes a header for the HTTP message
	 * 
	 * @throws IOException
	 */
	private void writeHeader() throws IOException {
		charset = Charset.forName(encoding);
		headerGenerated = true;

		String text = "HTTP/1.1 " + statusCode + " " + statusText + "\r\n";

		text += "Content-Type: " + mimeType;
		if (mimeType.contains("text")) {
			text += "; charset=" + encoding;
		}
		text += "\r\n";

		if (contentLength != null) {
			text += "Content-Length: " + contentLength + "\r\n";
		}

		if (!outputCookies.isEmpty()) {

			for (RCCookie cookie : outputCookies) {
				text += "Set-Cookie: " + cookie.getName() + "=\"" + cookie.getValue() + "\"";

				if (cookie.getDomain() != null) {
					text += "; Domain=" + cookie.getDomain();
				}
				if (cookie.getPath() != null) {
					text += "; Path=" + cookie.getPath();
				}
				if (cookie.getMaxAge() != null) {
					text += "; Max-Age=" + cookie.getMaxAge();
				}
				if (cookie.isHttpCookie()) {
					text += "; HttpOnly";
				}

				text += "\r\n";

			}
		}

		text += "\r\n";

		outputStream.write(text.getBytes(charset));

	}

}
