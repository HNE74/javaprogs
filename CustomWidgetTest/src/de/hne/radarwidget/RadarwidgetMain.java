package de.hne.radarwidget;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class RadarwidgetMain {

	private static List<RadarObject> objList = new ArrayList<RadarObject>();
	private static RadarWidget radar = null;
	
    public static void main(String[] args) {
        Display display = new Display();

        Shell shell = new Shell(display);

        // the layout manager handle the layout
        // of the widgets in the container
        shell.setLayout(new FillLayout());
        createRadarWidget(shell);

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }

            objList.get(0).setDegrees(90);            
            objList.get(0).setSpeed(0.2);
            objList.get(0).move();
                          
            if(!radar.isDisposed()) {
            	radar.redraw();
            }
                        
            radar.setCourse(radar.getCourse()+1);

            try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        display.dispose();
    }
    
    private static void createRadarWidget(Shell shell) {
    	
		Composite comp = new Composite(shell, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		gridLayout.makeColumnsEqualWidth = false;
		comp.setLayout(gridLayout);
		
		GridData gridData = new GridData();
		gridData.widthHint = 405;
		gridData.minimumWidth = 405;
		gridData.heightHint = 405;
		gridData.minimumHeight = 405;		

		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);		
		radar = new RadarWidget(comp, SWT.NONE);	
		addRadarObjects(radar);
		radar.setLayoutData(gd);
    }


    
    private static void addRadarObjects(RadarWidget radar) {
    	RadarObject obj = new RadarObject();
    	obj.setxPos(0);
    	obj.setyPos(0);
    	obj.setTag("A");
    	radar.addRadarObject(obj);
    	objList.add(obj);
    	
//    	obj = new RadarObject();
//    	obj.setxPos(-20);
//    	obj.setyPos(-20);
//    	obj.setTag("B");
//    	radar.addRadarObject(obj);
//    	objList.add(obj);
//    	
//    	obj = new RadarObject();
//    	obj.setxPos(-20);
//    	obj.setyPos(-20);
//    	obj.setTag("B");
//    	radar.addRadarObject(obj);
//    	objList.add(obj);    	
//    	
//    	obj = new RadarObject();
//    	obj.setxPos(-20);
//    	obj.setyPos(30);
//    	obj.setTag("c");
//    	radar.addRadarObject(obj);
//    	objList.add(obj);    	
//    	
//    	obj = new RadarObject();
//    	obj.setxPos(20);
//    	obj.setyPos(10);
//    	obj.setTag("d");
    	radar.addRadarObject(obj);
    	objList.add(obj);    	
    }
}
