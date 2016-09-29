package org.talterapeut_app;

import java.io.File;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.*;
import com.vaadin.ui.*;

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
        DDCssLayout DragDropALayout = new DDCssLayout();
        //DragDropALayout.setComponentHorizontalDropRatio(0.3f);
        DragDropALayout.setSizeFull();
        DragDropALayout.setDragMode(LayoutDragMode.CLONE);
        DragDropALayout.setDropHandler(new DefaultCssLayoutDropHandler());
        
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
        DDCssLayout DragDropBLayout = new DDCssLayout();
        //DragDropBLayout.setComponentHorizontalDropRatio(0.3f);
        DragDropBLayout.setSizeFull();
        DragDropBLayout.setDragMode(LayoutDragMode.CLONE);
        DragDropBLayout.setDropHandler(new DefaultCssLayoutDropHandler());

        Image image1 = new Image("book");
        image1.setSource(new ExternalResource("http://www.utu.fi/fi/SiteCollectionImages/kirja45px.png"));
        image1.setWidth("100px");
        image1.setHeight("100px");
        DragDropBLayout.addComponent(image1);
        Image image2 = new Image("group");
        image2.setSource(new ExternalResource("http://www.utu.fi/fi/SiteCollectionImages/group-45px.png"));
        image2.setWidth("100px");
        image2.setHeight("100px");
        DragDropBLayout.addComponent(image2);
        Image image3 = new Image("map");
        image3.setSource(new ExternalResource("http://www.utu.fi/fi/SiteCollectionImages/kartta45px.png"));
        image3.setWidth("100px");
        image3.setHeight("100px");
        DragDropBLayout.addComponent(image3);

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
