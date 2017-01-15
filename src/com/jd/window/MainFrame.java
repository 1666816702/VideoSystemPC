package com.jd.window;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.jd.model.FileInfo;

public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9101280566563643985L;
	
	//窗口大小
	private static int WINDOW_WIDTH = 1000;
	private static int WINDOW_HEIGHT = 600;
	//窗口位置
	private int window_X;
	private int window_Y;
	//窗口标题
	private String title = "古驿平安上传客户端";
	//窗口图标
	private ImageIcon icon = new ImageIcon("img/window_icon.gif");
	//主面板
	public static MainPanel mainPanel = null;
	
	//
	public static Vector<FileInfo> fileinfoVector = null;
	//
	public static MainFrame mainFrame = null;
	
	/*
	 * 构造函数 初始化窗口
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
	 * 计算窗口显示位置
	 */
	public void setWindowLocation()
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.window_X = (int)(screenSize.getWidth()-WINDOW_WIDTH)/2;
		this.window_Y = (int)(screenSize.getHeight()-WINDOW_HEIGHT)/2;
	}
	
	/*
	 * 应用入口
	 */
	public static void main(String[] args) {
		mainFrame = new MainFrame();
		mainFrame.setVisible(true);
	}
}
