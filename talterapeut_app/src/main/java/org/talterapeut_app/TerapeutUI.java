package org.talterapeut_app;

import java.io.File;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FileResource;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Audio;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import fi.jasoft.dragdroplayouts.DDHorizontalLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
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

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        
        final GridLayout gridLayout = new GridLayout(3, 3);
        gridLayout.setSizeFull();
        
        
        // layout consisting of the phrase length settings
        VerticalLayout wordSelectLayout = new VerticalLayout();
        Button twoWordButton = new Button("Two Words");
        Button threeWordButton = new Button("Three Words");
        Button fourWordButton = new Button("Four Words");
        twoWordButton.setWidth("125%");
        threeWordButton.setWidth("125%");
        fourWordButton.setWidth("125%");
        wordSelectLayout.addComponent(twoWordButton);
        wordSelectLayout.addComponent(threeWordButton);
        wordSelectLayout.addComponent(fourWordButton);
        gridLayout.addComponent(wordSelectLayout);
        
        // temporary buttons for the folder and randomizer
        Button folderButton = new Button("Word Folder");
        gridLayout.addComponent(folderButton);
        gridLayout.setComponentAlignment(folderButton, Alignment.TOP_CENTER);
        gridLayout.addComponent(new Button("Randomize"));
        
        gridLayout.addComponent(new Label(" "));
        
       
        // The first DnD layout (located in the middle)
        DDHorizontalLayout DragDropALayout = new DDHorizontalLayout();
        DragDropALayout.setComponentHorizontalDropRatio(0.3f);
        DragDropALayout.setDragMode(LayoutDragMode.CLONE);
        DragDropALayout.setDropHandler(new DefaultHorizontalLayoutDropHandler());
        
        gridLayout.addComponent(DragDropALayout);
        
        
        // sound button (clicking it should list the button order in the console)
        Button playPhraseButton = new Button("Play Sound");
        gridLayout.addComponent(playPhraseButton);
        playPhraseButton.addClickListener( e -> {
        	int length = DragDropALayout.getComponentCount();
        	if (length > 0) {
        		System.out.print(DragDropALayout.getComponent(0).getCaption());
            	for (int i = 1; i < length; i++)
            		System.out.print(" " + DragDropALayout.getComponent(i).getCaption());
            	System.out.print("\n");
        	}
        });
        
        // second DnD area + layout padding
        gridLayout.addComponent(new Label(" "));
        
        // second DnD area + reset button
        DDHorizontalLayout DragDropBLayout = new DDHorizontalLayout();
        DragDropBLayout.setComponentHorizontalDropRatio(0.3f);
        DragDropBLayout.setDragMode(LayoutDragMode.CLONE);
        DragDropBLayout.setDropHandler(new DefaultHorizontalLayoutDropHandler());

        Button btn1 = new Button("Button 1");
        btn1.setWidth("100px");
        btn1.setHeight("50px");
        DragDropBLayout.addComponent(btn1);
        Button btn2 = new Button("Button 2");
        btn2.setWidth("100px");
        btn2.setHeight("50px");
        DragDropBLayout.addComponent(btn2);
        Button btn3 = new Button("Button 3");
        btn3.setWidth("100px");
        btn3.setHeight("50px");
        DragDropBLayout.addComponent(btn3);
        gridLayout.addComponent(DragDropBLayout);

        gridLayout.addComponent(new Button("Reset"));
        
        
        gridLayout.setMargin(true);
        gridLayout.setSpacing(true);
        setContent(gridLayout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = TerapeutUI.class, productionMode = false)
    public static class TerapeutUIServlet extends VaadinServlet {
    }
}
