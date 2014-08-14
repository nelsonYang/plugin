
package com.framework.generator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.loaders.DataObject;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(
        category = "File",
        id = "com.framework.generator.ServiceGeneratorAction")
@ActionRegistration(
        displayName = "#CTL_ServiceGeneratorAction")
@ActionReference(path = "Loaders/folder/any/Actions", position = 350)
@Messages("CTL_ServiceGeneratorAction=ServiceGeneratorAction")
public final class ServiceGeneratorAction implements ActionListener {

    private final DataObject context;

    public ServiceGeneratorAction(DataObject context) {
          this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
         new ServiceDAOInfoPanel(context);
    }
    
   
}
