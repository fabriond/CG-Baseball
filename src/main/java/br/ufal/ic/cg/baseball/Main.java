package br.ufal.ic.cg.baseball;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.FloatBuffer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;

import br.ufal.ic.cg.baseball.ColorPickerButton.ColorChangedListener;

public class Main {

	public static void main(String[] args) {
		int width = 800;
		int height = 800;
		
		GLCapabilities capabilities = new GLCapabilities(GLProfile.getDefault());
		FieldCanvas canvas = new FieldCanvas(width, height, capabilities);
		JFrame jframe = new JFrame("Baseball Field"); 
		JPanel jpanel = new JPanel();
		JButton bresenhamToggle = new JButton("Change drawing mode");
		JLabel staticLabel = new JLabel("Drawing mode:");
		JLabel drawMode = new JLabel(canvas.getDrawingMode());
		bresenhamToggle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				canvas.changeDrawingMode();
				drawMode.setText(canvas.getDrawingMode());
			}			
		});
		ColorPickerButton colorPicker = new ColorPickerButton(Color.WHITE);
		colorPicker.addColorChangedListener(new ColorChangedListener() {
		    @Override
		    public void colorChanged(Color newColor) {
		    	canvas.setMouseColor(newColor);
		    }
		});
		
		jpanel.add(bresenhamToggle);
		jpanel.add(colorPicker);
		jpanel.add(staticLabel);
		jpanel.add(drawMode);
		//canvas.addMouseListener(mouseEvent);
		//jframe.addMouseListener(mouseEvent);
		jframe.add(jpanel);
		jframe.pack();
		jframe.setLocation(10, 10);
		jframe.getContentPane().add(canvas);
        //jframe.getContentPane().add(canvas, BorderLayout.CENTER);
        jframe.setSize(width, height);
        jframe.setVisible(true);
        jframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowevent) {
                jframe.dispose();
                System.exit(0);
            }
        });
	}

}
