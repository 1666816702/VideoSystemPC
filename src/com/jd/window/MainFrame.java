package com.jd.window;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

import com.jd.model.FileInfo;

public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9101280566563643985L;
	
	//
	private static int WINDOW_WIDTH = 1000;
	private static int WINDOW_HEIGHT = 600;
	//
	private int window_X;
	private int window_Y;
	//¢˜
	private String title = "¿Í»§¶Ë";
	//
	private ImageIcon icon = new ImageIcon("img/window_icon.gif");
	//
	public static MainPanel mainPanel = null;
	
	//
	public static int currentUploadFile = 0;
	//
	public static Vector<FileInfo> fileinfoVector = null;
	//
	public static MainFrame mainFrame = null;
	
	public static boolean isUpload = false;
	
	/*
	 * 
	 */
	public MainFrame()
	{
		mainPanel = new MainPanel(WINDOW_WIDTH,WINDOW_HEIGHT);
		
		this.setWindowLocation();
		this.setBounds(window_X, window_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setTitle(title);
		this.setIconImage(icon.getImage());
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.add(mainPanel);
	}
	
	/*
	 * 
	 */
	public void setWindowLocation()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.window_X = (int)(screenSize.getWidth()-WINDOW_WIDTH)/2;
		this.window_Y = (int)(screenSize.getHeight()-WINDOW_HEIGHT)/2;
	}
	
	/*
	 * 
	 */
	public static void main(String[] args) {
		try {
	    	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
		
		mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}
}
