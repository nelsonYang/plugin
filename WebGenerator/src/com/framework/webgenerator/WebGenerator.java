/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.framework.webgenerator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.loaders.DataObject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "com.framework.webgenerator.WebGenerator")
@ActionRegistration(
        displayName = "#CTL_WebGenerator")
@ActionReference(path = "Loaders/folder/any/Actions", position = 450)
@Messages("CTL_WebGenerator=WebGenerator")
public final class WebGenerator implements ActionListener {

    private final DataObject context;

    public WebGenerator(DataObject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
            new WebPanel(context);
    }
}
