package org.talterapeut_app;

import javafx.scene.image.Image;

import java.nio.file.Path;

import javax.sound.sampled.Clip;

public class Word {
	private String name;
	private String part_of_speech;
	private Image picture;
	private Clip sound;
	
	public Word (String id, Path path)  {
		this.name = id;
	}
	
	public Word(String n, String pos, Image p, Clip s) {
		this.name = n;
		this.part_of_speech = pos;
		this.picture = p;
		this.sound = s;
	}
}