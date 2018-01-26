package de.hne.databindingtest;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DatabindingMain {

		private Text nameWidget = null;
		private Mitarbeiter mitarbeiter = null;
		private Button showModel = null;
		private Button changeModel = null;
	
		public static void main(String args[]) {
			DatabindingMain dbMain = new DatabindingMain();
			dbMain.show();
		}
		
		private void show() {
	        Display display = new Display();
	        Shell shell = new Shell(display);

	        Realm.runWithDefault(SWTObservables.getRealm(display),new Runnable()
	        {
	           public void run()
	           {
	   	        // the layout manager handle the layout
	   	        // of the widgets in the container
	   	        shell.setLayout(new FillLayout());
	   	        DatabindingMain.this.mitarbeiter = new Mitarbeiter();
	   	        createMitarbeiterDisplay(shell);
	   	        createDatabinding();
	   	   	        
	   	        shell.open();
	   	        while (!shell.isDisposed()) {
	   	            if (!display.readAndDispatch()) {
	   	                display.sleep();
	   	            }
	   	        }
	   	        display.dispose();	
	           }
	        });	        
		}
		
		private void createDatabinding() {
//	        mitarbeiter.addPropertyChangeListener("name", new PropertyChangeListener() {
//	        	public void propertyChange(PropertyChangeEvent evt) { nameWidget.setText((String) evt.getNewValue());
//	        	}
//	        });	
//	        
//	        nameWidget.addModifyListener(new ModifyListener() {
//		        public void modifyText(ModifyEvent e) {
//		        	mitarbeiter.setName(nameWidget.getText());
//		        }
//	        });	
			
			// create new Context
			DataBindingContext ctx = new DataBindingContext();

			// define the IObservables
			IObservableValue target = WidgetProperties.text(SWT.Modify).
			    observe(nameWidget);
			IObservableValue model= BeanProperties.
			    value(Mitarbeiter.class,"name").observe(mitarbeiter);

			// connect them
			ctx.bindValue(target, model);
 			
		}
		
		private void createMitarbeiterDisplay(Shell shell) {
			Composite comp = new Composite(shell, SWT.NONE);
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 1;
			gridLayout.makeColumnsEqualWidth = false;
			comp.setLayout(gridLayout);
			
			GridData gridData = new GridData();
			gridData.grabExcessHorizontalSpace = false;
			gridData.grabExcessVerticalSpace = false;
			
			nameWidget = new Text(comp, SWT.NONE);
			nameWidget.setLayoutData(gridData);
			nameWidget.setText("Test");
				
			showModel = new Button(comp, SWT.NONE);
			showModel.setText("Show model");
			showModel.addSelectionListener(new SelectionAdapter()  {        	 
	            @Override
	            public void widgetSelected(SelectionEvent e) {
	            	System.out.println(mitarbeiter.getName());
	            }            
	        });	
	        			
			changeModel = new Button(comp, SWT.NONE);
			changeModel.setText("Change model");
			changeModel.addSelectionListener(new SelectionAdapter()  {        	 
	            @Override
	            public void widgetSelected(SelectionEvent e) {
	            	mitarbeiter.setName("ABC");
	            }            
	        });	
	        
			
		}
}
