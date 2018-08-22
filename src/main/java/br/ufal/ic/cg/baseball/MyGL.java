package br.ufal.ic.cg.baseball;

import com.jogamp.opengl.DebugGL2;
import com.jogamp.opengl.GL2;

public class MyGL extends DebugGL2 {

	public MyGL(GL2 downstream) {
		super(downstream);
	}
	
	public void drawBresenhamCircle(int radius) {
		int x = 0;
		int y = radius;
		float d = 5/4 - radius;
		while(x <= y) {
			this.glVertex2i(x, y);
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
	
	public void drawFullBresenhamCircle(int centerX, int centerY, int radius) {
		this.glTranslatef(centerX, centerY, 0);
		for(int j = 0; j < 9; ++j) {
			this.glBegin(GL2.GL_TRIANGLE_FAN);
				this.glVertex2i(0, 0);
				drawBresenhamCircle(radius);
			this.glEnd();
	        this.glRotatef(42f, 0, 0, 1);
        }

	}
	
	public void drawCircle(int radius) {
		int y = (int) Math.sqrt(radius*radius);
		for(int x = 0; x <= y; x++){
			this.glVertex2i(x, y);
			y = (int) Math.sqrt(radius*radius - x*x);
		}
	}
	
	public void drawFullCircle(int centerX, int centerY, int radius) {
		this.glTranslatef(centerX, centerY, 0);
		for(int j = 0; j < 9; ++j) {
			this.glBegin(GL2.GL_TRIANGLE_FAN);
				this.glVertex2i(0, 0);
				drawCircle(radius);
			this.glEnd();
	        this.glRotatef(42.0f, 0, 0, 1);
        }
	}
	
	public void drawBresenhamLine(int x1, int y1, int x2, int y2) {
		boolean swap = false;
		boolean flip = false;
		if(Math.abs(y2 - y1) > Math.abs(x2 - x1)) {
			int aux = x1;
			x1 = y1;
			y1 = aux;
			aux = x2;
			x2 = y2;
			y2 = aux;
			swap = true;
		}
		if(x1 > x2) {
			int aux = x1;
			x1 = x2;
			x2 = aux;
			aux = y1;
			y1 = y2;
			y2 = aux;
		}
		if(y1 > y2) {
			y1 = -y1;
			y2 = -y2;
			flip = true;
		}
		
		int dx = x2 - x1;
		int dy = y2 - y1;
		int d = 2 * dy - dx;
		int incE = 2 * dy;
		int incNE = 2 * (dy - dx);
		int x = x1;
		int y = y1;
		if(swap) 
			if(flip) drawPixel(y, -x);
			else drawPixel(y, x);
		else 
			if(flip) drawPixel(x, -y);
			else drawPixel(x, y);
		
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
			if(swap) 
				if(flip) drawPixel(-y, x);
				else drawPixel(y, x);
			else 
				if(flip) drawPixel(x, -y);
				else drawPixel(x, y);
		}
	}
	
	public void drawLine(int x1, int y1, int x2, int y2) {
		int x, y;
		float a;
		boolean swap = false;
		boolean flip = false;
		if(Math.abs(y2 - y1) > Math.abs(x2 - x1)) {
			int aux = x1;
			x1 = y1;
			y1 = aux;
			aux = x2;
			x2 = y2;
			y2 = aux;
			swap = true;
		}
		if(x1 > x2) {
			int aux = x1;
			x1 = x2;
			x2 = aux;
			aux = y1;
			y1 = y2;
			y2 = aux;
		}
		if(y1 > y2) {
			y1 = -y1;
			y2 = -y2;
			flip = true;
		}
		
		a = (float) Math.abs(y2 - y1)/Math.abs(x2 - x1);
		for(x = x1; x <= x2; x++) {
			y = (int) (y1+a*(x-x1));
			if(swap) 
				if(flip) drawPixel(-y, x);
				else drawPixel(y, x);
			else 
				if(flip) drawPixel(x, -y);
				else drawPixel(x, y);
		}
		
	}
	
	public void drawPixel(int x, int y) {
		this.glBegin(GL2.GL_POINTS);
			this.glVertex2i(x, y);
		this.glEnd();
	}

}
