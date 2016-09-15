import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;

public class SoundVolumeDemo {

	public static void sound(float f) {
		Info source = Port.Info.SPEAKER;
		// source = Port.Info.LINE_OUT;
		// source = Port.Info.HEADPHONE;

		if (AudioSystem.isLineSupported(source)) {
			try {
				Port outline = (Port) AudioSystem.getLine(source);
				outline.open();
				FloatControl volumeControl = (FloatControl) outline
						.getControl(FloatControl.Type.VOLUME);

				volumeControl.setValue(f);
				
				
			} catch (LineUnavailableException ex) {
				System.err.println("source not supported");
				ex.printStackTrace();
			}
		}
		
	}
	
	public static int returnVolume() {
		Info source = Port.Info.SPEAKER;
		// source = Port.Info.LINE_OUT;
		// source = Port.Info.HEADPHONE;
		int f=0;
		if (AudioSystem.isLineSupported(source)) {
			
			try {
				Port outline = (Port) AudioSystem.getLine(source);
				outline.open();
				FloatControl volumeControl = (FloatControl) outline
						.getControl(FloatControl.Type.VOLUME);

				 f=(int)volumeControl.getValue();
				
				
			} catch (LineUnavailableException ex) {
				System.err.println("source not supported");
				ex.printStackTrace();
			}
		}
		return f;
		
	}

}