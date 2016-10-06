package org.talterapeut_app;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
import org.talterapeut_app.appview.*;
import org.talterapeut_app.model.ImageLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class AppView extends VerticalLayout implements View {
    final private static String basepath = VaadinService.getCurrent()
            .getBaseDirectory().getAbsolutePath();
    static int phrase_length = 3; // the current phrase length (or no. of words)
    ArrayList correct_order = new ArrayList(); // stores the correct order of
                                               // the current phrase
    static ArrayList<Image> setOfImages = new ArrayList<Image>();

    final GridLayout gridLayout = new GridLayout(3, 3);

    private WordLengthLayout wordSelectLayout;
    private static DragDropLayouts dragDropLayouts;

    private WordFolder folderButton;
    private RandomButton randomButton;
    private ResetButton resetButton;
    private SoundButton playPhraseButton;

    public AppView() {
        setSizeFull();

        wordSelectLayout = new WordLengthLayout();
        folderButton = new WordFolder();
        dragDropLayouts = new DragDropLayouts();
        randomButton = new RandomButton();
        playPhraseButton = new SoundButton();
        resetButton = new ResetButton();

        // inserts components in grid layout
        gridLayout.addComponent(wordSelectLayout, 0, 0);
        gridLayout.addComponent(folderButton, 1, 0);
        gridLayout.addComponent(randomButton, 2, 0);
        gridLayout.addComponent(dragDropLayouts, 1, 1, 1, 2);
        gridLayout.addComponent(playPhraseButton, 2, 1);
        gridLayout.addComponent(resetButton, 2, 2);

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

    // // button which calls the reset method
    // private void initRandomButton() {
    // resetButton = new Button("Random");
    // resetButton.addClickListener(e -> {
    // reset();
    // });
    // }
    //
    // // button which calls the reset method
    // private void initResetButton() {
    // resetButton = new Button("Reset");
    // resetButton.addClickListener(e -> {
    // reset();
    // });
    // }

    // resets the DnD layouts and the phrase label
    public static void reset() {
        dragDropLayouts.resetDragDropArea();

        Image image = setOfImages.get(0);
        image.setWidth("100px");
        image.setHeight("100px");

        for (int i = 0; i < phrase_length; i++) {
            image = setOfImages.get(i);
            image.setWidth("100px");
            image.setHeight("100px");
            dragDropLayouts.addPicture(image);
        }
    }

    // Loads new set of images
    public static void randomize() {
        dragDropLayouts.setPhraseLabel("");

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

        // Object (if the three word option is chosen)
        if (phrase_length == 3) {
            ArrayList<Image> Objects = ImageLoader.loadImages(basepath
                    + "/WEB-INF/objekt/");
            randomIndex = ThreadLocalRandom.current().nextInt(0, Objects.size());
            Image imageOfObject = Objects.get(randomIndex);
            imageOfObject.setDescription("Object");
            setOfImages.add(imageOfObject);
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
        return dragDropLayouts.getTopComponents();
    }

    public static void setDragDropLabel(String str) {
        dragDropLayouts.setPhraseLabel(str);
    }

    @Override
    public void enter(ViewChangeEvent event) {
        // TODO Auto-generated method stub
        randomize();

    }

}