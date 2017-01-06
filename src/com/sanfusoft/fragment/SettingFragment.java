package com.sanfusoft.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sanfusoft.jishe.R;
import com.sanfusoft.util.DataBaseUtil;
import com.sanfusoft.util.DataUtil;
import com.sanfusoft.util.MySQLiteOpenHelper;
import com.sanfusoft.util.NetUtil;

import entity.ItemData;

public class SettingFragment extends Fragment{
	
	//fragment需要的属性
	private EditText ipText;
	private Button connectBut;
	private NetUtil netUtil;
	private DataUtil dataUtil;
	ItemData itemData = null;
	
	private TextView transText;
	
	//网络连接功能需要的属性
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
	private String ip;
	
//	MySQLiteOpenHelper mySQLiteOpenHelper = null;
	DataBaseUtil dataBaseUtil = new DataBaseUtil();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragmentsetting, null);
		
		//获取到editText和button对象
		ipText = (EditText)view.findViewById(R.id.editIpText);
		Button connectBut = (Button)view.findViewById(R.id.connectbut);
		Button disconnectBut = (Button) view.findViewById(R.id.disconnectbut);
		transText = (TextView) view.findViewById(R.id.transWendu);
		
		MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getActivity(), "irvingDB", null, 1);
		dataBaseUtil.setMySQLiteOpenHelper(mySQLiteOpenHelper);
		
		//视图线程会先执行，所以，在oncreateView时，recvclient还为null，不能传递参数。
		connectBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ipText.setText("192.168.16.254:8080");
				Toast.makeText(getActivity(), "连接成功", 0).show();
				ip = ipText.getText().toString();
				openConnect();
				
				
			}
		});
		
		//关闭连接
		disconnectBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				closeConnect();
			}
		});
		
		
		return view;
	}
	
	
	
	//自己添加的netutil里的方法
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
	
	//读取缓冲期数据
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
						//切换fragment时，就报错

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
		private String str;
		
		//重写这个方法来收获信息
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0) {
//				recvText.append("Server: " + recvMessageServer); // 刷新
			} else if (msg.what == 1) {
				try {
					//进行数据的具体操作，或者在上面进行
					Log.d("WTF", recvMessageClient); 
					Log.d("wtf", recvMessageClient.length()+"");
					if(recvMessageClient.length()==14){
						Log.d("wtf", "这时候才装吧");
						dataUtil = new DataUtil();
						itemData = dataUtil.formatData(recvMessageClient);
						//存储数据
						dataBaseUtil.insert(itemData);
						
					}
//					String recwendu = recvMessageClient.substring(0, 2);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}//handle message
		
		
		
	};
	

}
