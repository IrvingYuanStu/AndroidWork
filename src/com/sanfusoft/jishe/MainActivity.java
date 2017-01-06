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
	Boolean flag = true;//�������ƿ�����ʾ�ı�־λ

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.activity_main);

		//StrictModeһ���Ͻ��Ĳ���ģʽ��
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
		
		//����ʼ���Ӱ�ť����һ����������StartClickListener
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

		String mess = decode("01");// ��ʮ��������ת�����ַ���

		@Override
		public void onClick(View v) {

			try {
				flag = !flag;
				if (flag == false) {

					kaideng1.setText("1�ŵƹ�");
				} else
					kaideng1.setText("1�ŵƿ�");
				mPrintWriterClient.print(mess);// ���͸�������
				mPrintWriterClient.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(MainActivity.this, "�������ӷ�����", 2000).show();
				e.printStackTrace();
			}

		}
	};
	private OnClickListener kaideng2Listener = new OnClickListener() {

		String mess = decode("02");// ��ʮ��������ת�����ַ���

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				flag = !flag;
				if (flag == false) {

					kaideng2.setText("2�ŵƹ�");
				} else
					kaideng2.setText("2�ŵƿ�");
				mPrintWriterClient.print(mess);// ���͸�������
				mPrintWriterClient.flush();
			} catch (Exception e) {
				Toast.makeText(MainActivity.this, "�������ӷ�����", 2000).show();
				e.printStackTrace();
			}

		}
	};
	private OnClickListener kaideng3Listener = new OnClickListener() {

		String mess = decode("03");// ��ʮ��������ת�����ַ���

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				flag = !flag;
				if (flag == false) {

					kaideng3.setText("3�ŵƹ�");
				} else
					kaideng3.setText("3�ŵƿ�");
				mPrintWriterClient.print(mess);// ���͸�������
				mPrintWriterClient.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(MainActivity.this, "�������ӷ�����", 2000).show();
				e.printStackTrace();
			}

		}
	};
	private OnClickListener kaideng4Listener = new OnClickListener() {

		String mess = decode("04");// ��ʮ��������ת�����ַ���

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				flag = !flag;
				if (flag == false) {

					kaideng4.setText("4�ŵƹ�");
				} else
					kaideng4.setText("4�ŵƿ�");
				mPrintWriterClient.print(mess);// ���͸�������
				mPrintWriterClient.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(MainActivity.this, "�������ӷ�����", 2000).show();
				e.printStackTrace();
			}

		}
	};
	private OnClickListener fengshanListener = new OnClickListener() {

		String mess = decode("05");// ��ʮ��������ת�����ַ���

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				flag = !flag;
				if (flag == false) {

					fengshan.setText("���ȹ�");
				} else
					fengshan.setText("���ȿ�");
				mPrintWriterClient.print(mess);// ���͸�������
				mPrintWriterClient.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(MainActivity.this, "�������ӷ�����", 2000).show();
				e.printStackTrace();
			}

		}
	};
	
	// �������Ӱ�ť��ע���¼�
	private OnClickListener StartClickListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			//�ر�����
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

				startButton.setText("��ʼ����");
				IPText.setEnabled(true);
				recvText.setText("��Ϣ:\n");
			} else {
				//��������
				isConnecting = true;
				startButton.setText("ֹͣ����");
				IPText.setEnabled(false);
				//mThreadClient��һ���߳��࣬�ֻ�ʵ���ǿͻ���
				mThreadClient = new Thread(mRunnable);
				mThreadClient.start();
			}
		}
	};

	// mThreadClient�̵߳�runnableʵ�ֽӿ�
	private Runnable mRunnable = new Runnable() {
		public void run() {
			//��ȡ�����ip��ַ
			String msgText = IPText.getText().toString();
			if (msgText.length() <= 0) {
				recvMessageClient = "IP����Ϊ�գ�\n";// ��Ϣ����
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
				return;
			}
			int start = msgText.indexOf(":");
			if ((start == -1) || (start + 1 >= msgText.length())) {
				recvMessageClient = "IP��ַ���Ϸ�\n";// ��Ϣ����
				Message msg = new Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
				return;
			}
			//���涼��ip��ַ�Ϸ����ж�
			
			//��ȡ�����server��ip��server�Ķ˿�
			String sIP = msgText.substring(0, start);
			String sPort = msgText.substring(start + 1);
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
						Log.d("hahah", recvMessageClient);

					}
				} catch (Exception e) {
					recvMessageClient = "�����쳣:" + e.getMessage() + "\n";// ��Ϣ����
					Message msg = new Message();
					msg.what = 1;
					mHandler.sendMessage(msg);
				}
			}
		}//run
	};

	Handler mHandler = new Handler() {

		//�����ʹ����յ�����Ϣ(recvMessageServer)
		//��д��������������յ���Ϣ
		public void handleMessage(Message msg) {

			// recvMessageClient���յ�������
			super.handleMessage(msg);
			if (msg.what == 0) {
				recvText.append("Server: " + recvMessageServer); // ˢ��
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
	 * 16���������ַ���
	 */
	private static String hexString = "0123456789ABCDEF";
	private TextView wenduText;
	private TextView shiduText;
	private ProgressBar wenduBar;
	private ProgressBar shiduBar;

	private static String buff[];

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
}