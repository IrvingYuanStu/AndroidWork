package com.sanfusoft.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class NetUtil {

	private boolean isConnecting = false;

	private Thread mThreadClient = null;
	private Thread mThreadServer = null;
	private Socket mSocketServer = null;
	private Socket mSocketClient = null;
	static BufferedReader mBufferedReaderServer = null;
	static PrintWriter mPrintWriterServer = null;
	static BufferedReader mBufferedReaderClient = null;
	static PrintWriter mPrintWriterClient = null;
	private String recvMessageClient = "";
	private String recvMessageServer = "";
	Boolean flag = true;// �������ƿ�����ʾ�ı�־λ

	public static String wendu;
	public static String shidu;
	
	//����ip
	private String ip;
	
	public NetUtil(String ip) {
		
		this.ip = ip;
	}

	// �ر����ӵķ���
	public void closeConnect(){
		
		if (isConnecting) {
			
			isConnecting = false;
			try {
				if (mSocketClient != null) {
					mSocketClient.close();
					mSocketClient = null;

					mPrintWriterClient.close();
					mPrintWriterClient = null;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mThreadClient.interrupt();
		}
	}
	
	
	//��������
	public void openConnect(){
		
		isConnecting = true;
		//mThreadClient��һ���߳��࣬�ֻ�ʵ���ǿͻ���
		mThreadClient = new Thread(mRunnable);
		mThreadClient.start();
	}

	//
	private String getInfoBuff(char[] buff, int count) {
		char[] temp = new char[count];
		for (int i = 0; i < count; i++) {
			temp[i] = buff[i];
		}
		return new String(temp);
	}
	
	
	
	
	
	/**
	 * �ڲ���mRunnable
	 */
	private Runnable mRunnable = new Runnable() {
		
		public void run() {
			
			//ip��ַ�Ϸ����ж�
			if (ip.length() <= 0) {
				recvMessageClient = "IP����Ϊ�գ�\n";// ��Ϣ����
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
				return;
			}
			int start = ip.indexOf(":");
			if ((start == -1) || (start + 1 >= ip.length())) {
				recvMessageClient = "IP��ַ���Ϸ�\n";// ��Ϣ����
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
				return;
			}
			
			//��ȡ�����server��ip��server�Ķ˿�
			String sIP = ip.substring(0, start);
			String sPort = ip.substring(start + 1);
			int port = Integer.parseInt(sPort);

			Log.d("gjz", "IP:" + sIP + ":" + port);

			try {
				// ���ӷ�����
				mSocketClient = new Socket(sIP, port); // portnum
				// ȡ�����롢�����
				mBufferedReaderClient = new BufferedReader(
						new InputStreamReader(mSocketClient.getInputStream()));

				mPrintWriterClient = new PrintWriter(
						mSocketClient.getOutputStream(), true);

				recvMessageClient = "�Ѿ�����server!\n";// ��Ϣ����
				//message��һ��Android��װ�õ������������ӵ���Ϣ�洢�ࡣ
				Message msg = new Message();
				//what���ԣ������������Ǹ�handler��msg�������ͻ
				msg.what = 1;
				//sendmessage�����Ὣһ��msg�ŵ����е�β�ˣ���handlemessage�����ӻ�
				mHandler.sendMessage(msg);
				// break;
			} catch (Exception e) {
				recvMessageClient = "����IP�쳣:" + e.toString() + e.getMessage()
						+ "\n";// ��Ϣ����
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
				return;
			}

			//buffer����������ȡserver������������
			char[] buffer = new char[256];
			int count = 0;
			while (isConnecting) {
				try {
					// if ( (recvMessageClient =
					// mBufferedReaderClient.readLine()) != null )
					if ((count = mBufferedReaderClient.read(buffer)) > 0) {
						recvMessageClient = getInfoBuff(buffer, count) + "\n";// ��Ϣ����
						Message msg = new Message();
						msg.what = 1;
						msg.obj = recvMessageClient;
						//sendMessage��msg���Զ���handlemessage����
						//����Android��װ�õ���
						mHandler.sendMessage(msg);
//						Log.d("hahah", recvMessageClient);

					}
				} catch (Exception e) {
					recvMessageClient = "�����쳣:" + e.getMessage() + "\n";// ��Ϣ����
					Message msg = new Message();
					msg.what = 1;
					mHandler.sendMessage(msg);
				}
			}
		}
		
	};//�ڲ���mRunnable
	
	
	
	/**
	 * �ڲ���mHandler
	 */
	private Handler mHandler = new Handler(){
		
		//��д����������ջ���Ϣ
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0) {
//				recvText.append("Server: " + recvMessageServer); // ˢ��
			} else if (msg.what == 1) {
				try {
//						Log.d("WTF", recvMessageClient); ���ڿ��Խ��յ��������¶�ʪ�ȸ�ֵʧ��
					String recwendu = recvMessageClient.substring(0, 2);
					wendu = recwendu;//�ڲ����޸��ⲿ���ֵ
					String recshidu = recvMessageClient.substring(1, 2);
					shidu = recvMessageClient;
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	};
	
	
	
	
	
	
	
	
	/*********************************************************************************/
	/**
	 * ���������ɵ�setter��getter����
	 * 
	 * @return
	 *********************************************************************************/
	public String getWendu() {
		return wendu;
	}

	public void setWendu(String wendu) {
		this.wendu = wendu;
	}

	public String getShidu() {
		return shidu;
	}

	public void setShidu(String shidu) {
		this.shidu = shidu;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
