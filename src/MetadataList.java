import java.awt.Color;
import java.awt.Font;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

@SuppressWarnings("serial")
public class MetadataList extends JFrame {
	static JLabel album, genre, comment, year, length;

	MetadataList(String a, String g, String c, String y, String l) {

		makeForm();


	}

	void makeForm() {
		 Border loweredBorder = new EtchedBorder(EtchedBorder.LOWERED);
	
		getContentPane().setBackground(new Color(10, 10, 10, 250));
		setUndecorated(true);
		setBounds(400, 200, 300, 400);
		setAlwaysOnTop(true);
		
		setLayout(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		//new MetadataList(album, genre, comment, year, length);
	}
}
