package org.talterapeut_app.appview;

import org.talterapeut_app.AppView;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.VerticalLayout;

public class WordLengthLayout extends VerticalLayout {

    public WordLengthLayout() {

        // listener which changes the phrase length + the other components
        ClickListener setWordCount = new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                int tmp = (int) event.getButton().getData();
                if (AppView.getPhraseLength() != tmp) {
                    AppView.setPhraseLength(tmp);
                    AppView.randomize();
                }
            }
        };

        Button twoWordButton = new Button("Two Words");
        Button threeWordButton = new Button("Three Words");
        // Button fourWordButton = new Button("Four Words");
        twoWordButton.setData(2);
        threeWordButton.setData(3);

        // all buttons use the same listener
        twoWordButton.addClickListener(setWordCount);
        threeWordButton.addClickListener(setWordCount);
        // fourWordButton.addClickListener(setWordCount);

        twoWordButton.setWidth("100%");
        twoWordButton.addStyleName("wordlength");
        threeWordButton.setWidth("100%");
        threeWordButton.addStyleName("wordlength");
        // fourWordButton.setWidth("100%");

        addComponent(twoWordButton);
        addComponent(threeWordButton);
        // wordSelectLayout.addComponent(fourWordButton);
    }
}