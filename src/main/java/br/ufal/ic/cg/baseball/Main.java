package br.ufal.ic.cg.baseball;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

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
		JButton clearStands = new JButton("Reset Stands");
		JLabel staticLabel = new JLabel("Drawing mode:");
		JLabel drawMode = new JLabel(canvas.getDrawingMode());
		JSlider lineWidthSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, 25);
		bresenhamToggle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				canvas.changeDrawingMode();
				drawMode.setPreferredSize(drawMode.getSize());
				drawMode.setText(canvas.getDrawingMode());
			}			
		});
		clearStands.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				canvas.clearStands();
			}
			
		});
		ColorPickerButton colorPicker = new ColorPickerButton(Color.WHITE);
		colorPicker.addColorChangedListener(new ColorChangedListener() {
		    @Override
		    public void colorChanged(Color newColor) {
		    	canvas.setMouseColor(newColor);
		    }
		});
		lineWidthSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				canvas.setMouseWidth(lineWidthSlider.getValue());
				canvas.display();
			}
			
		});
		lineWidthSlider.setMinimum(2);
		lineWidthSlider.setMajorTickSpacing(1);
		lineWidthSlider.setMaximum(8);
		lineWidthSlider.setSnapToTicks(true);
		lineWidthSlider.setPaintTicks(true);
		lineWidthSlider.setValue(3);
		jpanel.add(clearStands);
		jpanel.add(bresenhamToggle);
		jpanel.add(staticLabel);
		jpanel.add(drawMode);
		jpanel.add(colorPicker);
		jpanel.add(lineWidthSlider);
		jframe.add(jpanel);
		jframe.pack();
		jframe.setLocation(10, 10);
		jframe.getContentPane().add(canvas);
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
