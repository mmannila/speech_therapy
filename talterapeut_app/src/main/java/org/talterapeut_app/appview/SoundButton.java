package org.talterapeut_app.appview;

import java.util.ArrayList;
import java.util.Objects;

import org.talterapeut_app.AppView;

import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Notification;

public class SoundButton extends Button {

    public SoundButton() {
        setCaption("Play Sound");
        addClickListener(e -> {
            ArrayList<Component> list = AppView.getDragDropComponents();

            int length = list.size();
            if (length > 0) {
                String tmp = list.get(0).getDescription();
                for (int i = 1; i < length; i++) {
                    tmp += " " + list.get(i).getDescription();
                }
                AppView.setDragDropLabel(tmp);

                // test if the answer was correct or not
                if ((Objects.equals(tmp, "Subject Verb Object") && AppView
                        .getPhraseLength() == 3)
                        || (Objects.equals(tmp, "Subject Verb") && AppView
                                .getPhraseLength() == 2)) {
                    new Notification("Correct!").show(Page.getCurrent());
                } else {
                    new Notification("Incorrect").show(Page.getCurrent());
                }

            } else {
                AppView.setDragDropLabel("This DnD layout is empty!");
            }
        });
    }
}