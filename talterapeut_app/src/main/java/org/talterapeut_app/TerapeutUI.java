package org.talterapeut_app;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickListener;
import fi.jasoft.dragdroplayouts.DDCssLayout;
import fi.jasoft.dragdroplayouts.client.ui.LayoutDragMode;
import fi.jasoft.dragdroplayouts.drophandlers.DefaultCssLayoutDropHandler;
import org.talterapeut_app.model.ImageLoader;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

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

    final private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
    private int phrase_length = 3;	// the current phrase length (or no. of words)
    private ArrayList correct_order = new ArrayList(); // stores the correct order of the current phrase
    private ArrayList<Image> setOfImages = new ArrayList<Image>();

    private final GridLayout gridLayout = new GridLayout(3, 3);

    private VerticalLayout wordSelectLayout = new VerticalLayout();
    private Button folderButton;
    private Button randomButton;
    private Button resetButton;

    private VerticalLayout dragDropALayout;
    private Label phraseLabel;
    private DDCssLayout dragDropArea_A;
    private DDCssLayout dragDropArea_B;
    private Button playPhraseButton;


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        initWordLengthLayout();
        initWordFolder();
        initRandomButton();
        initDragDropLayouts();
        initSoundButton();
        initResetButton();

        randomize();

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
        ClickListener setWordCount = (ClickListener) event -> {
            // fetch button's corresponding number via its description to change
            // the word count (phrase_length) + refresh other components
            Button b = event.getButton();
            phrase_length = Integer.parseInt(b.getDescription());

            randomize();
        };

        Button twoWordButton = new Button("Two Words");
        Button threeWordButton = new Button("Three Words");

        // these descriptions are used to set the phrase length
        twoWordButton.setDescription("2");
        threeWordButton.setDescription("3");

        // all buttons use the same listener
        twoWordButton.addClickListener(setWordCount);
        threeWordButton.addClickListener(setWordCount);

        twoWordButton.setWidth("100%");
        threeWordButton.setWidth("100%");

        wordSelectLayout.addComponent(twoWordButton);
        wordSelectLayout.addComponent(threeWordButton);
    }


    // the word folder containing all words/images
    private void initWordFolder() {
        folderButton = new Button("Word Folder");
        folderButton.addClickListener( e -> promptImageSelector());
    }

    // button used to randomize
    private void initRandomButton() {
        randomButton = new Button("Randomize");
        randomButton.addClickListener( e -> randomize());
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

                // test if the answer was correct or not
                if (Objects.equals(tmp,"Subject Verb Object") || Objects.equals(tmp, "Subject Verb")) {
                    new Notification("Correct!").show(Page.getCurrent());
                }
                else {
                    new Notification("Incorrect").show(Page.getCurrent());
                }

            }
            else
                phraseLabel.setValue("This DnD layout is empty!");
        });
    }

    // button which calls the reset method
    private void initResetButton() {
        resetButton = new Button("Reset");
        resetButton.addClickListener( e -> reset());
    }

    // resets the DnD layouts and the phrase label
    private void reset() {
        phraseLabel.setValue("");
        dragDropArea_A.removeAllComponents();
        dragDropArea_B.removeAllComponents();

        for (int i = 0; i < phrase_length; i++) {
            Image image = setOfImages.get(i);
            image.setWidth("100px");
            image.setHeight("100px");
            dragDropArea_B.addComponent(image);
        }
    }

    // prompt for the selection of images.
    // TO DO: Split into modules
    private void promptImageSelector() {

        Window window = new Window("Image Selection");
        window.setModal(true);
        window.setWidth(300.0f, Unit.PIXELS);
        window.setResizable(false);
        window.center();

        VerticalLayout folderWindowLayout = new VerticalLayout();
        folderWindowLayout.setMargin(true);

        window.setContent(folderWindowLayout);

        Button subjectButton = new Button("Subject");
        subjectButton.setWidth("100%");
        folderWindowLayout.addComponent(subjectButton);

        Panel subjPanel = new Panel();
        subjPanel.setHeight(130.0f, Unit.PIXELS);
        subjPanel.setVisible(false);

        VerticalLayout subjWindowLayout = new VerticalLayout();
        subjPanel.setContent(subjWindowLayout);
        folderWindowLayout.addComponent(subjPanel);

        // WORK IN PROGRESS
        // Populate VerticalLayout within Panel (with scrollbar)
        // Clear layout and disable Panel on re-click
        subjectButton.addClickListener( e -> {

            if (!subjPanel.isVisible()) {
                ArrayList<Image> subjectArrayList = ImageLoader.loadImages(basepath + "/WEB-INF/subjekt/");
                subjPanel.setVisible(true);

                for (Image img : subjectArrayList) {
                    subjWindowLayout.addComponent(img);
                    subjWindowLayout.setComponentAlignment(img, Alignment.MIDDLE_CENTER);
                }
            } else {
                subjWindowLayout.removeAllComponents();
                subjPanel.setVisible(false);
            }
        });

        Button verbButton = new Button("Verb");
        verbButton.setWidth("100%");
        folderWindowLayout.addComponent(verbButton);


        getUI().getCurrent().addWindow(window);

    }


    // Loads new set of images
    private void randomize() {
        phraseLabel.setValue("");

        int randomIndex;
        setOfImages.clear();

        // Subject
        ArrayList<Image> Subjects = ImageLoader.loadImages(basepath+"/WEB-INF/subjekt");
        randomIndex = ThreadLocalRandom.current().nextInt(0, Subjects.size());
        Image imageOfSubject = Subjects.get(randomIndex);
        imageOfSubject.setDescription("Subject");
        setOfImages.add(imageOfSubject);

        // Verb
        ArrayList<Image> Verbs= ImageLoader.loadImages(basepath + "/WEB-INF/verb/");
        randomIndex = ThreadLocalRandom.current().nextInt(0, Verbs.size());
        Image imageOfVerb = Verbs.get(randomIndex);
        imageOfVerb.setDescription("Verb");
        setOfImages.add(imageOfVerb);

        // Object
        if (phrase_length == 3) {
            ArrayList<Image> Objects = ImageLoader.loadImages(basepath + "/WEB-INF/objekt/");
            randomIndex = ThreadLocalRandom.current().nextInt(0, Objects.size());
            Image imageOfObject = Objects.get(randomIndex);
            imageOfObject.setDescription("Object");
            setOfImages.add(imageOfObject);
        }

        Collections.shuffle(setOfImages);
        reset();
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = TerapeutUI.class, productionMode = false)
    public static class TerapeutUIServlet extends VaadinServlet {
    }
}
