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
            int phrase_length = AppView.getPhraseLength();
            String tmp = "";
            if (length > 0) {
                if (list.get(0) != null) {
                    tmp = list.get(0).getDescription();
                } else {
                    tmp = "___";
                }
                for (int i = 1; i < phrase_length; i++) {
                    if (list.get(i) != null) {
                        tmp += " " + list.get(i).getDescription();
                    } else {
                        tmp += " ___";
                    }
                }
                AppView.setDragDropLabel(tmp);

                // test if the answer was correct or not
                if ((Objects.equals(tmp, "Subject Verb Object") && phrase_length == 3)
                        || (Objects.equals(tmp, "Subject Verb") && phrase_length == 2)) {
                    new Notification("Correct!").show(Page.getCurrent());
                } else {
                    new Notification("Incorrect").show(Page.getCurrent());
                }
                
                // soundMachine.playSound(isAnswerCorrect(Objects)); // TODO
                AppView.soundMachine.playSound(Objects.equals(tmp,"Subject Verb Object")); // FIXME

            } else {
                AppView.setDragDropLabel("This DnD layout is empty!");
            }
        });
    }
}