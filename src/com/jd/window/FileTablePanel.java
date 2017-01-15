package com.jd.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jd.Event.UploadThread;

public class FileTablePanel extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void init()
	{
		this.setLayout(new BorderLayout(0,0));
		
		JPanel jp = new JPanel();
		jp.setLayout(new GridLayout(100,1));
		
		JScrollPane jsp = new JScrollPane();
		jsp.setViewportView(jp);
		
		this.addFile(jp,23);
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
				if(MainFrame.fileinfoVector.get(i).prodrass == 100)
				{
					j.setBackground(Color.green);
				}else{
					j.setBackground(Color.white);
				}
				
				j.setLayout(new GridLayout(1,5));
				j.add(new Label(i+1+""));
				j.add(new Label(MainFrame.fileinfoVector.get(i).file.getName()));
				j.add(new Label(MainFrame.fileinfoVector.get(i).file.length()/1024+" KB"));
				
				
				
				j.add(new Label(MainFrame.fileinfoVector.get(i).prodrass+""));
				
				//System.out.println(MainFrame.fileinfoVector.get(i).prodrass);
				
				j.add(new Label(MainFrame.fileinfoVector.get(i).fileStatus));
				panel.add(j);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("选择文件"))
		{
			//打开文件选择器
			new ChooseFileWindow();
		}
		if(e.getActionCommand().equals("开始上传"))
		{
			if(MainFrame.fileinfoVector != null)
			{
				new UploadThread();
			}
		}
		
	}
}
