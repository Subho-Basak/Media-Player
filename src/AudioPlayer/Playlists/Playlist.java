package AudioPlayer.Playlists;

import java.io.File;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class Playlist implements Serializable {
	@Override
	public String toString() {
		return "Playlist [path=" + path + ", name=" + name + ", dur=" + dur
				+ ", size=" + size + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String path, name, dur, size;
File file;
	public Playlist(String path, String name, String dur, String size) {
		super();

		this.path = path;
		this.name = name;
		this.size = size;
		this.dur = dur;

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDur() {
		return dur;
	}

	public void setDur(String dur) {
		this.dur = dur;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
public Playlist(File file){
	this.file=file;
}

public File getFile() {
	return file;
}

public void setFile(File file) {
	this.file = file;
}
}
