package com.framwork.dao.generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.loaders.DataObject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "com.framwork.dao.generator.DAOGeneratorAction")
@ActionRegistration(
        displayName = "#CTL_DAOGeneratorAction")
@ActionReference(path = "Loaders/folder/any/Actions", position = 550)
@Messages("CTL_DAOGeneratorAction=DAOGeneratorAction")
public final class DAOGeneratorAction implements ActionListener {

    private final DataObject context;

    public DAOGeneratorAction(DataObject context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // TODO use context
        new DAOInfoPanel(context);
    }
}
