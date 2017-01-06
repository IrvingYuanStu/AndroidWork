package com.sanfusoft.jishe;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */

	private Button startButton;
	private EditText IPText;
	private Button CreateButton;
	private TextView recvText;
	private Button kaideng1;
	private Button kaideng2;
	private Button kaideng3;
	private Button kaideng4;
	private Button fengshan;

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
	Boolean flag = true;//用来控制开关显示的标志位

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_main);

		//StrictMode一种严谨的测试模式类
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork() // or
																		// .detectAll()
																		// for
																		// all
																		// detectable
																		// problems
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());

		IPText = (EditText) findViewById(R.id.IPText);
		// IPText.setText("10.0.2.15:");
		IPText.setText("192.168.16.254:8080");
		startButton = (Button) findViewById(R.id.StartConnect);
		
		//给开始连接按钮设置一个监听器，StartClickListener
		startButton.setOnClickListener(StartClickListener);

		recvText = (TextView) findViewById(R.id.RecvText);
		recvText.setMovementMethod(ScrollingMovementMethod.getInstance());

		kaideng1 = (Button) findViewById(R.id.kaideng1);
		kaideng1.setOnClickListener(kaideng1Listener);

		kaideng2 = (Button) findViewById(R.id.kaideng2);
		kaideng2.setOnClickListener(kaideng2Listener);

		kaideng3 = (Button) findViewById(R.id.kaideng3);
		kaideng3.setOnClickListener(kaideng3Listener);

		kaideng4 = (Button) findViewById(R.id.kaideng4);
		kaideng4.setOnClickListener(kaideng4Listener);

		fengshan = (Button) findViewById(R.id.fengshan);
		fengshan.setOnClickListener(fengshanListener);

		wenduText = (TextView) findViewById(R.id.wendu);
		shiduText = (TextView) findViewById(R.id.shidu);
		wenduBar = (ProgressBar) findViewById(R.id.wenduBar);
		shiduBar = (ProgressBar) findViewById(R.id.shiduBar);

	}

	private OnClickListener kaideng1Listener = new OnClickListener() {

		String mess = decode("01");// 将十六进制数转化成字符串

		@Override
		public void onClick(View v) {

			try {
				flag = !flag;
				if (flag == false) {

					kaideng1.setText("1号灯关");
				} else
					kaideng1.setText("1号灯开");
				mPrintWriterClient.print(mess);// 发送给服务器
				mPrintWriterClient.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(MainActivity.this, "请先连接服务器", 2000).show();
				e.printStackTrace();
			}

		}
	};
	private OnClickListener kaideng2Listener = new OnClickListener() {

		String mess = decode("02");// 将十六进制数转化成字符串

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				flag = !flag;
				if (flag == false) {

					kaideng2.setText("2号灯关");
				} else
					kaideng2.setText("2号灯开");
				mPrintWriterClient.print(mess);// 发送给服务器
				mPrintWriterClient.flush();
			} catch (Exception e) {
				Toast.makeText(MainActivity.this, "请先连接服务器", 2000).show();
				e.printStackTrace();
			}

		}
	};
	private OnClickListener kaideng3Listener = new OnClickListener() {

		String mess = decode("03");// 将十六进制数转化成字符串

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				flag = !flag;
				if (flag == false) {

					kaideng3.setText("3号灯关");
				} else
					kaideng3.setText("3号灯开");
				mPrintWriterClient.print(mess);// 发送给服务器
				mPrintWriterClient.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(MainActivity.this, "请先连接服务器", 2000).show();
				e.printStackTrace();
			}

		}
	};
	private OnClickListener kaideng4Listener = new OnClickListener() {

		String mess = decode("04");// 将十六进制数转化成字符串

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				flag = !flag;
				if (flag == false) {

					kaideng4.setText("4号灯关");
				} else
					kaideng4.setText("4号灯开");
				mPrintWriterClient.print(mess);// 发送给服务器
				mPrintWriterClient.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(MainActivity.this, "请先连接服务器", 2000).show();
				e.printStackTrace();
			}

		}
	};
	private OnClickListener fengshanListener = new OnClickListener() {

		String mess = decode("05");// 将十六进制数转化成字符串

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				flag = !flag;
				if (flag == false) {

					fengshan.setText("风扇关");
				} else
					fengshan.setText("风扇开");
				mPrintWriterClient.print(mess);// 发送给服务器
				mPrintWriterClient.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(MainActivity.this, "请先连接服务器", 2000).show();
				e.printStackTrace();
			}

		}
	};
	
	// 网络连接按钮的注册事件
	private OnClickListener StartClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			//关闭连接
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

				startButton.setText("开始连接");
				IPText.setEnabled(true);
				recvText.setText("信息:\n");
			} else {
				//建立连接
				isConnecting = true;
				startButton.setText("停止连接");
				IPText.setEnabled(false);
				//mThreadClient是一个线程类，手机实际是客户端
				mThreadClient = new Thread(mRunnable);
				mThreadClient.start();
			}
		}
	};

	// mThreadClient线程的runnable实现接口
	private Runnable mRunnable = new Runnable() {
		public void run() {
			//获取输入的ip地址
			String msgText = IPText.getText().toString();
			if (msgText.length() <= 0) {
				recvMessageClient = "IP不能为空！\n";// 消息换行
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
				return;
			}
			int start = msgText.indexOf(":");
			if ((start == -1) || (start + 1 >= msgText.length())) {
				recvMessageClient = "IP地址不合法\n";// 消息换行
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
				return;
			}
			//上面都是ip地址合法性判断
			
			//获取输入的server的ip和server的端口
			String sIP = msgText.substring(0, start);
			String sPort = msgText.substring(start + 1);
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
						Log.d("hahah", recvMessageClient);

					}
				} catch (Exception e) {
					recvMessageClient = "接收异常:" + e.getMessage() + "\n";// 消息换行
					Message msg = new Message();
					msg.what = 1;
					mHandler.sendMessage(msg);
				}
			}
		}//run
	};

	Handler mHandler = new Handler() {

		//解析和处理收到的信息(recvMessageServer)
		//重写这个方法，才能收到消息
		public void handleMessage(Message msg) {

			// recvMessageClient接收到的数据
			super.handleMessage(msg);
			if (msg.what == 0) {
				recvText.append("Server: " + recvMessageServer); // 刷新
			} else if (msg.what == 1) {
				Toast.makeText(MainActivity.this, recvMessageClient,2000).show();

				try {
					String recwendu = recvMessageClient.substring(0, 2);
					wenduText.setText(recwendu);
					wenduBar.setProgress(Integer.parseInt(recwendu));
					String recshidu = recvMessageClient.substring(3, 4);
					shiduText.setText(recshidu);
					shiduBar.setProgress(Integer.parseInt(recshidu));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	};

	public void writeData(String mess) {
		buff = mess.split("\\|");
		wenduText.setText(buff[1]);
		wenduBar.setProgress(Integer.parseInt(buff[1]));
		shiduText.setText(buff[3]);
		shiduBar.setProgress(Integer.parseInt(buff[3]));

	}

	private String getInfoBuff(char[] buff, int count) {
		char[] temp = new char[count];
		for (int i = 0; i < count; i++) {
			temp[i] = buff[i];
		}
		return new String(temp);
	}

	public void onDestroy() {
		super.onDestroy();
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

	/*
	 * 16进制数字字符集
	 */
	private static String hexString = "0123456789ABCDEF";
	private TextView wenduText;
	private TextView shiduText;
	private ProgressBar wenduBar;
	private ProgressBar shiduBar;

	private static String buff[];

	/*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String encode(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/*
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}
}