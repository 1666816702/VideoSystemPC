package com.jd.window;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static FileTablePanel ftp = null;
	
	private JPanel topPanel = null;
	private JPanel bodyPanel = null;
	
	/*
	 * 
	 */
	public void initTopPanel()
	{
		topPanel = new JPanel();
		topPanel.setBorder(BorderFactory.createEtchedBorder ());
		topPanel.setLayout(new FlowLayout(30));
		
		JButton selectBtn = new JButton("ѡ���ļ�");
		JButton startBtn = new JButton("��ʼ�ϴ�");
		
		selectBtn.addActionListener(new FileTablePanel());
		startBtn.addActionListener(new FileTablePanel());
		
		topPanel.add(selectBtn);
		topPanel.add(startBtn);
	}
	
	/*
	 * 
	 */
	public void initBodyPanel()
	{
		bodyPanel = new JPanel();
		bodyPanel.setLayout(new BorderLayout());
		
		JPanel tableHead = new JPanel();
		tableHead.setBorder(BorderFactory.createEtchedBorder ());
		tableHead.setLayout(new GridLayout(1,5));
		
		tableHead.add(new Label("���"));
		tableHead.add(new Label("����"));
		tableHead.add(new Label("��С"));
		tableHead.add(new Label("����"));
		tableHead.add(new Label("״̬"));
		
		ftp = new FileTablePanel();
		ftp.init();
		
		bodyPanel.add(BorderLayout.NORTH,tableHead);
		bodyPanel.add(BorderLayout.CENTER,ftp);
	}
	
	/*
	 * 
	 */
	public MainPanel(int width,int height)
	{
		initTopPanel();
		initBodyPanel();
		
		this.setBounds(0, 0, width, height);
		this.setLayout(new BorderLayout());
		
		this.add(BorderLayout.NORTH,topPanel);
		this.add(BorderLayout.CENTER,bodyPanel);
	}
}