import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Test {

		
		public char extractFirstLetter(String s){

		char ch = 0;
		for (int i = 0; i < s.length(); i++) {

			if (s.substring(i, i + 1).matches("[A-Z]")) {

				System.out.println("first Character at :" + i);
				System.out.println("First character is :" + s.charAt(i));
				ch=s.charAt(i);

				break;

			}
		
		}
		return ch;
	}

}
