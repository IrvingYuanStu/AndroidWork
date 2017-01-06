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
	Boolean flag = true;// 用来控制开关显示的标志位

	public static String wendu;
	public static String shidu;
	
	//传入ip
	private String ip;
	
	public NetUtil(String ip) {
		
		this.ip = ip;
	}

	// 关闭连接的方法
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
	
	
	//开启连接
	public void openConnect(){
		
		isConnecting = true;
		//mThreadClient是一个线程类，手机实际是客户端
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
	 * 内部类mRunnable
	 */
	private Runnable mRunnable = new Runnable() {
		
		public void run() {
			
			//ip地址合法性判断
			if (ip.length() <= 0) {
				recvMessageClient = "IP不能为空！\n";// 消息换行
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
				return;
			}
			int start = ip.indexOf(":");
			if ((start == -1) || (start + 1 >= ip.length())) {
				recvMessageClient = "IP地址不合法\n";// 消息换行
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
				return;
			}
			
			//获取输入的server的ip和server的端口
			String sIP = ip.substring(0, start);
			String sPort = ip.substring(start + 1);
			int port = Integer.parseInt(sPort);

			Log.d("gjz", "IP:" + sIP + ":" + port);

			try {
				// 连接服务器
				mSocketClient = new Socket(sIP, port); // portnum
				// 取得输入、输出流
				mBufferedReaderClient = new BufferedReader(
						new InputStreamReader(mSocketClient.getInputStream()));

				mPrintWriterClient = new PrintWriter(
						mSocketClient.getOutputStream(), true);

				recvMessageClient = "已经连接server!\n";// 消息换行
				//message是一个Android封装好的用于网络连接的信息存储类。
				Message msg = new Message();
				//what属性，用于区分是那个handler的msg。避免冲突
				msg.what = 1;
				//sendmessage方法会将一个msg放到队列的尾端，被handlemessage方法接活
				mHandler.sendMessage(msg);
				// break;
			} catch (Exception e) {
				recvMessageClient = "连接IP异常:" + e.toString() + e.getMessage()
						+ "\n";// 消息换行
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
				return;
			}

			//buffer缓冲区，读取server发送来的数据
			char[] buffer = new char[256];
			int count = 0;
			while (isConnecting) {
				try {
					// if ( (recvMessageClient =
					// mBufferedReaderClient.readLine()) != null )
					if ((count = mBufferedReaderClient.read(buffer)) > 0) {
						recvMessageClient = getInfoBuff(buffer, count) + "\n";// 消息换行
						Message msg = new Message();
						msg.what = 1;
						msg.obj = recvMessageClient;
						//sendMessage的msg会自动被handlemessage处理
						//这是Android封装好的类
						mHandler.sendMessage(msg);
//						Log.d("hahah", recvMessageClient);

					}
				} catch (Exception e) {
					recvMessageClient = "接收异常:" + e.getMessage() + "\n";// 消息换行
					Message msg = new Message();
					msg.what = 1;
					mHandler.sendMessage(msg);
				}
			}
		}
		
	};//内部类mRunnable
	
	
	
	/**
	 * 内部类mHandler
	 */
	private Handler mHandler = new Handler(){
		
		//重写这个方法来收获信息
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0) {
//				recvText.append("Server: " + recvMessageServer); // 刷新
			} else if (msg.what == 1) {
				try {
//						Log.d("WTF", recvMessageClient); 现在可以接收到，担心温度湿度赋值失败
					String recwendu = recvMessageClient.substring(0, 2);
					wendu = recwendu;//内部类修改外部类的值
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
	 * 以下是生成的setter和getter方法
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
