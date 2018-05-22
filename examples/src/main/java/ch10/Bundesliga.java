package ch10;

import org.simpleflatmapper.csv.CsvMapperFactory;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.csv.CsvWriter;
import org.simpleflatmapper.map.property.RenameProperty;

import java.io.*;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class Bundesliga {
	Map<Integer, Verein> vereine;
	List<Spiel> spiele;

	private Bundesliga(Map<Integer, Verein> vereine, List<Spiel> spiele) {
		this.vereine = vereine;
		this.spiele = spiele;
	}

	static Bundesliga loadFromResource() throws IOException {

		ClassLoader classLoader = Bundesliga.class.getClassLoader();

		Reader reader2 = new InputStreamReader(classLoader.getResourceAsStream("bundesliga_Spiel.csv"));
		List<Spiel> spiele = CsvParser
				.separator(';')
				//.mapTo(Spiel.class)  <-- warum geht das nicht?! :-(
				.skip(1)
				.stream(reader2)
				.map(Spiel::fromCSV)
				.collect(Collectors.toList());

		Reader reader1 = new InputStreamReader(classLoader.getResourceAsStream("bundesliga_Verein.csv"));
		Map<Integer, Verein> vereine = CsvParser
				.separator(';')
				//.mapTo(Verein.class)  <-- gleiches Problem wie oben...
				.skip(1)
				.stream(reader1)
				.map(Verein::fromCSV)
				.collect(Collectors.toMap(Verein::getId, Function.identity()));

		return new Bundesliga(vereine, spiele);
	}


}