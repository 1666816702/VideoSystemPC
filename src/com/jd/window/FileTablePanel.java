package com.jd.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

public class FileTablePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init()
	{
		this.setLayout(new BorderLayout(0,0));
		
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(100,1));
		
		this.addFile(jp,23);
		
		JScrollPane jsp = new JScrollPane();
		jsp.setViewportView(jp);
		
		this.add(jsp,BorderLayout.CENTER);
	}
	
	public void addFile(JPanel panel,int progress)
	{
		if(MainFrame.fileinfoVector != null)
		{
			for(int i=0;i<MainFrame.fileinfoVector.size();i++)
			{
				JPanel j = new JPanel();
				j.setBorder(BorderFactory.createEtchedBorder());
				j.setBackground(Color.white);
				j.setLayout(new GridLayout(1,5));
				j.add(new Label(i+1+""));
				j.add(new Label(MainFrame.fileinfoVector.get(i).file.getName()));
				j.add(new Label(MainFrame.fileinfoVector.get(i).file.length()/1024+" KB"));
				
				JProgressBar jpb = new JProgressBar(0,100);
				jpb.setValue((int)MainFrame.fileinfoVector.get(i).prodrass);
				jpb.setStringPainted(true);
				jpb.setString("已上传:"+MainFrame.fileinfoVector.get(i).prodrass);
				
				
				System.out.println(MainFrame.fileinfoVector.get(i).prodrass);
				
				j.add(jpb);
				j.add(new Label(MainFrame.fileinfoVector.get(i).fileStatus));
				panel.add(j);
			}
		}
	}
}
