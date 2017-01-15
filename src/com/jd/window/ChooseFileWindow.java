package com.jd.window;

import java.io.File;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jd.model.FileInfo;

public class ChooseFileWindow extends JFileChooser{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChooseFileWindow()
	{
		//设置可多选文件
		this.setMultiSelectionEnabled(true);
		//设置文件格式
		this.setFileFilter(new FileNameExtensionFilter("文件格式","mp4","flv"));
		
		this.showOpenDialog(this);
	}
	
	/*
	 * 重写选择文件函数
	 * 
	 */
	public void approveSelection()
	{
		File[] files = this.getSelectedFiles();
		
		Vector<FileInfo> fileinfoVector = new Vector<FileInfo>();
		
		for(int i=0;i<files.length;i++)
		{
			FileInfo fileinfo = new FileInfo();
			fileinfo.file = files[i];
			fileinfo.fileStatus = "�ȴ��ϴ�";
			fileinfo.prodrass = 0;
			
			fileinfoVector.add(fileinfo);
		}
		
		MainFrame.fileinfoVector = fileinfoVector;
		MainFrame.mainPanel.ftp.init();
		MainFrame.mainPanel.validate();
		
		super.cancelSelection();
	}
}
