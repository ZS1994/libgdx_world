package com.mygdx.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ͨ���������������ࣨĿ���ǲ��ظ���
 * @author ��˳
 * <br>2016��9��2��11:30:45
 */
public class NameOfDate {
	public String getDir() {
		return new SimpleDateFormat("yyyy-MM").format(new Date());
	}
	public static String getFileName() {
		return new SimpleDateFormat("ddHHmmssSSS").format(new Date());
	}
	
	/**
	 * �����������������ɲ��ظ�������
	 * @return
	 */
	public static String getNum() {
		return getFileName()+(int)(Math.random()*9000+1000);
	}
}
