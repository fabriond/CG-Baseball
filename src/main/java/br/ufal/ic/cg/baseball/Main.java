package br.ufal.ic.cg.baseball;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

public class Main {

	public static void main(String[] args) {
		int width = 800;
		int height = 800;
		
		GLCapabilities capabilities = new GLCapabilities(GLProfile.getDefault());
		FieldCanvas canvas = new FieldCanvas(width, height, capabilities);
		final JFrame jframe = new JFrame("Baseball Field");
		final JPanel jpanel = new JPanel();
		JButton bresenham = new JButton("Bresenham");
		bresenham.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				canvas.setBresenham();
				if(canvas.getBresenham()) bresenham.setText("Normal");
				else bresenham.setText("Bresenham");
			}
			
		});
		jpanel.add(bresenham);
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
