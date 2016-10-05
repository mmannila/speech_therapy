package org.talterapeut_app.appview;

import java.util.ArrayList;

import org.talterapeut_app.AppView;
import org.talterapeut_app.model.ImageLoader;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

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
        subjectButton
                .addClickListener(e -> {

                    if (!subjPanel.isVisible()) {
                        ArrayList<Image> subjectArrayList = ImageLoader
                                .loadImages(AppView.getBasepath()
                                        + "/WEB-INF/subjekt/");
                        subjPanel.setVisible(true);

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

        Button verbButton = new Button("Verb");
        verbButton.setWidth("100%");
        folderWindowLayout.addComponent(verbButton);

        getUI().getCurrent().addWindow(window);

    }
}