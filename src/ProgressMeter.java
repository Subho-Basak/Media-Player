import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ProgressMeter extends JFrame {
	JPanel p1, p2;
	Timer timer;
	int w = 0;

	public ProgressMeter() {
		// TODO Auto-generated constructor stub
		p2 = new JPanel();
		p2.setBackground(Color.black);
		p2.setBounds(200, 400, 0, 3);

		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				w = w + 1;

				p2.setSize(w, 3);
				add(p2);
			}
		}, 0, 1000);

		p1 = new JPanel();
		p1.setBounds(200, 400, 400, 3);
		p1.setBackground(new Color(200, 200, 200, 150));
		add(p1);

	}

	public static void main(String[] args) {
		ProgressMeter m = new ProgressMeter();
		m.setExtendedState(JFrame.MAXIMIZED_BOTH);
		m.setLayout(null);
		m.setVisible(true);

	}
}