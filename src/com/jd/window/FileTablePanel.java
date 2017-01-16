package com.jd.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jd.Event.UploadThread;
import com.jd.model.FileInfo;

public class FileTablePanel extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static Vector<Vector<Label>> labels = new Vector<Vector<Label>>();
	
	public void init()
	{
		this.setLayout(new BorderLayout(0,0));
		
		JPanel jp = new JPanel();
		if(MainFrame.fileinfoVector == null)
		{
			jp.setLayout(new GridLayout(0,1));
		}else{
			jp.setLayout(new GridLayout(MainFrame.fileinfoVector.size()+30,1));
			if(MainFrame.isUpload)
			{
				for(int i=0;i<MainFrame.fileinfoVector.size();i++)
				{
					if(MainFrame.fileinfoVector.get(i).prodrass == 100)
					{
						labels.get(i).get(3).setBackground(Color.green);
						labels.get(i).get(3).setText("100%");
						labels.get(i).get(4).setText("上传完成");
					}
				}
				
				FileInfo fileinfo = MainFrame.fileinfoVector.get(MainFrame.currentUploadFile);
				
				DecimalFormat df = new DecimalFormat("##");
				labels.get(MainFrame.currentUploadFile).get(3).setText(df.format(fileinfo.prodrass)+"%");
				labels.get(MainFrame.currentUploadFile).get(4).setText(fileinfo.fileStatus);
			}
			else{
				for(int i=0;i<MainFrame.fileinfoVector.size();i++)
				{
					Vector<Label> v = new Vector<Label>();
					v.add(new Label(i+""));
					v.add(new Label(MainFrame.fileinfoVector.get(i).file.getName()));
					v.add(new Label(MainFrame.fileinfoVector.get(i).file.length()/1024+" KB"));
					v.add(new Label((int)MainFrame.fileinfoVector.get(i).prodrass+""));
					v.add(new Label(MainFrame.fileinfoVector.get(i).fileStatus));
					labels.add(v);
				}
			}
		}
		
		JScrollPane jsp = new JScrollPane();
		jsp.setViewportView(jp);
		
		this.addFile(jp,23);
		this.add(jsp,BorderLayout.CENTER);
	}
	
	public void addFile(JPanel panel,int progress)
	{
		if(MainFrame.fileinfoVector != null)
		{
			if(!MainFrame.isUpload)
			{
				for(int i=0;i<MainFrame.fileinfoVector.size();i++)
				{
					JPanel j = new JPanel();
					j.setBorder(BorderFactory.createEtchedBorder());
					j.setBackground(Color.white);
					
					j.setLayout(new GridLayout(1,5));
					
					j.add(FileTablePanel.labels.get(i).get(0));
					j.add(FileTablePanel.labels.get(i).get(1));
					j.add(FileTablePanel.labels.get(i).get(2));
					j.add(FileTablePanel.labels.get(i).get(3));
					j.add(FileTablePanel.labels.get(i).get(4));
					
					panel.add(j);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!MainFrame.isUpload)
		{
			if(e.getActionCommand().equals("选择文件"))
			{
				System.out.println("ok");
				//打开文件选择器
				new ChooseFileWindow();
			}
			if(e.getActionCommand().equals("开始上传"))
			{
				if(MainFrame.fileinfoVector != null)
				{
					if(MainFrame.fileinfoVector.size() != 0)
					{
						System.out.println(MainFrame.fileinfoVector.size());
						MainFrame.isUpload = true;
						
						new UploadThread();
					}
				}
			}
		}
	}
}
