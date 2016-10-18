package org.talterapeut_app.appview;

import java.util.ArrayList;

import com.vaadin.ui.*;
import org.talterapeut_app.AppView;
import org.talterapeut_app.model.ImageLoader;
import java.util.Iterator;

public class WordFolder extends Button {

    public WordFolder() {
        setCaption("Word Folder");
        addClickListener(e -> {
            promptImageSelector();
        });
    }

    private void promptImageSelector() {

        Window window = new Window("Image Selection");
        window.setModal(true);
        window.setWidth(800.0f, Unit.PIXELS);
        window.setHeight(600.0f, Unit.PIXELS);
        window.setResizable(false);
        window.center();

        VerticalLayout folderWindowLayout = new VerticalLayout();
        //folderWindowLayout.setMargin(true);

        window.setContent(folderWindowLayout);

        Button subjectButton = new Button("Subject");
        subjectButton.setWidth("100%");
        folderWindowLayout.addComponent(subjectButton);

        Panel subjPanel = new Panel();
        subjPanel.setHeight(390.0f, Unit.PIXELS);
        subjPanel.setVisible(false);
        folderWindowLayout.addComponent(subjPanel);

        VerticalLayout subjWindowLayout1 = new VerticalLayout();
        VerticalLayout subjWindowLayout2 = new VerticalLayout();
        VerticalLayout subjWindowLayout3 = new VerticalLayout();

        // Create HorizontalLayout that holds the 3 columns of images
        HorizontalLayout subLayoutHolder = new HorizontalLayout();
        subLayoutHolder.addComponent(subjWindowLayout1);
        subLayoutHolder.addComponent(subjWindowLayout2);
        subLayoutHolder.addComponent(subjWindowLayout3);

        subjPanel.setContent(subLayoutHolder);


        // WORK IN PROGRESS
        // Populate VerticalLayout within Panel (with scrollbar)
        // Clear layout and disable Panel on re-click
        subjectButton
                .addClickListener(e -> {

                    if (!subjPanel.isVisible()) {
                        ArrayList<Image> subjectArrayList = ImageLoader
                                .loadImages(AppView.getBasepath()
                                        + "/WEB-INF/subjekt/");
                        subjPanel.setVisible(true);

                        Iterator<Image> iter = subjectArrayList.iterator();
                        while(iter.hasNext()) {
                            System.out.println("1: " + iter.hasNext());
                            if (iter.hasNext()) {
                                Image tmp1 = iter.next();
                                subjWindowLayout1.addComponent(tmp1);
                                subjWindowLayout1.setComponentAlignment(tmp1,
                                        Alignment.MIDDLE_CENTER);
                            }
                            System.out.println("2: " + iter.hasNext());
                            if (iter.hasNext()) {
                                Image tmp2 = iter.next();
                                subjWindowLayout2.addComponent(tmp2);
                                subjWindowLayout2.setComponentAlignment(tmp2,
                                        Alignment.MIDDLE_CENTER);
                            }
                            System.out.println("3: " + iter.hasNext());
                            if (iter.hasNext()) {
                                Image tmp3 = iter.next();
                                subjWindowLayout3.addComponent(tmp3);
                                subjWindowLayout3.setComponentAlignment(tmp3,
                                        Alignment.MIDDLE_CENTER);
                            }
                        }

                        /*
                        // ITERATE OVER ALL ELEMENTS AND DISTRIBUTE THEM TO 3 LAYOUTS
                        for (int i = 0; i < subjectArrayList.size(); i+=3) {

                            Image tmp1 = subjectArrayList.get(i);
                            subjWindowLayout1.addComponent(tmp1);
                            subjWindowLayout1.setComponentAlignment(tmp1,
                                    Alignment.MIDDLE_CENTER);

                            Image tmp2 = subjectArrayList.get(i+1);
                            subjWindowLayout2.addComponent(tmp2);
                            subjWindowLayout2.setComponentAlignment(tmp2,
                                    Alignment.MIDDLE_CENTER);

                            Image tmp3 = subjectArrayList.get(i+2);
                            subjWindowLayout3.addComponent(tmp3);
                            subjWindowLayout3.setComponentAlignment(tmp3,
                                    Alignment.MIDDLE_CENTER);
                        }*/


                    } else {
                        subjWindowLayout1.removeAllComponents();
                        subjWindowLayout2.removeAllComponents();
                        subjWindowLayout3.removeAllComponents();
                        subjPanel.setVisible(false);
                    }
                });

        Button verbButton = new Button("Verb");
        verbButton.setWidth("100%");
        folderWindowLayout.addComponent(verbButton);

        getUI().getCurrent().addWindow(window);

    }

    /*  IN CASE OF TABLEDESIGN
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

        Table subjectImageTable = new Table();
        subjectImageTable.setSizeFull();
        subjectImageTable.setSelectable(true);
        subjectImageTable.setMultiSelect(false);
        subjectImageTable.setVisible(false);
        subjectImageTable.setPageLength(3);

        subjectButton
                .addClickListener(e -> {

                    if (!subjectImageTable.isVisible()) {
                        ArrayList<Image> subjectArrayList = ImageLoader
                                .loadImages(AppView.getBasepath()
                                        + "/WEB-INF/subjekt/");
                        subjectImageTable.setVisible(true);

                        for (int i = 0; i < (subjectArrayList.size() / 3); i++) {
                            subjectImageTable.addIte
                        }


                        for (Image img : subjectArrayList) {
                            subjWindowLayout.addComponent(img);
                            subjWindowLayout.setComponentAlignment(img,
                                    Alignment.MIDDLE_CENTER);
                        }
                    } else {
                        subjWindowLayout.removeAllComponents();
                        subjPanel.setVisible(false);
                    }
                });

    } */
}