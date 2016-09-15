import java.awt.Image;
import java.awt.image.DataBufferUShort;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.xml.sax.ContentHandler;
import org.xml.sax.helpers.DefaultHandler;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

import AudioPlayer.Playlists.Playlist;
import AudioPlayer.Playlists.PlaylistUtils;

public class AudioDatabase {

	public void addAudioFile(File file) {

		try {

			PlaylistUtils putil = new PlaylistUtils();
			Playlist list = new Playlist(file);
			putil.addFiles(list);
		} catch (Exception e) {
			System.out.println("Not added");
			System.out.println("File not found.");
		}
	}

}
