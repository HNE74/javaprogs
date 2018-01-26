package de.hne.customwidgettest;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;


public class AmpelWidget extends Canvas {

	public enum State { RED, ORANGE, GREEN }
		
	private int circleSize = 10;
	private int horSpacing = 2;
	private int verSpacing = 2;
	private int width = calcWidth();	
	private int height = calcHeight();
	
	private Rectangle rectRed = null;
	private Rectangle rectOrange = null;
	private Rectangle rectGreen = null;
	
    private String text = "";
    
	private State status = State.RED;

    
    public AmpelWidget(Composite parent, int style) {
        super(parent, style);

        // Draw your widget.....
        addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent paintEvent) {
			    GC gc = paintEvent.gc;
			    paintBorderAndText(gc);
			}
		});
        
        addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent e) {
				// not implemented			
			}
			
			@Override
			public void mouseDown(MouseEvent e) {
				if(rectRed.intersects(e.x, e.y, 1, 1)) {
					setStatus(State.RED);
				}
				else if(rectOrange.intersects(e.x, e.y, 1, 1)) {
					setStatus(State.ORANGE);
				}
				else if(rectGreen.intersects(e.x, e.y, 1, 1)) {
					setStatus(State.GREEN);
				}				
			}
		
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				mouseDown(e);
			}
		});                      
    }

    protected void paintBorderAndText(GC gc) {
    	Display display = Display.getCurrent(); 	
    	if(status == State.RED) {
    		gc.setBackground(display.getSystemColor(SWT.COLOR_RED));
    	}
    	else {
    		gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_GRAY));    		
    	}  
    	rectRed = new Rectangle(horSpacing + 1, verSpacing, circleSize, circleSize);    	
    	gc.fillOval(rectRed.x, rectRed.y, rectRed.width, rectRed.height);
    	
    	if(status == State.ORANGE) {
    		Device device = Display.getCurrent();
    		gc.setBackground(new Color (device, 255,165,0));
    	}
    	else {
    		gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_GRAY));    		
    	}     	
    	int offset = verSpacing * 2 + circleSize;
    	rectOrange = new Rectangle(horSpacing + 1, verSpacing + offset, circleSize, circleSize);    	    	
    	gc.fillOval(rectOrange.x, rectOrange.y, rectOrange.width, rectOrange.height);
    	
    	if(status == State.GREEN) {
    		gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
    	}
    	else {
    		gc.setBackground(display.getSystemColor(SWT.COLOR_DARK_GRAY));    		
    	}    	
    	offset = verSpacing * 4 + circleSize * 2;
    	rectGreen = new Rectangle(horSpacing + 1, verSpacing + offset, circleSize, circleSize);    	
    	gc.fillOval(rectGreen.x, rectGreen.y, rectGreen.width, rectGreen.height);  
    	     	
    	gc.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
    	gc.drawRoundRectangle(0, 0, width, height, 10, 10);
    }
       
    public String getText() {
        checkWidget();
        return text;
    }

    public void setText(String text) {
        checkWidget();
        this.text = text;
        redraw();
    }
    
    @Override
    public Point computeSize(int wHint, int hHint, boolean changed) {
    	int height = verSpacing * 4 + circleSize * 3;
    	int width = horSpacing * 2 + circleSize;
    	return new Point(height, width);
    }

    public State getStatus() {
		return status;
	}

	public void setStatus(State status) {
		this.status = status;
		this.redraw();		
	}
	
	public int getCircleSize() {
		return circleSize;
	}

	public void setCircleSize(int circleSize) {
		this.circleSize = circleSize;
		this.width = calcWidth();
		this.height = calcHeight();
		this.redraw();
	}	
	
	private int calcWidth() {
		return horSpacing * 2 + circleSize + 1;
	}
	
	private int calcHeight() {
		return verSpacing * 5 + circleSize * 3 + 1;		
	}
}
