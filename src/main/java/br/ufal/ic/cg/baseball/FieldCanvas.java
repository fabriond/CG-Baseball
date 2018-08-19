package br.ufal.ic.cg.baseball;

import com.jogamp.opengl.DebugGL2;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;

public class FieldCanvas extends GLCanvas implements GLEventListener{
	
	private boolean bresenham = true;
	
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
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
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
	    
        gl.glColor3f(0, 0.5f, 0);
	    
    	gl.glPushMatrix();
	        gl.glTranslatef(800, 400, 0);
	        if(bresenham) drawBresenhamCircle(gl, 800);
	        else drawCircle(gl, 800);
	        gl.glRotatef(45.0f, 0, 0, 1);
	        if(bresenham) drawBresenhamCircle(gl, 800);
	        else drawCircle(gl, 800);
        gl.glPopMatrix();
        
        
       	gl.glColor3f(0.625f, 0.32f, 0.176f);
       
       	gl.glPushMatrix();
	        gl.glTranslatef(800, 400, 0);
	        if(bresenham) drawBresenhamCircle(gl, 500);
	        else drawCircle(gl, 500);
	        gl.glRotatef(45.0f, 0, 0, 1);
	        if(bresenham) drawBresenhamCircle(gl, 500);
	        else drawCircle(gl, 500);
        gl.glPopMatrix();
       	
	    gl.glColor3f(1f, 1f, 1f);

	    gl.glPushMatrix();
	        gl.glTranslatef(780, 420, 0);
		    if(bresenham) drawBresenhamLine(gl, 0, 0, 600, 600);
	        else drawLine(gl, 0, 0, 600, 600);
		    gl.glTranslatef(40, 0, 0);
		    gl.glRotatef(90.0f, 0, 0, 1);
		    if(bresenham) drawBresenhamLine(gl, 0, 0, 600, 600);
	        else drawLine(gl, 0, 0, 600, 600);
	    gl.glPopMatrix();
	    
	    gl.glColor3f(0.625f, 0.32f, 0.176f);
	    

    	gl.glPushMatrix();
	        gl.glTranslatef(800, 420, 0);
	        for(int j = 0; j < 8; ++j) {
		        if(bresenham) drawBresenhamCircle(gl, 100);
		        else drawCircle(gl, 100);
		        gl.glRotatef(45.0f, 0, 0, 1);
	        }
        gl.glPopMatrix();
	    gl.glFlush();
	}
	
	public void drawBresenhamCircle(GL2 gl, int radius) {
		int x = 0;
		int y = radius;
		float d = 5/4 - radius;
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex2f(0, 0);
			while(y >= x) {
				gl.glVertex2f(x, y);
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
		gl.glEnd();
	}
	
	public void drawCircle(GL2 gl, int radius) {
    	//y = +- sqrt(r*r-x*x);
		gl.glBegin(GL2.GL_TRIANGLE_FAN);
			gl.glVertex2f(0, 0);
			for(int x = 0; x < Math.sqrt(radius)*Math.cos(Math.PI/4); ++x) {
				System.out.println(x+" "+(int) Math.sqrt(radius*radius - x*x));
				gl.glVertex2i(x, (int) Math.sqrt(radius*radius - x*x));
			}
		gl.glEnd();
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