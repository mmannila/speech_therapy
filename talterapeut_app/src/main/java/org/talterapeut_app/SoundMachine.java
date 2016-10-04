package org.talterapeut_app;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.Clip;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import org.talterapeut_app.Constant;

/**
 * Audio
 *
 */
class SoundMachine implements LineListener {
	
	private String mediaBasepath; // media files basepath
	private AudioInputStream audioStream;
	
	SoundMachine(String mediaBasepath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.mediaBasepath = mediaBasepath;
		LoadResources();
	}
	
	// implement the LineListener interface#update
	@Override
	public void update(LineEvent arg) {
		System.out.println("Sound is coming..."); // TODO get a logger
	}
	
	/**
	 * Sets up audio/media resources 
	 * 
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	public void LoadResources() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		audioStream = AudioSystem.getAudioInputStream(new File(mediaBasepath + Constant.SOUND_SUCCESS_FILENAME));
		System.out.println("Loaded: " + Constant.SOUND_SUCCESS_FILENAME);
	}
	
	/**
	 * Plays success sound
	 * @throws LineUnavailableException 
	 * @throws IOException 
	 */
	public void PlaySuccess() throws LineUnavailableException, IOException {
		
		// TODO use caching to preserve audioStream
		try {
			LoadResources();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Clip...");
		Clip clip;
		Line.Info linfo = new Line.Info(Clip.class);
	    Line line = AudioSystem.getLine(linfo);
	    clip = (Clip) line;
	    clip.addLineListener(this);
		clip.open(audioStream); // audioStream is usually killed after one play!
		clip.start();
		System.out.println("...playing!");
	}
	
	/**
	 * Plays failure sound 
	 */
	public void PlayFail() {
		System.out.println("[SoundMachine.Playfail] I'm afraid I can't do that."); // TODO
	}

	
	/**
	 * Plays sound based on success parameter value
	 *  
	 * @param success
	 */
	public void playSound(Boolean success) {
        if (success.equals(Constant.SUCCESS)) {
        	try {
				PlaySuccess();
			} catch (LineUnavailableException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
        	PlayFail();
        }
	}
	
}