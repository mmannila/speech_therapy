package org.talterapeut_app;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
// @SuppressWarnings("serial")
public class TerapeutUI extends UI {

    // navigator and the URLs used to navigate views
    static Navigator navigator;
    protected static final String LOGINVIEW = "login"; // login view
    protected static final String APPVIEW = "app"; // app/main view
    protected static final String PROFILEVIEW = "profile"; // profile view

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("Speech Therapy App (WIP)");
        
        // sets up the navigator and views before jumping to the app view
        navigator = new Navigator(this, this);
        navigator.addView(APPVIEW, new AppView());
        navigator.addView(PROFILEVIEW, new ProfileView());
        navigator.navigateTo(APPVIEW);
    }
    
    public static MenuBar getMenuBar() {
        MenuBar tmp = new MenuBar();

        MenuBar.Command menuNav = new MenuBar.Command() {
            MenuItem previous = null;

            @Override
            public void menuSelected(MenuItem selectedItem) {
                if (selectedItem.getText().equals("App")) {
                    navigator.navigateTo(APPVIEW);
                }

                if (selectedItem.getText().equals("Profile")) {
                    navigator.navigateTo(PROFILEVIEW);
                }

                if (previous != null) {
                    previous.setStyleName(null);
                }
                selectedItem.setStyleName("highlight");
                previous = selectedItem;
            }

        };

        tmp.addItem("App", menuNav);
        tmp.addItem("Profile", menuNav);
        tmp.addItem("Log Out", menuNav);
        tmp.setWidth("100%");
        return tmp;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = TerapeutUI.class, productionMode = false)
    public static class TerapeutUIServlet extends VaadinServlet {
    }
}