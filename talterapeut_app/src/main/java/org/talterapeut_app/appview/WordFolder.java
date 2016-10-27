package org.talterapeut_app.appview;

import java.util.ArrayList;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.talterapeut_app.AppView;
import org.talterapeut_app.model.ImageLoader;

import java.util.Iterator;


/*
TODO: CONSISTANCY // SPLIT CODE
TODO: CHANGE FROM WINDOW TO POPUP FOR BLURLISTENER (Close on click outside MODAL)
 */

public class WordFolder extends Button implements Button.ClickListener {

    private Window window;
    private VerticalLayout imageCol1, imageCol2, imageCol3;
    private HorizontalLayout columnHolder;
    private Image phraseSubjImage, phraseVerbImage, phraseObjImage;
    private Button subjButton, verbButton, objButton;
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

        initWindow();
        selectedPhrase.clear();

        // VERTICAL HOLDS ALL CONTENT (Tried grid without visual success)
        VerticalLayout contentLayout = new VerticalLayout();
        window.setContent(contentLayout);

        //HORIZONTAL LAYOUT CONTAINS BUTTONS + PANEL
        HorizontalLayout midContent = new HorizontalLayout();
        midContent.addStyleName("midContent");
        contentLayout.addComponent(midContent);

        // BUTTONS FOR SUBJ/VERB/OBJ
        // ADD TO VERTICAL LAYOUT FOR NOW
        VerticalLayout buttonHolder = new VerticalLayout();
        buttonHolder.addStyleName("categorylayout");
        midContent.addComponent(buttonHolder);

        subjButton = new Button("Subject");
        subjButton.addStyleName("categorybutton");
        subjButton.addClickListener(this);
        verbButton = new Button("Verb");
        verbButton.addStyleName("categorybutton");
        verbButton.addClickListener(this);

        buttonHolder.addComponent(subjButton);
        buttonHolder.addComponent(verbButton);

        buttonHolder.setComponentAlignment(subjButton, Alignment.MIDDLE_LEFT);
        buttonHolder.setComponentAlignment(verbButton, Alignment.MIDDLE_LEFT);

        // SEE IF PHRASE LENGTH ALLOWS THE USE OF OBJECT
        if (AppView.getPhraseLength() == 3) {
            objButton = new Button("Object");
            objButton.addStyleName("categorybutton");
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
        columnHolder.addStyleName("imagecolumnlayout");
        columnHolder.addComponent(imageCol1);
        columnHolder.addComponent(imageCol2);
        columnHolder.addComponent(imageCol3);
        imagePanel = new Panel();
        imagePanel.setWidth(450.0f, Unit.PIXELS);
        imagePanel.setHeight(400.0f, Unit.PIXELS);
        imagePanel.setContent(columnHolder);
        midContent.addComponent(imagePanel);


        // SELECTED PHRASE TO GENERATE
        HorizontalLayout phraseHolderLayout = new HorizontalLayout();
        phraseHolderLayout.addStyleName("selectorphraselayout");
        phraseHolderLayout.setMargin(true);
        contentLayout.addComponent(phraseHolderLayout);
        contentLayout.setComponentAlignment(phraseHolderLayout, Alignment.BOTTOM_CENTER);


        // TODO: USE FONTICONS AS PLACEHOLDERS
        phraseSubjImage = new Image();
        phraseSubjImage.setDescription("Subject");
        phraseSubjImage.addStyleName("selectorphraseimage");
        phraseSubjImage.setWidth(140.0f, Unit.PIXELS);
        phraseSubjImage.setHeight(140.0f, Unit.PIXELS);
        phraseHolderLayout.addComponent(phraseSubjImage);

        phraseVerbImage = new Image();
        phraseVerbImage.setDescription("Verb");
        phraseVerbImage.addStyleName("selectorphraseimage");
        phraseVerbImage.setWidth(140.0f, Unit.PIXELS);
        phraseVerbImage.setHeight(140.0f, Unit.PIXELS);
        phraseHolderLayout.addComponent(phraseVerbImage);

        phraseObjImage = new Image();
        phraseObjImage.setDescription("Object");
        phraseObjImage.addStyleName("selectorphraseimage");
        phraseObjImage.setWidth(140.0f, Unit.PIXELS);
        phraseObjImage.setHeight(140.0f, Unit.PIXELS);

        if (AppView.getPhraseLength() == 2) {
            phraseObjImage.setVisible(false);
        }

        phraseHolderLayout.addComponent(phraseObjImage);

        Button generatePhraseButton = new Button("Generate Phrase");
        generatePhraseButton.addStyleName("generatephrasebutton");
        generatePhraseButton.setWidth(200.0f, Unit.PIXELS);
        contentLayout.addComponent(generatePhraseButton);
        contentLayout.setComponentAlignment(generatePhraseButton, Alignment.BOTTOM_CENTER);
        generatePhraseButton.addClickListener(e -> {

            // IF LENGTH IS > 2 AND ALL IMAGES ARE SELECTED
            if (AppView.getPhraseLength() > 2 && phraseSubjImage.getSource() != null &&
                    phraseVerbImage.getSource() != null && phraseObjImage.getSource() != null) {
                selectedPhrase.add(phraseSubjImage);
                selectedPhrase.add(phraseVerbImage);
                selectedPhrase.add(phraseObjImage);

                Notification.show("The phrase was generated");
                AppView.setPhrase(selectedPhrase);
                window.close();

                // IF LENGTH IS 2 AND BOTH IMAGES ARE SELECTED
            } else if (AppView.getPhraseLength() == 2 && phraseSubjImage.getSource() != null &&
                    phraseVerbImage.getSource() != null) {
                selectedPhrase.add(phraseSubjImage);
                selectedPhrase.add(phraseVerbImage);

                Notification.show("The phrase was generated");
                AppView.setPhrase(selectedPhrase);

                window.close();

            } else {
                Notification.show("Select image for each Category", Notification.Type.WARNING_MESSAGE);
            }
        });
    }

    private void resetImagePanel() {

        imageCol1.removeAllComponents();
        imageCol2.removeAllComponents();
        imageCol3.removeAllComponents();
    }

    private void populateImagePanel(ArrayList<Image> al) {

        Iterator<Image> iter = al.iterator();

        // Copied code in case of usage of subcategories for each column
        while (iter.hasNext()) {

            if (iter.hasNext()) {

                Image img1 = iter.next();
                img1.addStyleName("categoryimage");
                img1.setWidth(140.0f, Unit.PIXELS);
                img1.setHeight(140.0f, Unit.PIXELS);

                // CHEESY ONCLICKLISTENER
                img1.addClickListener(e1 -> {

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

                imageCol1.addComponent(img1);
                imageCol1.setComponentAlignment(img1, Alignment.MIDDLE_CENTER);
            }

            if (iter.hasNext()) {

                Image img2 = iter.next();
                img2.addStyleName("categoryimage");
                img2.setWidth(140.0f, Unit.PIXELS);
                img2.setHeight(140.0f, Unit.PIXELS);

                // CHEESY ONCLICKLISTENER
                img2.addClickListener(e2 -> {

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

                imageCol2.addComponent(img2);
                imageCol2.setComponentAlignment(img2, Alignment.MIDDLE_CENTER);
            }

            if (iter.hasNext()) {

                Image img3 = iter.next();
                img3.addStyleName("categoryimage");
                img3.setWidth(140.0f, Unit.PIXELS);
                img3.setHeight(140.0f, Unit.PIXELS);

                // CHEESY ONCLICKLISTENER
                img3.addClickListener(e3 -> {

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

                imageCol3.addComponent(img3);
                imageCol3.setComponentAlignment(img3, Alignment.MIDDLE_CENTER);
            }
        }
    }

    private void initWindow() {

        window = new Window("Image Selection");
        window.addStyleName("categorywindow");
        window.setModal(true);
        window.setWidth(600.0f, Unit.PIXELS);
        window.setHeight(720.0f, Unit.PIXELS);
        window.setResizable(false);
        window.center();

        // ADD WINDOW TO UI
        getUI().getCurrent().addWindow(window);
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