package org.talterapeut_app;

import java.io.File;
import org.talterapeut_app.Constant;

import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Audio;

/**
 * Audio
 *
 */
public class SoundMachine  {
	
	private String mediaBasepath; // media files basepath
	private Audio audio;
	
	Resource resource_S;
	Resource resource_F;

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
		System.out.println("Loaded resources.");
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