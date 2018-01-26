package de.hne.customwidgettest;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class WidgettestMain {
	
    public static void main(String[] args) {
        Display display = new Display();

        Shell shell = new Shell(display);

        // the layout manager handle the layout
        // of the widgets in the container
        shell.setLayout(new FillLayout());
        createAmpelWidget(shell);

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
    
    private static void createAmpelWidget(Shell shell) {
    	
		Composite comp = new Composite(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.makeColumnsEqualWidth = false;
		comp.setLayout(gridLayout);
		
		GridData gridData = new GridData();
		gridData.widthHint = 60;
		gridData.minimumWidth = 60;
		gridData.heightHint = 100;
		gridData.minimumHeight = 100;		
		
		AmpelWidget ampel = null;		
		GridData gd = new GridData(SWT.BEGINNING, SWT.FILL, true, true);		
		ampel = new AmpelWidget(comp, SWT.NONE);	
		ampel.setLayoutData(gd);	
		ampel.setCircleSize(20);    	    	
    }
	
}
