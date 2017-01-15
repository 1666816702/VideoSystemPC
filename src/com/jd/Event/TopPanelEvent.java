package com.jd.Event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.jd.window.ChooseFileWindow;

public class TopPanelEvent implements ActionListener{
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("选择文件"))
		{
			//打开文件选择对话框
			ChooseFileWindow cfw = new ChooseFileWindow();
		}
		if(e.getActionCommand().equals("开始上传"))
		{
			UploadThread ut = new UploadThread();
		}
	}
}
