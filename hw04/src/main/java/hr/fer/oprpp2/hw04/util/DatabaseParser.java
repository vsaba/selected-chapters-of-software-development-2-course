package hr.fer.oprpp2.hw04.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.oprpp2.hw04.band.Band;

/**
 * A helper class used for parsing from a .txt file that simulates a database
 * @author Vito Sabalic
 *
 */
public class DatabaseParser {

	/**
	 * Parses the band names, id, and songs
	 * @param path The path of the file from which the data is read and parsed
	 * @return Returns a list populated by a {@link Band} class
	 * @throws IOException
	 */
	public static List<Band> parseVotingDefinition(String path) throws IOException {
		
		List<Band> bands = new ArrayList<>();
		try (InputStream is = Files.newInputStream(Paths.get(path))) {

			String text = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			List<String> list = Arrays.asList(text.split("\r\n"));

			for (String s : list) {
				String[] pom = s.split("\t");

				bands.add(new Band(Integer.valueOf(pom[0].trim()), pom[1].trim(), pom[2].trim()));
			}

		}
		
		return bands;
		
	}

	/**
	 * Parses the voting results from the given path
	 * @param path The provided path
	 * @return Returns a map whose key-s are band ids and the value are the number of votes 
	 * @throws IOException
	 */
	public static Map<Integer, Integer> parseVotingResults(String path) throws IOException {
		
		Map<Integer, Integer> votingResults = new HashMap<>();
		
		Path p = Paths.get(path);
		
		if(!Files.exists(p)){
			try {
				Files.createFile(p);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return votingResults;
		}
		
		
		try(InputStream is = Files.newInputStream(p)){
			
			String text = new String(is.readAllBytes(), StandardCharsets.UTF_8);
			
			if(text.isEmpty()) {
				return votingResults;
			}
			
			List<String> list = Arrays.asList(text.split("\r\n"));
			
			for(String s : list) {
				String[] pom = s.split("\t");
				
				votingResults.put(Integer.parseInt(pom[0].trim()), Integer.parseInt(pom[1].trim()));
			}
			
		}
		
		return votingResults;

	}
	
	/**
	 * Assigns the votes based on the map keys to the bands in the provided list
	 * @param votes The map containing voting results
	 * @param bands The list containing the stored bands
	 * @return Returns the modified band list
	 */
	public static List<Band> assignVotesToBands(Map<Integer, Integer> votes, List<Band> bands){
		
		
		for(Band band : bands) {
			if(votes.containsKey(band.getId())) {
				band.setVotes(votes.get(band.getId()));
			}
		}
		
		return bands;
	}
}
