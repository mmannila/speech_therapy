package org.talterapeut_app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

import org.talterapeut_app.appview.DragDropLayoutBottom;
import org.talterapeut_app.appview.DragDropLayoutTop;
import org.talterapeut_app.appview.RandomButton;
import org.talterapeut_app.appview.ResetButton;
import org.talterapeut_app.appview.SoundButton;
import org.talterapeut_app.appview.WordFolder;
import org.talterapeut_app.appview.WordLengthLayout;
import org.talterapeut_app.model.ImageLoader;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Audio;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class AppView extends VerticalLayout implements View {
    final private static String basepath = VaadinService.getCurrent()
            .getBaseDirectory().getAbsolutePath();
    static int phrase_length = 3; // the current phrase length (or no. of words)
    static ArrayList<Image> setOfImages = new ArrayList<Image>();

    final GridLayout gridLayout = new GridLayout(3, 3);

    private WordLengthLayout wordSelectLayout;
    // private static DragDropLayouts dragDropLayouts;
    private static DragDropLayoutTop dragDropLayoutsTop;
    private static DragDropLayoutBottom dragDropLayoutsBottom;

    private WordFolder folderButton;
    private RandomButton randomButton;
    private ResetButton resetButton;
    private SoundButton playPhraseButton;
    private Audio audio;
    public static SoundMachine soundMachine;
    
    public ArrayList<Word> words = new ArrayList<>(); // only used for audio, for now // TODO make private

    public AppView() {
        setSizeFull();

        initSoundMachine();

        wordSelectLayout = new WordLengthLayout();
        folderButton = new WordFolder();
        // dragDropLayouts = new DragDropLayouts();
        dragDropLayoutsTop = new DragDropLayoutTop();
        dragDropLayoutsBottom = new DragDropLayoutBottom();
        randomButton = new RandomButton();
        playPhraseButton = new SoundButton();
        resetButton = new ResetButton();

        // inserts components in grid layout
        gridLayout.addComponent(wordSelectLayout, 0, 0);
        gridLayout.addComponent(folderButton, 1, 0);
        gridLayout.addComponent(randomButton, 2, 0);
        // gridLayout.addComponent(dragDropLayouts, 1, 1, 1, 2);
        gridLayout.addComponent(dragDropLayoutsTop, 1, 1);
        gridLayout.addComponent(dragDropLayoutsBottom, 1, 2);
        gridLayout.addComponent(playPhraseButton, 2, 1);
        gridLayout.addComponent(resetButton, 2, 2);
        gridLayout.addComponent(audio); // where to add, actually?

        gridLayout.setComponentAlignment(folderButton, Alignment.TOP_CENTER);

        addComponent(gridLayout);
        gridLayout.setMargin(true);
        gridLayout.setSpacing(true);
        gridLayout.setSizeFull();

        MenuBar menu = TerapeutUI.getMenuBar();
        addComponent(menu);

        // ensures the MenuBar's height is always minimized
        // NOTICE: add these two lines for the other views using the menu bar
        setExpandRatio(gridLayout, 1);
        setExpandRatio(menu, 0);
        
        
    }

    /**
     * Initializes audio. Could throw Exceptions to master initializer to
     * handle, but does not.
     *
     * TODO not accomodated to the nice new code structure (need to play sound
     * NOW)
     */
    private void initSoundMachine() {
        audio = new Audio(); // do not write a title text, it risks the web page
                             // layout
        audio.setAutoplay(false);
        audio.setShowControls(false);
        audio.setHtmlContentAllowed(true); // let's try to set to true to see if it makes any difference
        audio.setStyleName("invisible");
        audio.setAltText("Can't play media");

        soundMachine = new SoundMachine(audio, basepath
                + Constant.AUDIO_BASE_PATH);
    }

    // resets the DnD layouts and the phrase label
    public static void reset() {
        // dragDropLayouts.resetDragDropArea();
        dragDropLayoutsTop.resetDragDropArea();
        dragDropLayoutsBottom.resetDragDropArea();

        Image image = setOfImages.get(0);
        image.setWidth("100px");
        image.setHeight("100px");

        for (int i = 0; i < 3; i++) {
            image = setOfImages.get(i);
            image.setWidth("100px");
            image.setHeight("100px");
            // dragDropLayouts.addPicture(image);
            dragDropLayoutsBottom.addPicture(image);
        }
    }

    // Loads new set of images
    public static void randomize() {
        // dragDropLayouts.setPhraseLabel("");
        dragDropLayoutsTop.setPhraseLabel("");

        int randomIndex;
        setOfImages.clear();

        // Subject
        ArrayList<Image> Subjects = ImageLoader.loadImages(basepath
                + "/WEB-INF/subjekt");
        randomIndex = ThreadLocalRandom.current().nextInt(0, Subjects.size());
        Image imageOfSubject = Subjects.get(randomIndex);
        imageOfSubject.setDescription("Subject");
        setOfImages.add(imageOfSubject);

        // Verb
        ArrayList<Image> Verbs = ImageLoader.loadImages(basepath
                + "/WEB-INF/verb/");
        randomIndex = ThreadLocalRandom.current().nextInt(0, Verbs.size());
        Image imageOfVerb = Verbs.get(randomIndex);
        imageOfVerb.setDescription("Verb");
        setOfImages.add(imageOfVerb);

        // Object
        ArrayList<Image> Objects = ImageLoader.loadImages(basepath
                + "/WEB-INF/objekt/");
        randomIndex = ThreadLocalRandom.current().nextInt(0, Objects.size());
        Image imageOfObject = Objects.get(randomIndex);
        imageOfObject.setDescription("Object");
        setOfImages.add(imageOfObject);

        Collections.shuffle(setOfImages);
        reset();
    }

    public static void setPhrase(ArrayList<Image> al) {

        setOfImages.clear();

        for(int i = 0; i < al.size(); i++) {
            setOfImages.add(al.get(i));
        }

        Collections.shuffle(setOfImages);
        reset();
    }

    public static int getPhraseLength() {
        return phrase_length;
    }

    public static void setPhraseLength(int len) {
        phrase_length = len;
    }

    public static String getBasepath() {
        return basepath;
    }

    public static ArrayList<Component> getDragDropComponents() {
        return dragDropLayoutsTop.getComponents();
        // return dragDropLayouts.getTopComponents();
    }

    public static void setDragDropLabel(String str) {
        dragDropLayoutsTop.setPhraseLabel(str);
        // dragDropLayouts.setPhraseLabel(str);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        if (UI.getCurrent().getSession().getAttribute("email") == null) {
            // Page.getCurrent().setLocation("/");
            TerapeutUI.navigator.navigateTo(TerapeutUI.LOGINVIEW);
        } else {
            randomize();
        }
    }

}