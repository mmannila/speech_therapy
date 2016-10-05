package org.talterapeut_app.appview;

import org.talterapeut_app.AppView;

import com.vaadin.ui.Button;

public class ResetButton extends Button {

    public ResetButton() {
        setCaption("Reset");
        addClickListener(e -> {
            AppView.reset();
        });
    }
}