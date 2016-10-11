package org.talterapeut_app.appview;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Panel;
import com.vaadin.ui.SingleComponentContainer;

import fi.jasoft.dragdroplayouts.DDPanel;
import fi.jasoft.dragdroplayouts.DDPanel.PanelTargetDetails;
import fi.jasoft.dragdroplayouts.drophandlers.AbstractDefaultLayoutDropHandler;
import fi.jasoft.dragdroplayouts.events.LayoutBoundTransferable;

// this drop handles switches places with components instead of removing one of them
public class SwapPanelDropHandler extends AbstractDefaultLayoutDropHandler {

    @Override
    protected void handleComponentReordering(DragAndDropEvent event) {
        handleDropFromLayout(event);
    }

    @Override
    protected void handleDropFromLayout(DragAndDropEvent event) {
        // transferable = component being dragged
        // target = drop target

        LayoutBoundTransferable transferable = (LayoutBoundTransferable) event
                .getTransferable();
        PanelTargetDetails details = (PanelTargetDetails) event
                .getTargetDetails();
        DDPanel panel = (DDPanel) details.getTarget();
        Component source = transferable.getSourceComponent();
        Component target_comp = panel.getContent(); // the component in the
                                                    // targetted panel
        Component source_comp = transferable.getComponent(); // the dragged
                                                             // component

        // Detach the dragged component from its old source
        if (source instanceof ComponentContainer) {
            ((ComponentContainer) source).removeComponent(source_comp);
        } else if (source instanceof SingleComponentContainer) {
            ((SingleComponentContainer) source).setContent(null);
        }

        // Detach target panel's component from it
        if (panel instanceof ComponentContainer) {
            ((ComponentContainer) panel).removeComponent(target_comp);
        } else if (panel instanceof SingleComponentContainer) {
            ((SingleComponentContainer) panel).setContent(null);
        }

        // Attach to new sources
        panel.setContent(source_comp);
        ((AbstractSingleComponentContainer) source).setContent(target_comp);
    }

    @Override
    protected void handleHTML5Drop(DragAndDropEvent event) {
        PanelTargetDetails details = (PanelTargetDetails) event
                .getTargetDetails();
        DDPanel panel = (DDPanel) details.getTarget();
        panel.setContent(resolveComponentFromHTML5Drop(event));
    }

    @Override
    public Class<Panel> getTargetLayoutType() {
        return Panel.class;
    }
}