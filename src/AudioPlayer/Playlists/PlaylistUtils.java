package AudioPlayer.Playlists;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PlaylistUtils {
	ArrayList<Playlist> song_list;


	public void addSongs(Playlist plist) {
		try {
			FileInputStream fin = new FileInputStream("Playlist.dat");
			ObjectInputStream obj_in = new ObjectInputStream(fin);

			song_list = (ArrayList<Playlist>) obj_in.readObject();

			song_list.add(plist);
			FileOutputStream fout = new FileOutputStream("Playlist.dat");
			ObjectOutputStream obj_out = new ObjectOutputStream(fout);

			obj_out.writeObject(song_list);

			fout.close();
			obj_out.close();

			System.out.println("Song Added");

		} catch (Exception e) {

			try {

				FileOutputStream fout = new FileOutputStream("Playlist.dat");
				ObjectOutputStream obj_out = new ObjectOutputStream(fout);

				song_list = new ArrayList<Playlist>();

				song_list.add(plist);

				obj_out.writeObject(song_list);

				fout.close();
				obj_out.close();

				System.out.println("Song added");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void addFiles(Playlist plist) {
		try {
			FileInputStream fin = new FileInputStream("list.dat");
			ObjectInputStream obj_in = new ObjectInputStream(fin);

			song_list = (ArrayList<Playlist>) obj_in.readObject();

			song_list.add(plist);
			FileOutputStream fout = new FileOutputStream("list.dat");
			ObjectOutputStream obj_out = new ObjectOutputStream(fout);

			obj_out.writeObject(song_list);

			fout.close();
			obj_out.close();

			System.out.println("Song Added");

		} catch (Exception e) {

			try {

				FileOutputStream fout = new FileOutputStream("list.dat");
				ObjectOutputStream obj_out = new ObjectOutputStream(fout);

				song_list = new ArrayList<Playlist>();

				song_list.add(plist);

				obj_out.writeObject(song_list);

				fout.close();
				obj_out.close();

				System.out.println("Song added");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	
}
