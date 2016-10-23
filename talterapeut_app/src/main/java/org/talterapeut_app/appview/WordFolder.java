package org.talterapeut_app.appview;

import java.util.ArrayList;

import com.vaadin.ui.*;
import org.talterapeut_app.AppView;
import org.talterapeut_app.model.ImageLoader;

import java.util.Iterator;


public class WordFolder extends Button implements Button.ClickListener {

    private VerticalLayout imageCol1, imageCol2, imageCol3;
    private HorizontalLayout columnHolder;
    private Image phraseSubjImage, phraseVerbImage, phraseObjImage;
    private Button subjButton, verbButton, objButton;
    private Button btn1, btn2, btn3;
    private Panel imagePanel;
    private ArrayList<Image> selectedPhrase;

    private Category selectedCategory;

    // Enum for checking Word Category. Should be done with a Word Object
    private enum Category {
        SUBJECT,
        VERB,
        OBJECT
    }

    public WordFolder() {

        selectedPhrase = new ArrayList<>();
        setCaption("Word Folder");
        addClickListener(e -> {
            promptImageSelector();
        });
    }

    private void promptImageSelector() {

        Window window = new Window("Image Selection");
        window.setModal(true);
        window.setWidth(600.0f, Unit.PIXELS);
        window.setHeight(660.0f, Unit.PIXELS);
        window.setResizable(false);
        window.center();

        // VERTICAL HOLDS ALL CONTENT (Tried grid without visual success)
        VerticalLayout contentLayout = new VerticalLayout();
        window.setContent(contentLayout);

        //HORIZONTAL LAYOUT CONTAINS BUTTONS + PANEL
        HorizontalLayout midContent = new HorizontalLayout();
        contentLayout.addComponent(midContent);

        // BUTTONS FOR SUBJ/VERB/OBJ
        // ADD TO VERTICAL LAYOUT FOR NOW
        VerticalLayout buttonHolder = new VerticalLayout();
        midContent.addComponent(buttonHolder);

        subjButton = new Button("Subject");
        subjButton.setWidth("100px");
        subjButton.addClickListener(this);
        verbButton = new Button("Verb");
        verbButton.setWidth("100px");
        verbButton.addClickListener(this);

        buttonHolder.addComponent(subjButton);
        buttonHolder.addComponent(verbButton);

        buttonHolder.setComponentAlignment(subjButton, Alignment.MIDDLE_LEFT);
        buttonHolder.setComponentAlignment(verbButton, Alignment.MIDDLE_LEFT);

        // SEE IF PHRASE LENGTH ALLOWS THE USE OF OBJECT
        if (AppView.getPhraseLength() == 3) {
            objButton = new Button("Object");
            objButton.setWidth("100px");
            objButton.addClickListener(this);
            buttonHolder.addComponent(objButton);
            buttonHolder.setComponentAlignment(objButton, Alignment.MIDDLE_LEFT);
        }


        // VERTICAL COLUMNS FOR IMAGES
        // PANEL HOLDS HORIZONTAL LAYOUT THAT CONTAINS 3 VERTICALS
        imageCol1 = new VerticalLayout();
        imageCol2 = new VerticalLayout();
        imageCol3 = new VerticalLayout();
        columnHolder = new HorizontalLayout();
        columnHolder.addComponent(imageCol1);
        columnHolder.addComponent(imageCol2);
        columnHolder.addComponent(imageCol3);
        imagePanel = new Panel();
        imagePanel.setWidth(500.0f, Unit.PIXELS);
        imagePanel.setHeight(400.0f, Unit.PIXELS);
        imagePanel.setContent(columnHolder);
        midContent.addComponent(imagePanel);


        // SELECTED PHRASE TO GENERATE
        HorizontalLayout phraseHolderLayout = new HorizontalLayout();
        contentLayout.addComponent(phraseHolderLayout);
        contentLayout.setComponentAlignment(phraseHolderLayout, Alignment.BOTTOM_CENTER);

        phraseSubjImage = new Image();
        phraseSubjImage.setDescription("Subject");
        phraseSubjImage.setWidth(160.0f, Unit.PIXELS);
        phraseSubjImage.setHeight(160.0f, Unit.PIXELS);
        phraseHolderLayout.addComponent(phraseSubjImage);

        phraseVerbImage = new Image();
        phraseSubjImage.setDescription("Verb");

        phraseVerbImage.setWidth(160.0f, Unit.PIXELS);
        phraseVerbImage.setHeight(160.0f, Unit.PIXELS);
        phraseHolderLayout.addComponent(phraseVerbImage);

        phraseObjImage = new Image();
        phraseSubjImage.setDescription("Object");
        phraseObjImage.setWidth(160.0f, Unit.PIXELS);
        phraseObjImage.setHeight(160.0f, Unit.PIXELS);
        phraseHolderLayout.addComponent(phraseObjImage);
        phraseObjImage.setVisible(false);


        if (AppView.getPhraseLength() == 3) {
            phraseObjImage.setVisible(true);
        }

        Button generatePhraseButton = new Button("Use this Phrase");
        generatePhraseButton.setWidth(200.0f, Unit.PIXELS);
        contentLayout.addComponent(generatePhraseButton);
        contentLayout.setComponentAlignment(generatePhraseButton, Alignment.BOTTOM_CENTER);
        generatePhraseButton.addClickListener(e -> {
            Notification.show("THIS IS THE GENERATED PHRASE");

            selectedPhrase.add(phraseSubjImage);
            selectedPhrase.add(phraseVerbImage);

            if (AppView.getPhraseLength() == 3) {
                selectedPhrase.add(phraseObjImage);
            }

            AppView.setPhrase(selectedPhrase);
            window.close();

        });


        getUI().getCurrent().addWindow(window);
    }


    private void resetImagePanel() {

        imageCol1.removeAllComponents();
        imageCol2.removeAllComponents();
        imageCol3.removeAllComponents();
    }

    private void populateImagePanel(ArrayList<Image> al) {

        Iterator<Image> iter = al.iterator();
        System.out.println("Iterator size: " + al.size());

        while (iter.hasNext()) {

            if (iter.hasNext()) {

                Image img1 = iter.next();
                btn1 = new Button();
                btn1.setIcon(img1.getSource());

                // CHEESY ONCLICKLISTENER
                btn1.addClickListener(e1 -> {

                    switch (selectedCategory) {

                        case SUBJECT:
                            phraseSubjImage.setSource(img1.getSource());
                            break;

                        case VERB:
                            phraseVerbImage.setSource(img1.getSource());
                            break;

                        case OBJECT:
                            phraseObjImage.setSource(img1.getSource());
                            break;
                    }


                });

                btn1.addStyleName("borderless");
                btn1.setWidth(160.0f, Unit.PIXELS);
                btn1.setHeight(160.0f, Unit.PIXELS);
                imageCol1.addComponent(btn1);
                imageCol1.setComponentAlignment(btn1, Alignment.MIDDLE_CENTER);
            }

            if (iter.hasNext()) {

                Image img2 = iter.next();
                btn2 = new Button(img2.getSource());

                // CHEESY ONCLICKLISTENER
                btn2.addClickListener(e2 -> {

                    switch (selectedCategory) {

                        case SUBJECT:
                            phraseSubjImage.setSource(img2.getSource());
                            break;

                        case VERB:
                            phraseVerbImage.setSource(img2.getSource());
                            break;

                        case OBJECT:
                            phraseObjImage.setSource(img2.getSource());
                            break;
                    }


                });

                btn2.addStyleName("borderless");
                btn2.setWidth(160.0f, Unit.PIXELS);
                btn2.setHeight(160.0f, Unit.PIXELS);
                imageCol2.addComponent(btn2);
                imageCol2.setComponentAlignment(btn2, Alignment.MIDDLE_CENTER);
            }

            if (iter.hasNext()) {

                Image img3 = iter.next();
                btn3 = new Button(img3.getSource());

                // CHEESY ONCLICKLISTENER
                btn3.addClickListener(e3 -> {

                    switch (selectedCategory) {

                        case SUBJECT:
                            phraseSubjImage.setSource(img3.getSource());
                            break;

                        case VERB:
                            phraseVerbImage.setSource(img3.getSource());
                            break;

                        case OBJECT:
                            phraseObjImage.setSource(img3.getSource());
                            break;
                    }


                });

                btn3.addStyleName("borderless");
                btn3.setWidth(160.0f, Unit.PIXELS);
                btn3.setHeight(160.0f, Unit.PIXELS);
                imageCol3.addComponent(btn3);
                imageCol3.setComponentAlignment(btn3, Alignment.MIDDLE_CENTER);
            }
        }
    }

    @Override
    public void buttonClick(ClickEvent clickEvent) {

        if (clickEvent.getButton() == subjButton) {

            selectedCategory = Category.SUBJECT;
            resetImagePanel();
            ArrayList<Image> subjArrayList = ImageLoader
                    .loadImages(AppView.getBasepath()
                            + "/WEB-INF/subjekt/");

            populateImagePanel(subjArrayList);

        } else if (clickEvent.getButton() == verbButton) {

            selectedCategory = Category.VERB;
            resetImagePanel();
            ArrayList<Image> verbArrayList = ImageLoader
                    .loadImages(AppView.getBasepath()
                            + "/WEB-INF/verb/");

            populateImagePanel(verbArrayList);

        } else if (clickEvent.getButton() == objButton) {

            selectedCategory = Category.OBJECT;
            resetImagePanel();
            ArrayList<Image> objArrayList = ImageLoader
                    .loadImages(AppView.getBasepath()
                            + "/WEB-INF/objekt/");

            populateImagePanel(objArrayList);

        }
    }
}