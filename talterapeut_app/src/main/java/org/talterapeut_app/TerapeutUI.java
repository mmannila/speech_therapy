package org.talterapeut_app;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import fi.jasoft.dragdroplayouts.DDCssLayout;
import fi.jasoft.dragdroplayouts.DDHorizontalLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultCssLayoutDropHandler;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultHorizontalLayoutDropHandler;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
//@SuppressWarnings("serial")
public class TerapeutUI extends UI {
	
	int phrase_length = 3;	// the current phrase length (or no. of words)
	ArrayList correct_order = new ArrayList(); // stores the correct order of the current phrase
	
    final GridLayout gridLayout = new GridLayout(3, 3);
	
    VerticalLayout wordSelectLayout = new VerticalLayout();
    Button folderButton;
    Button randomButton;
    Button resetButton;
    
    VerticalLayout dragDropALayout;
    Label phraseLabel;
    DDCssLayout dragDropArea_A;
    DDCssLayout dragDropArea_B;
    Button playPhraseButton;
    
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	initWordLengthLayout();
    	initWordFolder();
    	initRandomButton();
    	initDragDropLayouts();
    	initSoundButton();
    	initResetButton();
    	
    	reset();

        setupUI();
        
    }
    
    // adds all components to the main layout
    private void setupUI() {
    	// inserts components in grid layout
        gridLayout.addComponent(wordSelectLayout, 0, 0);
        gridLayout.addComponent(folderButton, 1, 0);
        gridLayout.addComponent(randomButton, 2, 0);
        gridLayout.addComponent(dragDropALayout, 1, 1);
        gridLayout.addComponent(playPhraseButton, 2, 1);
        gridLayout.addComponent(dragDropArea_B, 1, 2);
        gridLayout.addComponent(resetButton, 2, 2);
        
        gridLayout.setComponentAlignment(folderButton, Alignment.TOP_CENTER);
        
        gridLayout.setSizeFull();
        gridLayout.setMargin(true);
        gridLayout.setSpacing(true);
        setContent(gridLayout);
    }
 
    // initializes the buttons used to set phrase length
    private void initWordLengthLayout() {
    	wordSelectLayout = new VerticalLayout();
    	
    	// listener which changes the phrase length + the other components
    	ClickListener setWordCount = new ClickListener() {
    	    @Override
    	    public void buttonClick(ClickEvent event) {
    	        // fetch button's corresponding number via its description to change
    	    	// the word count (phrase_length) + refresh other components
    	    }
    	  };
    		
        Button twoWordButton = new Button("Two Words");
        Button threeWordButton = new Button("Three Words");
        Button fourWordButton = new Button("Four Words");
        
        // these descriptions are used to set the phrase length
        twoWordButton.setDescription("2");
        twoWordButton.setDescription("3");
        twoWordButton.setDescription("4");
        
        // all buttons use the same listener
        twoWordButton.addClickListener(setWordCount);
        threeWordButton.addClickListener(setWordCount);
        fourWordButton.addClickListener(setWordCount);
        
        twoWordButton.setWidth("100%");
        threeWordButton.setWidth("100%");
        fourWordButton.setWidth("100%");
        
        wordSelectLayout.addComponent(twoWordButton);
        wordSelectLayout.addComponent(threeWordButton);
        wordSelectLayout.addComponent(fourWordButton);
    }
    
    
    // the word folder containing all words/images
    private void initWordFolder() {
    	folderButton = new Button("Word Folder");
    	folderButton.addClickListener( e -> {
        	
		});
    }
    
    // button used to randomize
    private void initRandomButton() {
    	randomButton = new Button("Randomize");
    	randomButton.addClickListener( e -> {
    	
    		});
    }
    
    // initializes the drag and drop layouts
    private void initDragDropLayouts() {
    	dragDropALayout = new VerticalLayout();    	
    	phraseLabel = new Label();
    	
    	dragDropArea_A = new DDCssLayout();
    	dragDropArea_A.setSizeFull();
        dragDropArea_A.setDragMode(LayoutDragMode.CLONE);
        dragDropArea_A.setDropHandler(new DefaultCssLayoutDropHandler());
    	
        dragDropArea_B = new DDCssLayout();
        dragDropArea_B.setSizeFull();
        dragDropArea_B.setDragMode(LayoutDragMode.CLONE);
        dragDropArea_B.setDropHandler(new DefaultCssLayoutDropHandler());
        
        dragDropALayout.addComponent(dragDropArea_A);
        dragDropALayout.addComponent(phraseLabel);   
        dragDropALayout.setSizeFull();
    }

    // plays the phrase in DnD layout A
    private void initSoundButton() {
    	playPhraseButton = new Button("Play Sound");
    	playPhraseButton.addClickListener( e -> {
        	int length = dragDropArea_A.getComponentCount();
        	if (length > 0) {
        		String tmp = dragDropArea_A.getComponent(0).getDescription();
            	for (int i = 1; i < length; i++)
            		tmp += " " + dragDropArea_A.getComponent(i).getDescription();
            	phraseLabel.setValue(tmp);
        	}
        	else
        		phraseLabel.setValue("This DnD layout is empty!");
        });
    }
    
    // button which calls the reset method
    private void initResetButton() {
    	resetButton = new Button("Reset");
    	resetButton.addClickListener( e -> {
    			reset();
    		});
    }
    
    // resets the DnD layouts and the phrase label
    private void reset() {
    	phraseLabel.setValue("");
    	dragDropArea_A.removeAllComponents();
    	dragDropArea_B.removeAllComponents();
    	
    	
    	Image image1 = new Image();
        image1.setSource(new ExternalResource("http://www.utu.fi/fi/SiteCollectionImages/kirja45px.png"));
        image1.setWidth("75px");
        image1.setHeight("75px");
        image1.setDescription("book");
        dragDropArea_B.addComponent(image1);
        Image image2 = new Image();
        image2.setSource(new ExternalResource("http://www.utu.fi/fi/SiteCollectionImages/group-45px.png"));
        image2.setWidth("75px");
        image2.setHeight("75px");
        image2.setDescription("group");
        dragDropArea_B.addComponent(image2);
        Image image3 = new Image();
        image3.setSource(new ExternalResource("http://www.utu.fi/fi/SiteCollectionImages/kartta45px.png"));
        image3.setWidth("75px");
        image3.setHeight("75px");
        image3.setDescription("map");
        dragDropArea_B.addComponent(image3);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = TerapeutUI.class, productionMode = false)
    public static class TerapeutUIServlet extends VaadinServlet {
    }
}
