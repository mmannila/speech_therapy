package org.talterapeut_app;

import java.io.File;
import java.nio.file.Path;

import org.talterapeut_app.Constant;

import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Audio;
import com.vaadin.ui.Component.Listener;

/**
 * Audio play and resource load.
 *
 * Is the mimetype A problem if the audio does not play in all devices
 * This could help maybe: use StreamResource (not FileResource) and:
 	@Override
	public String getMIMEType() {
    	return "audio/mp3";
	}
 */
public class SoundMachine  {
	
	protected String mediaBasepath; // media files basepath
	protected Audio audio;
	
	protected Resource resource_S;
	protected Resource resource_F;

	protected Resource resource1;
	protected Resource resource2;
	protected Resource resource3;

	SoundMachine(Audio audio, String mediaBasepath) {
		this.audio = audio;
		this.mediaBasepath = mediaBasepath;
		loadResources();
		
	}
	
	/**
	 * Sets up audio/media resources 
	 */
	public void loadResources() {
		resource_S = new FileResource(new File(mediaBasepath + Constant.SOUND_SUCCESS_FILENAME));
		resource_F = new FileResource(new File(mediaBasepath + Constant.SOUND_FAIL_FILENAME));
		System.out.println("Loaded F and S resources.");
	}
	
	public void loadWordSounds(String word1, String word2, String word3) {
		resource1 = new FileResource(new File(word1));
		resource2 = new FileResource(new File(word2));
		resource3 = new FileResource(new File(word3));
		System.out.println("Loaded word sound resources.");
	}
	
	public void playWordSounds() {
		/* TODO play some kind of proper sequence - audio.play does not block and wait until the audio has played
		 * Event Listener Wait/Notify could help?
		 */
		System.out.println("Playing word sounds... MIMEType of first audio file:" + resource1.getMIMEType());
        audio.setSource(resource1);
    	audio.play();
        audio.setSource(resource2);
    	audio.play();
    	audio.setSource(resource3);
    	audio.play();
	}
	
	/**
	 * Plays success sound
	 */
	public void playSuccess() {
		System.out.println("Playing 'success'... " + resource_S.getMIMEType());
        audio.setSource(resource_S);
    	audio.play();
	}
	
	/**
	 * Plays failure sound 
	 */
	public void playFail() {
		System.out.println("Playing 'fail'... " + resource_F.getMIMEType());
        audio.setSource(resource_F);
    	audio.play();
	}

	
	/**
	 * Plays sound based on success parameter value
	 *  
	 * @param success
	 */
	public void playSound(Boolean success) {
        if (success.equals(Constant.SUCCESS)) {
        	playSuccess();
        } else {
        	playFail();
        }
	}
	
}