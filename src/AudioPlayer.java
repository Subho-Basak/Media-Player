import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.BasicStroke;
import java.awt.geom.Arc2D;
import java.awt.image.ImagingOpException;

import javax.swing.*;
import javax.imageio.ImageIO;
import javax.management.MXBean;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.spi.AudioFileReader;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.table.*;

import org.jaudiotagger.audio.*;
import org.jaudiotagger.tag.*;

import com.ibm.media.codec.audio.ima4.IMA4;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;
import com.sun.media.codec.audio.mpa.MpegAudio;

import AudioPlayer.Playlists.*;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.jlap;

import java.net.*;

@SuppressWarnings({ "serial", "unused" })
public class AudioPlayer extends JFrame implements ActionListener,
		MouseListener, ChangeListener {

	private Player player;
	private FileInputStream FIS;
	private BufferedInputStream BIS;
	private boolean canResume;
	private String path;
	private int total;
	private int stopped;
	private boolean valid;
	JLabel openFile;
	JLabel cross, minimize, minmark, maximize, next, previous, play, pause,
			volumeicon;
	JLabel sName, sArtist, metaicon, endTime, currentSec, label, singer, comp,
			loc;
	JLabel genre, album, year, comment, length, filenm;
	JFileChooser chooser;
	private File file;
	Mp3File mp3file = null;
	int minDur, seconds, interval, time;
	JSlider slider;
	JLabel albumMid, albumRgt, albumLft;
	boolean paused;
	JProgressBar adding;
	int a = 0, b = 0, c = 0, d = 0;
	static String maxColor;
	ImageIcon imageC, imageC1, imageC2;
	JLabel mediaBck1, mediaBck2, mediaBck3, mediaBck4;
	Timer tm, timer, looping;
	JPanel metadata, tableWindow, srtpnl;
	JButton colorpick;
	int curPos = 0;
	JLabel options, mxtbl;
	JLabel theme, mute, unmute, stop, loop, loopact;
	JPanel opPanel, songlist, wallpanel, gradientpanel;
	JPanel floater, loading;
	static AudioPlayer player1;
	int duration;
	JLabel restart, stp, tblclose, count, srt;
	JLabel t, art, gn, rld, crtsong;
	JPanel panel, addsongs, seek, seekbck;
	JScrollBar vBar;
	JScrollPane pane;
	boolean pressed = false;
	boolean active = false;
	int seekvalue;
	JLabel seekp, btn, vol;
	public boolean playing = true;
	boolean loopon = false;
	private DefaultListModel<String> model, dtlmodel;
	JTable table;
	String[] columnNames = { "path", ".", ".", "." };
	JToggleButton audio, volume;
	String var = "name";
	AudioFile af;
	JList<String> l, dl;
	Color color1;
	Color color2;
	JPanel volPan;
	static int width;
	static int height;

	public AudioPlayer() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		this.width = width;
		this.height = height;

		player = null;
		FIS = null;
		valid = false;
		BIS = null;
		path = null;
		total = 0;
		stopped = 0;
		canResume = false;
		makeMetadataWindow();
		makeDetailTable();
		playlist();
		makeGUI();

	}

	void playlist() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

			e.printStackTrace();
		}

		final JPanel pl = new JPanel();
		pl.setBounds(0, 32, (int) (0.235 * width), 29);
		pl.setBackground(Color.decode("#101010"));
		pl.setLayout(new BorderLayout());

		l = new JList<>(model = new DefaultListModel<String>());
		l.setBackground(Color.decode("#202020"));
		l.setForeground(Color.white);
		l.setFont(new Font("segoe ui", Font.ROMAN_BASELINE, 13));
		l.setSelectionBackground(Color.GRAY);
		l.setFixedCellHeight(50);
		l.setLayout(new BorderLayout());
		l.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane jsp = new JScrollPane(l);
		jsp.setBounds(0, 62, (int) (0.235 * width), height);
		add(jsp);

		ImageIcon plicon = new ImageIcon("playlist.png");
		JLabel add = new JLabel();
		add.setIcon(plicon);
		add.setBounds(10, -2, 32, 32);
		add.setCursor(getCursor().getPredefinedCursor(HAND_CURSOR));
		pl.add(add);

		ImageIcon mxicon = new ImageIcon("maximizetable.png");
		mxtbl = new JLabel();
		mxtbl.setIcon(mxicon);
		mxtbl.setBounds((int) (0.235 * width) - 32, -2, 32, 32);
		mxtbl.setCursor(getCursor().getPredefinedCursor(HAND_CURSOR));
		mxtbl.setToolTipText("View detailed List");
		mxtbl.addMouseListener(this);
		pl.add(mxtbl);

		btn = new JLabel("Songs");

		btn.setHorizontalAlignment(SwingConstants.CENTER);
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("segoe ui", Font.ROMAN_BASELINE, 13));

		pl.add(btn);
		add(pl);
		add.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (Exception e) {

					e.printStackTrace();
				}

				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(true);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"MP3 File", "mp3");
				fc.setFileFilter(filter);
				fc.showOpenDialog(new JFrame());

				File[] selectedFiles = fc.getSelectedFiles();

				if (selectedFiles != null) {

					/*
					 * addsongs = new JPanel(); // addsongs.setBounds(0, 0,
					 * 1400, 800); addsongs.setBackground(new Color(10, 10, 10,
					 * 200)); addsongs.setLayout(null); //
					 * addsongs.setVisible(false); loading = new JPanel();
					 * loading.setBounds(500, 280, 550, 200);
					 * loading.setBackground(Color.WHITE);
					 * loading.setLayout(null);
					 * 
					 * ImageIcon loader = new ImageIcon("loader.gif");
					 * 
					 * crtsong = new JLabel("Updating Your Music Library...",
					 * loader, JLabel.CENTER); crtsong.setBounds(-50, 50, 500,
					 * 80); crtsong.setForeground(Color.decode("#303030"));
					 * crtsong.setFont(new Font("segoe ui", Font.PLAIN, 15));
					 * loading.add(crtsong);
					 * 
					 * addsongs.add(loading); add(addsongs);
					 */

					for (int j = 0; j < selectedFiles.length; j++) {

						AudioDatabase adata = new AudioDatabase();
						adata.addAudioFile(selectedFiles[j]);
						model.addElement("" + selectedFiles[j].getName());
						System.out.println(model.size());
					}
				}

			}
		});
	}

	void makeDetailTable() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

			e.printStackTrace();
		}

		tableWindow = new JPanel();
		tableWindow.setBounds(0, (int) (0.04 * height), width, height
				- (int) (0.04 * height));
		tableWindow.setBackground(new Color(10, 10, 10, 240));
		tableWindow.setVisible(false);
		tableWindow.setLayout(null);
		add(tableWindow);

		ImageIcon cross = new ImageIcon("cross2.png");
		tblclose = new JLabel();

		tblclose.setBounds(width - 50, 0, 50, 50);
		tblclose.setIcon(cross);
		tblclose.addMouseListener(this);
		tblclose.setCursor(getCursor().getPredefinedCursor(HAND_CURSOR));
		tableWindow.add(tblclose);

		JLabel song = new JLabel("My Music Library");
		song.setBounds(100, 50, 250, 50);
		song.setForeground(Color.white);
		song.setFont(new Font("segoe ui light", Font.PLAIN, 35));
		tableWindow.add(song);

		count = new JLabel();
		count.setText(0 + " Songs");
		count.setForeground(Color.WHITE);
		count.setFont(new Font("segoe ui light", Font.PLAIN, 15));
		count.setBounds(1250, 70, 120, 30);
		tableWindow.add(count);

		JLabel sort = new JLabel("Sort By :");
		sort.setBounds(700, 70, 80, 30);
		sort.setForeground(Color.white);
		sort.setFont(new Font("segoe ui light", Font.PLAIN, 15));
		tableWindow.add(sort);

		srt = new JLabel("Title(A-Z)");
		srt.setBounds(770, 70, 200, 30);
		srt.setForeground(Color.WHITE);
		srt.addMouseListener(this);
		srt.setFont(new Font("segoe ui light", Font.PLAIN, 13));
		srt.setCursor(getCursor().getPredefinedCursor(HAND_CURSOR));
		tableWindow.add(srt);

		JSeparator sp = new JSeparator();
		sp.setBounds(40, 100, 1300, 1);
		sp.setForeground(Color.gray);
		tableWindow.add(sp);

		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(columnNames);

		table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		table.setModel(model);
		table.setRowHeight(40);
		table.setCursor(getCursor().getPredefinedCursor(HAND_CURSOR));
		table.setFont(new Font("segoe ui", Font.PLAIN, 14));
		table.setBackground(Color.decode("#bbbbbb"));
		table.setBorder(null);

		table.removeColumn(table.getColumnModel().getColumn(0));
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setShowGrid(false);
		table.setSelectionBackground(Color.gray);
		table.setFocusable(false);
		table.setForeground(Color.black);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(this);
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);

		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(5, (int) (0.156 * 768), width, height - 100);

		scroll.setBackground(getBackground());
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		table.addMouseListener(this);

		tableWindow.add(scroll);

		ArrayList<Playlist> cust_list = new ArrayList<Playlist>();

		Playlist plist;
		try {
			FileInputStream fin = new FileInputStream("list.dat");
			ObjectInput obj_in = new ObjectInputStream(fin);

			cust_list = (ArrayList<Playlist>) obj_in.readObject();
			int size = cust_list.size();
			System.out.println("Size :" + size);
			int i;
			for (i = 0; i < size; i++) {
				plist = cust_list.get(i);

				File file = plist.getFile();

				String path = file.getPath();
				String ttl = "" + file.getName();
				Test t = new Test();
				// char ch=t.extractFirstLetter(ttl);
				String init = null;

				AudioFile af = AudioFileIO.read(file);
				int duration = af.getAudioHeader().getTrackLength();
				int min = duration / 60;
				int sec = duration % 60;
				String dur = min + ":" + "" + sec;

				double sz = file.length();
				sz = (sz / 1048576);

				String length = (new DecimalFormat("##.##").format(sz) + "MB");

				// String lab = ("<html>" + ttl + "<br>" + dur + "<br>" + length
				// + "</html>");

				ImageIcon icon = new ImageIcon("msicon.png");

				MyRenderer renderer = new MyRenderer();

				renderer.getTableCellRendererComponent(table, icon);

				// table.getColumnModel().getColumn(0).setCellRenderer(renderer);

				table.setDefaultRenderer(Object.class,
						new DefaultTableCellRenderer() {
							@Override
							public Component getTableCellRendererComponent(
									JTable table, Object val, boolean selected,
									boolean hasFocus, int r, int c) {
								// TODO Auto-generated method stub

								Component c1 = super
										.getTableCellRendererComponent(table,
												val, selected, hasFocus, r, c);

								c1.setBackground(r % 2 == 0 ? Color
										.decode("#bbbbbb") : Color
										.decode("#f0f0f0"));
								c1.setForeground(Color.black);
								return c1;

							}

						});

				model.addRow(new Object[] { path, ttl, dur, length });

			}

			if (i < 1) {
				JOptionPane.showMessageDialog(null, "No Record Found", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			if (i == 1) {
				System.out.println(i + " Record Found");

			} else {
				System.out.println(i + " Records Found");
				count.setText(i + " Songs ");
			}

		} catch (Exception e) {
			System.out.println("File not found.");

		}

	}

	void makeMetadataWindow() {

		metadata = new JPanel();

		metadata.setBounds(320, 60, 700, 520);
		metadata.setBackground(new Color(10, 10, 10, 240));
		metadata.setLayout(null);
		metadata.addMouseListener(this);

		JLabel label = new JLabel("MetaData");
		label.setBounds(20, 20, 220, 50);
		label.setFont(new Font("segoe ui light", Font.ROMAN_BASELINE, 30));
		label.setForeground(Color.decode("#999999"));

		JLabel song = new JLabel();
		song.setBounds(120, 30, 300, 20);

		JSeparator s = new JSeparator();
		s.setBounds(10, 70, 680, 1);
		s.setForeground(Color.decode("#555555"));
		int x = 650;
		filenm = new JLabel("Song Title :	");
		filenm.setBounds(20, 115, x, 25);
		filenm.setForeground(Color.white);
		filenm.setFont(new Font("segoe ui", Font.ROMAN_BASELINE, 15));

		singer = new JLabel("Singer :");
		singer.setBounds(20, 160, x, 25);
		singer.setForeground(Color.white);
		singer.setFont(new Font("segoe ui", Font.ROMAN_BASELINE, 15));

		comp = new JLabel("Composer :");
		comp.setBounds(20, 205, x, 25);
		comp.setForeground(Color.white);
		comp.setFont(new Font("segoe ui", Font.ROMAN_BASELINE, 15));

		genre = new JLabel("Genre  :");
		genre.setBounds(20, 295, x, 25);
		genre.setForeground(Color.white);
		genre.setFont(new Font("segoe ui", Font.ROMAN_BASELINE, 15));

		album = new JLabel("Album  :	");
		album.setBounds(20, 250, x, 25);
		album.setFont(new Font("segoe ui", Font.ROMAN_BASELINE, 15));
		album.setForeground(Color.white);

		year = new JLabel("Release Year  :");
		year.setBounds(20, 340, x, 25);
		year.setFont(new Font("segoe ui", Font.ROMAN_BASELINE, 15));
		year.setForeground(Color.white);

		comment = new JLabel("Duration  :");
		comment.setBounds(20, 385, x, 25);
		comment.setFont(new Font("segoe ui", Font.ROMAN_BASELINE, 15));
		comment.setForeground(Color.white);

		length = new JLabel("Size  :");
		length.setBounds(20, 435, x, 25);
		length.setFont(new Font("segoe ui", Font.ROMAN_BASELINE, 15));
		length.setForeground(Color.white);

		loc = new JLabel("File Location : ");
		loc.setBounds(20, 475, x, 25);
		loc.setForeground(Color.white);
		loc.setFont(new Font("segoe ui", Font.ROMAN_BASELINE, 15));

		metadata.add(filenm);
		metadata.add(song);
		metadata.add(s);
		metadata.add(label);
		metadata.add(genre);
		metadata.add(album);
		metadata.add(year);
		metadata.add(comment);
		metadata.add(length);
		metadata.setVisible(false);
		metadata.add(singer);
		metadata.add(loc);
		metadata.add(comp);

		add(metadata);

	}

	@SuppressWarnings({ "static-access", "deprecation" })
	public void makeGUI() {

		/*
		 * try { UIManager.setLookAndFeel(UIManager
		 * .getCrossPlatformLookAndFeelClassName()); } catch (Exception e1) { //
		 * TODO Auto-generated catch block e1.printStackTrace(); }
		 * 
		 * try {
		 * UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 * } catch (Exception e) {
		 * 
		 * e.printStackTrace(); }
		 */

		ImageIcon rst = new ImageIcon("restart.png");
		restart = new JLabel();
		restart.setBounds(540, 707, 32, 32);
		restart.setCursor(getCursor().getPredefinedCursor(HAND_CURSOR));
		restart.addMouseListener(this);
		restart.setToolTipText("Restart");
		restart.setIcon(rst);
		restart.setBorder(null);
		add(restart);

		ImageIcon loopicon = new ImageIcon("loop.png");
		loop = new JLabel();
		loop.setIcon(loopicon);
		loop.setBounds(580, 710, 32, 32);
		loop.setToolTipText("Repeat");
		loop.setCursor(getCursor().getPredefinedCursor(HAND_CURSOR));
		loop.addMouseListener(this);
		add(loop);

		ImageIcon st = new ImageIcon("stop.png");
		stp = new JLabel();
		stp.setBounds(620, 712, 32, 32);
		stp.setIcon(st);
		stp.setCursor(getCursor().getPredefinedCursor(HAND_CURSOR));
		stp.setToolTipText("Stop");
		stp.addMouseListener(this);
		add(stp);

		ImageIcon opicon = new ImageIcon("settings.png");
		options = new JLabel("Options");
		options.setBounds(1250, 30, 120, 30);
		options.setFont(new Font("segoe ui", Font.ROMAN_BASELINE, 12));
		options.setCursor(getCursor().getPredefinedCursor(HAND_CURSOR));
		options.setIcon(opicon);
		options.addMouseListener(this);
		add(options);

		panel = new JPanel();
		panel.setBounds(380, 35, 100, 23);
		panel.setBackground(Color.decode("#dddddd"));
		panel.setVisible(false);

		ImageIcon open = new ImageIcon("open.png");
		openFile = new JLabel("Open File");
		openFile.setBounds((int) (0.235 * width) + 5, 35, (int) (0.09 * width),
				23);
		openFile.setFont(new Font("segoe ui", Font.ROMAN_BASELINE, 12));
		openFile.setIcon(open);
		openFile.setCursor(getCursor().getPredefinedCursor(HAND_CURSOR));
		openFile.setForeground(Color.darkGray);
		openFile.addMouseListener(this);

		add(openFile);
		add(panel);

		opPanel = new JPanel();
		opPanel.setBounds(1200, 63, 150, 140);
		opPanel.setBackground(new Color(20, 20, 20, 220));
		opPanel.setLayout(null);
		opPanel.setVisible(false);

		floater = new JPanel();
		floater.setBackground(Color.decode("#202020"));
		floater.setVisible(false);
		floater.setBounds(1200, 73, 150, 30);

		ImageIcon thicon = new ImageIcon("thicon.png");

		theme = new JLabel("Themes");
		theme.setIcon(thicon);
		theme.setBounds(0, 10, 150, 30);
		theme.setForeground(Color.white);
		theme.addMouseListener(this);
		theme.setFont(new Font("arial", Font.ROMAN_BASELINE, 12));

		mute = new JLabel("Mute");
		mute.setBounds(30, 40, 120, 30);
		mute.setForeground(Color.white);
		mute.addMouseListener(this);
		mute.setFont(new Font("arial", Font.ROMAN_BASELINE, 12));

		unmute = new JLabel("Unmute");
		unmute.setBounds(30, 70, 120, 30);
		unmute.setForeground(Color.white);
		unmute.addMouseListener(this);
		unmute.setFont(new Font("arial", Font.ROMAN_BASELINE, 12));

		stop = new JLabel("Stop");
		stop.setBounds(30, 100, 120, 30);
		stop.setForeground(Color.white);
		stop.addMouseListener(this);
		stop.setFont(new Font("arial", Font.ROMAN_BASELINE, 12));

		opPanel.add(theme);
		opPanel.add(mute);
		opPanel.add(unmute);
		opPanel.add(stop);

		add(opPanel);
		add(floater);

		sName = new JLabel("Song");
		sName.setBounds((int) (0.366 * width), (int) (0.781 * height),
				(int) (0.512 * width), 30);
		sName.setForeground(Color.decode("#dddddd"));
		sName.setFont(new Font("segoe ui", Font.ROMAN_BASELINE, 20));
		add(sName);

		sArtist = new JLabel("artist");
		sArtist.setBounds((int) (0.366 * width), (int) (0.781 * height) + 30,
				(int) (0.512 * width), 40);
		sArtist.setForeground(Color.white);
		sArtist.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
		add(sArtist);

		ImageIcon micon = new ImageIcon("metadatalist.png");
		metaicon = new JLabel();
		metaicon.setIcon(micon);
		metaicon.setBounds((int) (0.366 * width), 710, 25, 25);
		metaicon.setFont(new Font("Arial", Font.ROMAN_BASELINE, 15));
		metaicon.addMouseListener(this);
		metaicon.setToolTipText("View Audio Metadata");
		metaicon.setCursor(getCursor().getPredefinedCursor(HAND_CURSOR));
		add(metaicon);

		volume = new JToggleButton();
		final ImageIcon icon3 = new ImageIcon("volm.png");
		final ImageIcon icon2 = new ImageIcon("volum.png");

		volume.setIcon(icon2);
		volume.setFocusable(false);
		volume.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JToggleButton tBtn = (JToggleButton) e.getSource();
				if (tBtn.isSelected()) {
					volPan.setVisible(true);
				} else
					volPan.setVisible(false);
			}
		});
		volume.setBounds(325, 589, 38, 38);
		add(volume);

		JLabel aud = new JLabel("Audio");
		aud.setBounds(390, 600, 59, 20);
		aud.setFont(new Font("Arial", Font.PLAIN, 12));
		add(aud);
		ImageIcon icon = new ImageIcon("on.png");
		ImageIcon icon1 = new ImageIcon("off.png");
		audio = new JToggleButton();
		audio.setBounds(430, 600, 29, 20);
		audio.setFocusable(false);
		audio.setBackground(Color.GREEN);
		audio.setIcon(icon);
		audio.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ImageIcon icon = new ImageIcon("on.png");
				ImageIcon icon1 = new ImageIcon("off.png");
				JToggleButton tBtn = (JToggleButton) e.getSource();
				if (tBtn.isSelected()) {
					tBtn.setIcon(icon1);
					SoundVolumeDemo.sound(0.0f);
					slider.setEnabled(false);
					volume.setIcon(icon3);
				} else {
					tBtn.setIcon(icon);
					float fv = slider.getValue() / 100f;
					SoundVolumeDemo.sound(fv);
					slider.setEnabled(true);
					volume.setIcon(icon2);
				}
			}
		});
		add(audio);

		volPan = new JPanel();
		volPan.setBounds(320, 540, 324, 40);
		volPan.setBackground(Color.decode("#303030"));
		volPan.setLayout(null);
		volPan.setVisible(false);

		ImageIcon volume = new ImageIcon("volume.png");
		volumeicon = new JLabel();
		volumeicon.setIcon(volume);
		volumeicon.setBounds(5, 1, 40, 40);
		volPan.add(volumeicon);

		slider = new JSlider(JSlider.HORIZONTAL);
		slider.setBounds(58, 3, 210, 40);
		slider.setBackground(Color.decode("#303030"));
		slider.setBorder(null);
		SoundVolumeDemo.sound(0.5f);
		slider.setValue(50);
		slider.setPaintTrack(true);
		slider.addChangeListener(this);
		slider.setMajorTickSpacing(10);
		volPan.add(slider);

		vol = new JLabel();
		vol.setText("" + slider.getValue());
		vol.setBounds(280, 0, 40, 40);
		vol.setForeground(Color.white);
		vol.setFont(new Font("segoe ui", Font.ROMAN_BASELINE, 20));
		volPan.add(vol);

		add(volPan);

		label = new JLabel();
		JPanel cover = new JPanel();
		cover.setBounds(320, 625, 140, 140);
		ImageIcon albumcoverdefault = new ImageIcon("albumDefaultBttm.jpg");
		cover.setOpaque(false);
		label.setIcon(albumcoverdefault);
		cover.add(label);
		add(cover);

		JLabel label1 = new JLabel();
		JPanel cover1 = new JPanel();
		cover1.setBounds(320, 625, 140, 140);

		ImageIcon albumcoverdefault1 = new ImageIcon("albumDefaultBttm.jpg");
		cover1.setOpaque(false);
		label1.setIcon(albumcoverdefault1);
		cover1.add(label1);
		add(cover1);

		ImageIcon albumArt1 = new ImageIcon("albumDefaultMid.jpg");
		albumLft = new JLabel("", albumArt1, JLabel.CENTER);
		albumLft.setBounds(700, 195, 230, 230);
		add(albumLft);

		ImageIcon shadow = new ImageIcon("covershadow.png");
		JLabel albumLft = new JLabel("", shadow, JLabel.CENTER);
		albumLft.setBounds(700, 78, 230, 230);
		add(albumLft);

		ImageIcon albumArt2 = new ImageIcon("albumDefaultLR.jpg");
		albumMid = new JLabel("", albumArt2, JLabel.CENTER);
		albumMid.setBounds(800, 200, 220, 220);
		add(albumMid);

		ImageIcon shadowm = new ImageIcon("shadowm.png");
		JLabel albumMid = new JLabel("", shadowm, JLabel.CENTER);
		albumMid.setBounds(910, 200, 220, 220);
		add(albumMid);

		ImageIcon albumArt3 = new ImageIcon("albumDefaultLR.jpg");
		albumRgt = new JLabel("", albumArt3, JLabel.CENTER);
		albumRgt.setBounds(600, 200, 220, 220);
		add(albumRgt);

		ImageIcon shadowml = new ImageIcon("covershadow.png");
		JLabel albumLftl = new JLabel("", shadowml, JLabel.CENTER);
		albumLftl.setBounds(600, 83, 230, 230);
		// add(albumLftl);

		ImageIcon shadowl = new ImageIcon("shadowl.png");
		JLabel albumRgt = new JLabel("", shadowl, JLabel.CENTER);
		albumRgt.setBounds(489, 200, 220, 220);
		add(albumRgt);

		endTime = new JLabel("/ " + "00:00");
		endTime.setBounds(1163, 670, 40, 10);
		endTime.setForeground(Color.decode("#bbbbbb"));
		endTime.setFont(new Font("segoe ui", Font.PLAIN, 12));
		add(endTime);

		currentSec = new JLabel();
		currentSec.setBounds((int) (0.827 * width), (int) (0.872 * height), 40,
				10);
		currentSec.setForeground(Color.cyan);
		currentSec.setFont(new Font("segoe ui", Font.PLAIN, 12));
		currentSec.setText(a + "" + b + ":" + c + "" + d);
		add(currentSec);

		ImageIcon i1 = new ImageIcon("play.png");
		play = new JLabel();

		play.setBounds((int) (0.915 * width), (int) (0.872 * height), 60, 60);
		play.setIcon(i1);
		play.setToolTipText("Play");
		play.addMouseListener(this);
		add(play);

		ImageIcon i4 = new ImageIcon("pause.png");
		pause = new JLabel();
		pause.setBounds((int) (0.915 * width), (int) (0.872 * height), 60, 60);
		pause.setIcon(i4);
		pause.setToolTipText("Pause");
		pause.addMouseListener(this);
		pause.setVisible(false);
		add(pause);

		/*
		 * ImageIcon i = new ImageIcon("13.jpg"); JLabel l = new JLabel("", i,
		 * JLabel.CENTER); l.setBounds(460, 630, 1050, 150); add(l);
		 */

		ImageIcon albumArt01 = new ImageIcon("albumDefaultMid.jpg");
		JLabel albumLft0 = new JLabel("", albumArt01, JLabel.CENTER);
		albumLft0.setBounds(700, 195, 230, 230);
		add(albumLft0);

		ImageIcon albumArt02 = new ImageIcon("albumDefaultLR.jpg");
		JLabel albumMid0 = new JLabel("", albumArt02, JLabel.CENTER);
		albumMid0.setBounds(800, 200, 220, 220);
		add(albumMid0);

		ImageIcon albumArt03 = new ImageIcon("albumDefaultLR.jpg");
		JLabel albumRgt0 = new JLabel("", albumArt03, JLabel.CENTER);
		albumRgt0.setBounds(600, 200, 220, 220);
		add(albumRgt0);

		ImageIcon albumShadow = new ImageIcon("shadowMid.png");
		JLabel shadow1 = new JLabel("", albumShadow, JLabel.CENTER);
		shadow1.setBounds(700, 418, 230, 50);
		add(shadow1);

		ImageIcon albumShadow1 = new ImageIcon("shadowSide.png");
		JLabel shadow2 = new JLabel("", albumShadow1, JLabel.CENTER);
		shadow2.setBounds(600, 408, 220, 50);
		add(shadow2);

		ImageIcon albumShadow2 = new ImageIcon("shadowSide.png");
		JLabel shadow3 = new JLabel("", albumShadow2, JLabel.CENTER);
		shadow3.setBounds(800, 408, 220, 50);
		add(shadow3);

		JPanel translayer = new JPanel();
		translayer.setBounds((int) (0.235 * width), 62, width, 201);
		translayer.setBackground(new Color(250, 250, 250, 80));

		ImageIcon bckimg = new ImageIcon("musicbackground.jpg");
		Image image1 = bckimg.getImage().getScaledInstance(
				width - (int) (0.235 * width), 200, Image.SCALE_SMOOTH);
		bckimg = new ImageIcon(image1);
		JLabel bcklit = new JLabel("", bckimg, JLabel.CENTER);
		bcklit.setBounds((int) (0.235 * width), 62, width
				- (int) (0.235 * width), 200);

		add(translayer);
		add(bcklit);

		ImageIcon cross1 = new ImageIcon("cross.png");

		cross = new JLabel();
		cross.setIcon(cross1);
		cross.addMouseListener(this);
		cross.setBounds(width - 40, 0, 30, 30);

		add(cross);

		ImageIcon mark = new ImageIcon("minmark.png");
		minmark = new JLabel();
		minmark.setIcon(mark);
		minmark.addMouseListener(this);
		minmark.setBounds(width - 90, 0, 30, 30);
		add(minmark);

		try {
			UIManager.setLookAndFeel(UIManager
					.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		seek = new JPanel();
		seek.setBounds((int) (0.366 * width), (int) (0.90 * height), 0, 4);
		seek.setBackground(Color.decode("#ffffff"));
		add(seek);

		seekbck = new JPanel();
		seekbck.setBounds((int) (0.366 * width), (int) (0.90 * height),
				(int) (0.51 * width), 4);
		seekbck.setBackground(new Color(200, 200, 200, 150));
		add(seekbck);

		gradientpanel = new JPanel() {

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_QUALITY);
				int w = 1100;
				int h = 200;
				color1 = Color.decode("#151515");
				color2 = Color.decode("#555555");
				GradientPaint gp = new GradientPaint(0, 0, color2, w, h, color1);
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, w, h);
			}
		};

		gradientpanel.setBounds((int) (0.21 * width), (int) (0.760 * height),
				1100, 200);
		add(gradientpanel);

		JPanel panelshdw = new JPanel() {

			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
						RenderingHints.VALUE_RENDER_QUALITY);
				int w = 1100;
				int h = 10;
				color1 = Color.gray;
				color2 = Color.decode("#ffffff");
				GradientPaint gp = new GradientPaint(0, 0, color2, 0, h, color1);
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, w, h);
			}
		};

		panelshdw.setBounds(300, 575, 1100, 10);
		add(panelshdw);

	}

	private void chooseFile() {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

			e.printStackTrace();
		}

		AudioDatabase adata = null;

		chooser = new JFileChooser("E:\\");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"MP3 File", "mp3");
		chooser.setFileFilter(filter); // filtering the type of file

		int returnVal = chooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			curPos = 0;
			if (timer != null) {
				timer.cancel();
			}
			a = b = c = d = 0;
			file = chooser.getSelectedFile();

			try {
				af = AudioFileIO.read(file);
				duration = af.getAudioHeader().getTrackLength();
				int min = duration / 60;
				int sec = duration % 60;
				endTime.setText("/" + min + ":" + "" + sec);
				comment.setText("Duration  :              " + min + ":" + ""
						+ sec);
				label.setIcon(null);
				albumMid.setIcon(null);
				albumLft.setIcon(null);
				albumRgt.setIcon(null);

				getMetadata(af);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// choosing the desired file to
			if (player != null) {
				player.close();
				tm.cancel();
			}
			getMetadata(af);
			createPlayer();

		} else
			file = null;

	}

	public void getMetadata(AudioFile af) {
		filenm.setText("File Name :           " + file.getName());

		AudioParser ap = new AudioParser();
		ArrayList<String> list = ap.getMetadata(file);

		filenm.setText("Song Title :             " + list.get(0));
		if (filenm.getText() == null) {
			filenm.setText("Song Title :           " + file.getName());
		}
		sName.setText(list.get(0));
		if (sName.getText() == null) {
			sName.setText(file.getName());
		}
		sArtist.setText(list.get(1));
		singer.setText("Singer :                   " + list.get(1));
		comp.setText("Composer :             " + list.get(2));
		genre.setText("Genre :                    " + list.get(3));
		album.setText("Album :                   " + list.get(4));
		year.setText("Release Year :         " + list.get(6));

		loc.setText("File Location :         " + file.getPath());
		int min = duration / 60;
		int sec = duration % 60;
		endTime.setText("/" + min + ":" + "" + sec);
		comment.setText("Duration  :              " + min + ":" + "" + sec);
		double size = file.length();
		size = (size / 1048576);

		length.setText("Size :                       "
				+ new DecimalFormat("##.##").format(size) + "MB");

		try {
			mp3file = new Mp3File("" + file);

		} catch (Exception e) {

			System.out.println("File not found.");

		}
		if (mp3file.hasId3v1Tag()) {
			ID3v1 id3v1Tag = mp3file.getId3v1Tag();
			ID3v2 id3v2Tag = mp3file.getId3v2Tag();

			imageC = new ImageIcon(id3v2Tag.getAlbumImage());
			imageC1 = new ImageIcon(id3v2Tag.getAlbumImage());
			imageC2 = new ImageIcon(id3v2Tag.getAlbumImage());
			Image image1 = imageC1.getImage().getScaledInstance(230, 230,
					Image.SCALE_SMOOTH);
			Image image3 = imageC2.getImage().getScaledInstance(220, 220,
					Image.SCALE_SMOOTH);
			Image image2 = imageC.getImage().getScaledInstance(140, 140,
					Image.SCALE_SMOOTH);

			imageC = new ImageIcon(image2);
			imageC1 = new ImageIcon(image1);
			imageC2 = new ImageIcon(image3);

			label.setIcon(imageC);
			albumMid.setIcon(imageC2);
			albumLft.setIcon(imageC1);
			albumRgt.setIcon(imageC2);

		}
	}

	public void createPlayer() {

		player1.setPath(path);
		if (file == null) {
			file = new File(path);
		}
		System.out.println(path);

		player1.play(-1);

		pause.setVisible(true);
		play.setVisible(false);
	}

	public boolean canResume() {
		return canResume;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void pause() {
		try {
			stopped = FIS.available();
			FIS.skip(5000);
			player.close();
			FIS = null;
			BIS = null;
			player = null;
			tm.cancel();
			timer.cancel();
			if (valid)
				canResume = true;
		} catch (Exception e) {

		}
	}

	public void resume() {
		if (!canResume)
			return;
		if (play(total - stopped))
			canResume = false;
	}

	public boolean play(int pos) {
		valid = true;
		canResume = false;

		try {
			FIS = new FileInputStream(file);
			System.out.println(file);
			total = FIS.available();
			if (pos > -1)
				FIS.skip(pos);
			BIS = new BufferedInputStream(FIS);
			player = new Player(BIS);

			startSeeking();
			startTimer();

			new Thread(new Runnable() {
				public void run() {
					try {
						player.play();

					} catch (Exception e) {

						valid = false;
					}
				}

			}).start();

		} catch (Exception e) {

			valid = false;
		}
		return valid;
	}

	public void startTimer() {

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {

				if (d == 9 && c == 5) {
					b++;
					c = 0;
					d = 0;

				} else if (d >= 9) {
					c++;
					d = 0;
				} else
					d++;
				currentSec.setText(a + "" + b + ":" + c + "" + d);
			}
		}, 0, 1000);
	}

	public void startSeeking() {
		try {
			af = AudioFileIO.read(file);
			duration = af.getAudioHeader().getTrackLength();
			getMetadata(af);
		} catch (Exception e) {

			e.printStackTrace();
		}

		tm = new Timer();
		tm.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (curPos == 700) {
					seek.setSize(0, 4);
					tm.cancel();

				} else {
					seek.setSize(curPos, 4);
					curPos++;
				}
			}

		}, 0, (long) (duration * 1.44));

	}

	public void enableLooping() {
		looping = new Timer();
		looping.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (loopon == true) {
					if (player.isComplete()) {
						player.close();
						curPos = 0;
						timer.cancel();
						tm.cancel();
						a = b = c = d = 0;
						createPlayer();

					}
				} else {

					cancelLooping();
				}
			}
		}, 0, duration * 10);

	}

	void cancelLooping() {
		looping.cancel();
	}

	public static void main(String[] args) {

		player1 = new AudioPlayer();

		player1.setExtendedState(JFrame.MAXIMIZED_BOTH);
		player1.setLayout(null);
		player1.setUndecorated(true);
		player1.setVisible(true);
		player1.getContentPane().setBackground(Color.white);

		JPanel hdpnl = new JPanel();
		hdpnl.setBounds(width / 3, 0, width / 3, (int) (height * 0.04) + 1);
		hdpnl.setBackground(Color.decode("#a0a0a0"));

		JLabel tile = new JLabel("AMPLIFIER");

		tile.setFont(new Font("yu gothic", Font.ROMAN_BASELINE, 18));
		hdpnl.add(tile);
		player1.add(hdpnl);

		player1.setIconImage(Toolkit.getDefaultToolkit()
				.getImage("mainico.png"));

		JSeparator sp = new JSeparator();
		sp.setBounds(0, (int) (height * 0.04) + 1, width, 1);
		sp.setBorder(null);
		sp.setForeground(Color.gray);
		player1.add(sp);
	}

	public void actionPerformed(ActionEvent ac) {

		if (ac.getSource() == colorpick) {
			Color c = JColorChooser.showDialog(null, "Choose color",
					colorpick.getForeground());
			if (c != null)
				colorpick.setForeground(c);
		}

	}

	public void mouseClicked(MouseEvent act) {
		if (act.getSource() == openFile) {
			chooseFile();
		}
		if (act.getSource() == stp) {
			player.close();
			seek.setSize(0, 4);
			tm.cancel();
			timer.cancel();
			a = b = c = d = 0;
			currentSec.setText(a + "" + b + ":" + c + "" + d);
			playing = false;
			pause.setVisible(false);
			play.setVisible(true);
		}

		if (act.getSource() == restart) {
			player.close();
			curPos = 0;
			timer.cancel();
			tm.cancel();
			a = b = c = d = 0;
			createPlayer();
		}
		if (act.getSource() == pause) {
			player1.pause();
			play.setVisible(true);
			pause.setVisible(false);
			playing = true;

		}

		if (act.getSource() == play) {

			if (playing == true) {
				player1.resume();
				pause.setVisible(true);
				play.setVisible(false);
			} else {
				createPlayer();

			}
		}

		if (act.getSource() == cross) {
			if (file == null || player == null)
				dispose();

			else {

				player.close();
				dispose();

			}

		}

		if (act.getSource() == metaicon) {

			if (active == false) {
				metadata.setVisible(true);
				active = true;

			} else if (active == true) {
				metadata.setVisible(false);
				active = false;
			}
		}

		if (act.getSource() == minmark)
			setState(Frame.ICONIFIED);

		if (act.getSource() == options) {

			if (pressed == false) {
				opPanel.setVisible(true);
				floater.setVisible(true);
				pressed = true;
			} else if (pressed = true) {
				opPanel.setVisible(false);
				floater.setVisible(false);
				pressed = false;
			}

		}

		if (act.getSource() == loop) {
			if (loopon == false) {
				ImageIcon loopact = new ImageIcon("loopact.png");
				loop.setIcon(loopact);

				loopon = true;
				enableLooping();
				loop.setToolTipText("Repeat(ON)");
			} else if (loopon == true) {
				ImageIcon loop1 = new ImageIcon("loop.png");
				loop.setIcon(loop1);

				loopon = false;
				loop.setToolTipText("Repeat(OFF)");
			}
		}
		if (act.getSource() == mxtbl) {

			tableWindow.setVisible(true);
		}
		if (act.getSource() == tblclose) {

			tableWindow.setVisible(false);

		}
		if (act.getSource() == table) {
			if (act.getClickCount() == 2) {
				if (player != null) {

					player.close();
					file = null;
					curPos = 0;
					timer.cancel();
					tm.cancel();
					a = b = c = d = 0;

				}

				path = ""
						+ (table.getModel().getValueAt(table.getSelectedRow(),
								0));

				createPlayer();
				tableWindow.setVisible(false);
			}

		}

	}

	public void mouseEntered(MouseEvent m) {
		// TODO Auto-generated method stub

		if (m.getSource() == metaicon) {
			ImageIcon ic = new ImageIcon("metalisthover.png");
			metaicon.setIcon(ic);
		}
		if (m.getSource() == theme) {
			floater.setBounds(1200, 73, 150, 30);
		}
		if (m.getSource() == mute) {
			floater.setBounds(1200, 103, 150, 30);
		}
		if (m.getSource() == unmute) {
			floater.setBounds(1200, 133, 150, 30);
		}
		if (m.getSource() == stop) {

			floater.setBounds(1200, 163, 150, 30);
		}

		if (m.getSource() == restart) {

			ImageIcon rsth = new ImageIcon("restarthover.png");
			restart.setIcon(rsth);
		}
		if (m.getSource() == srtpnl || m.getSource() == gn) {
			srtpnl.setVisible(true);
		}

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == restart) {
			ImageIcon rst = new ImageIcon("restart.png");
			restart.setIcon(rst);
		}

		if (e.getSource() == metaicon) {
			ImageIcon ic = new ImageIcon("metadatalist.png");
			metaicon.setIcon(ic);
		}

		if (e.getSource() == srtpnl)
			srtpnl.setVisible(false);
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void stateChanged(ChangeEvent g) {
		// TODO Auto-generated method stub
		if (g.getSource() == slider) {
			vol.setText("" + slider.getValue());
			float f = slider.getValue() / 100f;
			System.out.println(f);

			SoundVolumeDemo.sound(f);
		}
	}

}
