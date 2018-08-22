package br.ufal.ic.cg.baseball;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

public class FieldCanvas extends GLCanvas implements GLEventListener{
	
	private boolean bresenham = true;
	
	private Color mouseColor = Color.WHITE;
	
	private int mouseWidth = 4;
	
	List<Point> stands = new ArrayList<>();
	List<Color> standColors = new ArrayList<>();
	
	private static final long serialVersionUID = 1L;

	public FieldCanvas(int width, int height, GLCapabilities capabilities) {
		super(capabilities);
		setSize(width, height);
		addGLEventListener(this);
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		drawable.setGL(new MyGL(gl));
				
		gl.glClearColor(0.0f, 0.5f, 0.0f, 0.0f);
		gl.glLineWidth(10);
		gl.glPointSize(4);
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0, drawable.getSurfaceWidth(), 0, drawable.getSurfaceHeight(), -1, 1);
		addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent arg0) {
				addStands(arg0.getX(), (getHeight() - arg0.getY()));
				display();
			}
			
		});
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		MyGL gl = new MyGL(drawable.getGL().getGL2());
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
	    gl.glLoadIdentity();
	    
       	gl.glPushMatrix();
       		gl.glTranslatef(400, 200, 0);
	       	gl.glColor3f(0.625f, 0.32f, 0.176f);
	       	//draw outer field outline
	       	drawOuterField(gl);
	       	//draw inner field
	       	drawInnerField(gl);
	       	
	        gl.glColor3f(0, 0.5f, 0);
	        //draw inner diamond
	        drawDiamond(gl);
	        
	        //draw regular bases
        	drawBase(gl, -93, 130, 22);
        	drawBase(gl, 93, 130, 22);
        	drawBase(gl, 0, 210, 27);
	        
	        
		    //draw foul lines
	        gl.glColor3f(1f, 1f, 1f);
		    drawFoulLines(gl);
		    
		    //draw pitcher's mound
		    drawPitcherBase(gl, 0, 125, 22);
		    
		    //draw the home base
		    drawHomeBase(gl, 0, 20, 40);
		gl.glPopMatrix();
   		drawStands(gl); // positioning of this line makes stands go over the field when drawing
	    gl.glFlush();
	}
	
	private void drawOuterField(MyGL gl) {
       	gl.glPushMatrix();
	        gl.glBegin(GL2.GL_LINE_STRIP);
		        if(bresenham) gl.drawBresenhamCircle(400);
		        else gl.drawCircle(400);
		        gl.glVertex2i(0, 0);
		    gl.glEnd();
	        gl.glRotatef(45.0f, 0, 0, 1);
	        gl.glBegin(GL2.GL_LINE_STRIP);
		        gl.glVertex2i(0, 0);    
		        if(bresenham) gl.drawBresenhamCircle(400);
		        else gl.drawCircle(400);
	        gl.glEnd();
        gl.glPopMatrix();
	}
	
	private void drawInnerField(MyGL gl) {
		gl.glPushMatrix();
	        for(int i = 0; i < 2; ++i) {
		        gl.glBegin(GL2.GL_TRIANGLE_FAN);
		        	gl.glVertex2i(0, 0);
			        if(bresenham) gl.drawBresenhamCircle(250);
			        else gl.drawCircle(250);
			    gl.glEnd();
		        gl.glRotatef(45.0f, 0, 0, 1);
	        }
        gl.glPopMatrix();
	}
	
	private void drawDiamond(MyGL gl) {
		gl.glPushMatrix();
			gl.glTranslatef(0, 30, 0);
	        gl.glBegin(GL2.GL_QUADS);
			    gl.glVertex2i(-100, 100);
			    gl.glVertex2i(0, 200);
			    gl.glVertex2i(100, 100);
			    gl.glVertex2i(0, 0);
		    gl.glEnd();
	   	gl.glPopMatrix();
	}
	
	private void drawFoulLines(MyGL gl) {
		gl.glPushMatrix();
			gl.glTranslatef(-20, 5, 0);
		    if(bresenham) gl.drawBresenhamLine(0, 0, 300, 300);
	        else gl.drawLine(0, 0, 300, 300);
		    gl.glTranslatef(40, 0, 0);
		    gl.glRotatef(90.0f, 0, 0, 1);
		    if(bresenham) gl.drawBresenhamLine(0, 0, 300, 300);
	        else gl.drawLine(0, 0, 300, 300);
	    gl.glPopMatrix();
	}
	
	private void drawBase(MyGL gl, int centerX, int centerY, int radius) {
		gl.glPushMatrix();
			gl.glColor3f(0.625f, 0.32f, 0.176f);
			if(bresenham) gl.drawFullBresenhamCircle(centerX, centerY, radius);
        	else gl.drawFullCircle(centerX, centerY, radius);
        gl.glPopMatrix();
        
        gl.glColor3f(1, 1, 1);
        gl.glPointSize(8);
    	gl.glBegin(GL2.GL_POINTS);
    		gl.glVertex2i(centerX, centerY);
    	gl.glEnd();
    	gl.glPointSize(3);
	}
	
	private void drawHomeBase(MyGL gl, int centerX, int centerY, int radius) {
		drawBase(gl, centerX, centerY, radius);
		gl.glColor3f(1, 1, 1);
		if(bresenham) {
			gl.drawBresenhamLine(centerX+radius/7, centerY+radius/3, centerX+radius/3, centerY+radius/3);
	        gl.drawBresenhamLine(centerX+radius/3, centerY-radius/3, centerX+radius/3, centerY+radius/3);
	        gl.drawBresenhamLine(centerX+radius/7, centerY-radius/3, centerX+radius/3, centerY-radius/3);
	        gl.drawBresenhamLine(centerX-radius/3, centerY+radius/3, centerX-radius/7, centerY+radius/3);
	        gl.drawBresenhamLine(centerX-radius/3, centerY-radius/3, centerX-radius/3, centerY+radius/3);
	        gl.drawBresenhamLine(centerX-radius/3, centerY-radius/3, centerX-radius/7, centerY-radius/3);
		} else {
			gl.drawLine(centerX+radius/7, centerY+radius/3, centerX+radius/3, centerY+radius/3);
	        gl.drawLine(centerX+radius/3, centerY-radius/3, centerX+radius/3, centerY+radius/3);
	        gl.drawLine(centerX+radius/7, centerY-radius/3, centerX+radius/3, centerY-radius/3);
	        gl.drawLine(centerX-radius/3, centerY+radius/3, centerX-radius/7, centerY+radius/3);
	        gl.drawLine(centerX-radius/3, centerY-radius/3, centerX-radius/3, centerY+radius/3);
	        gl.drawLine(centerX-radius/3, centerY-radius/3, centerX-radius/7, centerY-radius/3);
		}
	}
	
	private void drawPitcherBase(MyGL gl, int centerX, int centerY, int radius) {
		drawBase(gl, centerX, centerY, radius);
		//clears the dot at the center of the base
		gl.glColor3f(0.625f, 0.32f, 0.176f);
		gl.glPointSize(8);
    	gl.glBegin(GL2.GL_POINTS);
    		gl.glVertex2i(centerX, centerY);
    	gl.glEnd();
    	//draws the line at the center of the pitcher's base
    	gl.glPointSize(4);
		gl.glColor3f(1, 1, 1);
		if(bresenham) gl.drawBresenhamLine(centerX-radius/4, centerY, centerX+radius/4, centerY);
		else gl.drawLine(centerX-radius/4, centerY, centerX+radius/4, centerY);
		gl.glPointSize(2);
	}
	
	public void drawStands(MyGL gl) {
		gl.glPointSize(mouseWidth);
		for(int i = 0; i < stands.size()-1; i++) {
			float currentColor[] = {1f, 1f, 1f, 1f};
			standColors.get(i+1).getColorComponents(currentColor);
			gl.glColor4f(currentColor[0], currentColor[1], currentColor[2], currentColor[3]);
	    	if(bresenham) gl.drawBresenhamLine(stands.get(i).x, stands.get(i).y, stands.get(i+1).x, stands.get(i+1).y);
	    	else gl.drawLine(stands.get(i).x, stands.get(i).y, stands.get(i+1).x, stands.get(i+1).y);
	    }
		gl.glPointSize(4);
	}
	
	public void addStands(int x, int y) {
		stands.add(new Point(x, y));
		standColors.add(mouseColor);
	}
	
	public String getDrawingMode() {
		if(bresenham) return "Bresenham";
		else return "Normal";
	}
	
	public void changeDrawingMode() {
		bresenham = !bresenham;
		display();
	}
	
	public void clearStands() {
		stands.clear();
		standColors.clear();
		display();
	}
	
	public int getMouseWidth() {
		return mouseWidth;
	}

	public void setMouseWidth(int mouseWidth) {
		this.mouseWidth = mouseWidth;
		display();
	}
	
	public void undoStand() {
		if(stands.size() > 0) {
			stands.remove(stands.size()-1);
			standColors.remove(standColors.size()-1);
			display();
		}
	}

	public Color getMouseColor() {
		return mouseColor;
	}
	
	public void setMouseColor(Color mouseColor) {
		this.mouseColor = mouseColor;
		display();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0, 0, width, height);
	}
	
}
