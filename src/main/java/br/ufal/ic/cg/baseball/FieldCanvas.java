package br.ufal.ic.cg.baseball;

import com.jogamp.opengl.DebugGL2;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

public class FieldCanvas extends GLCanvas implements GLEventListener{
	
	private boolean bresenham = false;
	
	private static final long serialVersionUID = 1L;

	public FieldCanvas(int width, int height, GLCapabilities capabilities) {
		super(capabilities);
		setSize(width, height);
		addGLEventListener(this);
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		drawable.setGL(new DebugGL2(gl));
		gl.glClearColor(0.2f, 0.5f, 0.0f, 0.0f);
		gl.glLineWidth(10);
		gl.glPointSize(2);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glOrtho(0, 1600, 0, 1600, -1, 1);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
	    gl.glLoadIdentity();        
        
       	gl.glColor3f(0.625f, 0.32f, 0.176f);
       	//draw outer field outline
       	drawOuterField(gl);
       	//draw inner field
       	drawInnerField(gl);
       	
        gl.glColor3f(0, 0.5f, 0);
        //draw inner diamond
        drawDiamond(gl);
        
        drawBase(gl, 800, 440, 80);
	    drawBase(gl, 615, 640, 45);
	    drawBase(gl, 985, 640, 45);
	    drawBase(gl, 800, 620, 55);
	    drawBase(gl, 800, 820, 45);
	    
	    //draw foul lines
        gl.glColor3f(1f, 1f, 1f);
	    drawFoulLines(gl);
	    
	    
    	/*gl.glPushMatrix();
	        if(bresenham) drawFullBresenhamCircle(gl, 800, 420, 100);
	        else drawFullCircle(gl, 800, 420, 100);
        gl.glPopMatrix();*/
	    gl.glFlush();
	}
	
	private void drawOuterField(GL2 gl) {
       	gl.glPushMatrix();
	        gl.glTranslatef(800, 400, 0);
	        gl.glBegin(GL2.GL_LINE_STRIP);
		        if(bresenham) drawBresenhamCircle(gl, 800);
		        else drawCircle(gl, 800);
		        gl.glVertex2i(0, 0);
		    gl.glEnd();
	        gl.glRotatef(45.0f, 0, 0, 1);
	        gl.glBegin(GL2.GL_LINE_STRIP);
		        gl.glVertex2i(0, 0);    
		        if(bresenham) drawBresenhamCircle(gl, 800);
		        else drawCircle(gl, 800);
	        gl.glEnd();
        gl.glPopMatrix();
	}
	
	private void drawInnerField(GL2 gl) {
		gl.glPushMatrix();
	        gl.glTranslatef(800, 400, 0);
	        for(int i = 0; i < 2; ++i) {
		        gl.glBegin(GL2.GL_TRIANGLE_FAN);
		        	gl.glVertex2i(0, 0);
			        if(bresenham) drawBresenhamCircle(gl, 500);
			        else drawCircle(gl, 500);
			    gl.glEnd();
		        gl.glRotatef(45.0f, 0, 0, 1);
	        }
        gl.glPopMatrix();
	}
	
	private void drawDiamond(GL2 gl) {
		gl.glPushMatrix();
	        gl.glTranslatef(800, 430, 0);
	        gl.glBegin(GL2.GL_QUADS);
			    gl.glVertex2i(-200, 200);
			    gl.glVertex2i(0, 400);
			    gl.glVertex2i(200, 200);
			    gl.glVertex2i(0, 0);
		    gl.glEnd();
	   	gl.glPopMatrix();
	}
	
	private void drawFoulLines(GL2 gl) {
		gl.glPushMatrix();
	        gl.glTranslatef(780, 420, 0);
		    if(bresenham) drawBresenhamLine(gl, 0, 0, 600, 600);
	        else drawLine(gl, 0, 0, 600, 600);
		    gl.glTranslatef(40, 0, 0);
		    gl.glRotatef(90.0f, 0, 0, 1);
		    if(bresenham) drawBresenhamLine(gl, 0, 0, 600, 600);
	        else drawLine(gl, 0, 0, 600, 600);
	    gl.glPopMatrix();
	}
	
	private void drawBase(GL2 gl, int centerX, int centerY, int radius) {
		gl.glPushMatrix();
			gl.glColor3f(0.625f, 0.32f, 0.176f);
			if(bresenham) drawFullBresenhamCircle(gl, centerX, centerY, radius);
        	else drawFullCircle(gl, centerX, centerY, radius);
        gl.glPopMatrix();
        gl.glColor3f(1, 1, 1);
        gl.glPointSize(5);
    	gl.glBegin(GL2.GL_POINTS);
    		gl.glVertex2i(centerX, centerY);
    	gl.glEnd();
    	gl.glPointSize(2);
	}
	
	public void drawBresenhamCircle(GL2 gl, int radius) {
		int x = 0;
		int y = radius;
		float d = 5/4 - radius;
		while(x <= y) {
			gl.glVertex2i(x, y);
			if(d < 0) {
				//E
				d += 2 * x + 3;
				x++;
			} else {
				//SE
				d += 2 * (x-y) + 5;
				x++;
				y--;
			}
		}
	}
	
	private void drawFullBresenhamCircle(GL2 gl, int centerX, int centerY, int radius) {
		gl.glTranslatef(centerX, centerY, 0);
		for(int j = 0; j < 9; ++j) {
			gl.glBegin(GL2.GL_TRIANGLE_FAN);
				gl.glVertex2i(0, 0);
				drawBresenhamCircle(gl, radius);
			gl.glEnd();
	        gl.glRotatef(44f, 0, 0, 1);
        }

	}
	
	public void drawCircle(GL2 gl, int radius) {
		int y = (int) Math.sqrt(radius*radius);
		for(int x = 0; x <= y; x++){
			gl.glVertex2i(x, y);
			y = (int) Math.sqrt(radius*radius - x*x);
		}
	}
	
	public void drawFullCircle(GL2 gl, int centerX, int centerY, int radius) {
		gl.glTranslatef(centerX, centerY, 0);
		for(int j = 0; j < 8; ++j) {
			gl.glBegin(GL2.GL_TRIANGLE_FAN);
				gl.glVertex2i(0, 0);
				drawCircle(gl, radius);
			gl.glEnd();
	        gl.glRotatef(45.0f, 0, 0, 1);
        }
	}
	
	public void drawBresenhamLine(GL2 gl, int x1, int y1, int x2, int y2) {
		int dx = x2 - x1;
		int dy = y2 - y1;
		int d = 2 * dy - dx;
		int incE = 2 * dy;
		int incNE = 2 * (dy - dx);
		int x = x1;
		int y = y1;
		drawPixel(gl, x, y);
		while(x < x2) {
			if(d <= 0) {
				//E
				d += incE;
				x++;
			} else {
				//NE
				d += incNE;
				x++;
				y++;
			}
			drawPixel(gl, x, y);
		}
	}
	
	void drawLine(GL2 gl, int x1, int y1, int x2, int y2) {
		int x, y;
		float a;
		a = (y2 - y1)/(x2 - x1);
		for(x = x1; x <= x2; x++) {
			y = (int) (y1+a*(x-x1));
			drawPixel(gl, x, y);
		}
	}
	
	void drawPixel(GL2 gl, int x, int y) {
		gl.glBegin(GL2.GL_POINTS);
			gl.glVertex2i(x, y);
		gl.glEnd();
	}
	
	public boolean getBresenham() {
		return bresenham;
	}
	
	public void setBresenham() {
		bresenham = !bresenham;
		display();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(0, 0, width, height);
	}
	
}
