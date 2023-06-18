package com.software_design;

import java.awt.*;

import javax.swing.*;

public class IconClass {	
	public static ImageIcon createIcon(String location, int width, int height) {
		ImageIcon imageIcon =  new ImageIcon(location);
		Image image = imageIcon.getImage();
		Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}
}
