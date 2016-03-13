package caveGame;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;

public class SoundHandler {
	
	private static Clip clip = null;
	private static final String SND = "./snd/";
	
	public void playSound(String soundName) {
		
		String fileName = soundName;
		File soundFile;
		
		try {
			Line.Info linfo = new Line.Info(Clip.class);
			Line line = AudioSystem.getLine(linfo);
			clip = (Clip) line;
			
			soundFile = new File(SND+fileName);	
			//System.out.println("Playing " + soundFile.getName());
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			
			clip.open(ais);
			
			FloatControl volControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			float gain = (volControl.getMaximum()-volControl.getMinimum())*(75.0f/100.0f) + volControl.getMinimum();
			//System.out.println(gain);
			volControl.setValue(gain);
			//System.out.println(volControl.toString());			
			
			clip.start();
			//clip.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
	
	public Clip getClip() {
		return clip;
	}

}
