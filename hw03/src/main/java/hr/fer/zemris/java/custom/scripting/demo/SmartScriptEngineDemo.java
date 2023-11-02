package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Demo class for {@link SmartScriptEngine}
 * @author Vito Sabalic
 *
 */
public class SmartScriptEngineDemo {

	public static void main(String[] args) throws IOException {
		String documentBody = Files.readString(Paths.get("webroot/scripts/fibonaccih.smscr"));
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		Map<String, String> tempParam = new HashMap<>();
		
		tempParam.put("title", "The color has been changed");
		// create engine and execute it
		new SmartScriptEngine(
		new SmartScriptParser(documentBody).getDocumentNode(),
		new RequestContext(System.out, parameters, persistentParameters, cookies, tempParam, null, null)
		).execute();




	}

}
