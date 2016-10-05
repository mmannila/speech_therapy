package org.talterapeut_app.appview;

import org.talterapeut_app.AppView;

import com.vaadin.ui.Button;

public class RandomButton extends Button {

    public RandomButton() {
        setCaption("Randomize");
        addClickListener(e -> {
            AppView.randomize();
        });
    }
}