package com.sanfusoft.util;

import java.io.ByteArrayOutputStream;

import entity.ItemData;

/**
 * �����շ���ʮ���ƺ�ʮ������ת��
 * @author ���
 *
 */
public class DataUtil {
	
	private static String hexString = "0123456789ABCDEF";
	
	/*
	 * ���ַ��������16��������,�����������ַ����������ģ�
	 */
	public static String encode(String str) {
		// ����Ĭ�ϱ����ȡ�ֽ�����
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// ���ֽ�������ÿ���ֽڲ���2λ16��������
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/*
	 * ��16�������ֽ�����ַ���,�����������ַ����������ģ�
	 */
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// ��ÿ2λ16����������װ��һ���ֽ�
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}
	
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}
	
	public ItemData formatData(String recv){
		
		ItemData itemData = new ItemData();
		
		itemData.setLight(recv.substring(recv.indexOf("g")+1, recv.indexOf("g")+3));
		
		itemData.setWendu(recv.substring(recv.indexOf("w")+1, recv.indexOf("w")+3));
		
		itemData.setShidu(recv.substring(recv.indexOf("sd")+2, recv.indexOf("sd")+4));
		
		int dotNum = Integer.parseInt(recv.substring(recv.indexOf("id")+2,recv.indexOf("id")+3));
		
		itemData.setDotNum(dotNum);
		
//		System.out.println(itemData.getLight()+"|"+itemData.getShidu()+"idΪ��"+itemData.getDotNum());
		
		return itemData;
		
	}
	
	
}
