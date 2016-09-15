import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.media.CannotRealizeException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AudioParser {

	/**
	 * @param args
	 */

	public ArrayList<String> getMetadata(File fl) {
		String fileLocation = fl.getPath();
		ArrayList<String> list = new ArrayList<String>();
		try {

			InputStream input = new FileInputStream(new File(fileLocation));
			
				ContentHandler handler = new DefaultHandler();
				Metadata metadata = new Metadata();
				Parser parser =  new Mp3Parser();
				ParseContext parseCtx = new ParseContext();
				parser.parse(input, handler, metadata, parseCtx);
				input.close();

				// List all metadata

				// Retrieve the necessary info from metadata
				// Names - title, xmpDM:artist etc. - mentioned below may differ
				// based
				System.out
						.println("----------------------------------------------");
				list.add((metadata.get("title")));
				list.add((metadata.get("xmpDM:artist")));
				list.add((metadata.get("xmpDM:composer")));
				list.add((metadata.get("xmpDM:genre")));
				list.add((metadata.get("xmpDM:album")));
				list.add((metadata.get("xmpDM:duration")));
				list.add((metadata.get("xmpDM:releaseDate")));
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;

	}
}