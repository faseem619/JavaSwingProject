import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

public class MyFrame extends JFrame{
	MyFrame(){
		this.setVisible(true);
		this.setSize(300,325);
		this.setTitle("JFrame Tutorial");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		ImageIcon img = new ImageIcon("settings.svg");
		this.setIconImage(img.getImage());
		this.getContentPane().setBackground(new Color(0x12345F));

	}
		
}