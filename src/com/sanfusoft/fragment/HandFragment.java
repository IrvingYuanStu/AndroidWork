package com.sanfusoft.fragment;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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
import android.widget.Toast;

import com.sanfusoft.jishe.R;
import com.sanfusoft.util.DataUtil;

public class HandFragment extends Fragment{
	
	private Button but1;
	private Button but2;
	
	
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
	private String ip;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragmenthand, null);
		
		
		but1 = (Button) view.findViewById(R.id.lightbut1);
		but2 = (Button) view.findViewById(R.id.lightbut2);
		
		ip = "192.168.16.254:8080";
		
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

		} else {
			isConnecting = true;

			mThreadClient = new Thread(mRunnable);
			mThreadClient.start();
		}
		
		
		but1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String mess = DataUtil.decode("01");
				Log.d("AAAA", mess);
				
				try {
					flag = !flag;
					if (flag == false) {
	
						but1.setText("�ѹر�");
					} else{
						but1.setText("�Ѵ�");
					}
					
					mPrintWriterClient.print(mess);// ���͸�������
					mPrintWriterClient.flush();
					
				} catch (Exception e) {
					Toast.makeText(getActivity(), "�������ӷ�����", 2000).show();
					e.printStackTrace();
				}
				
			}
		});
		
		
		but2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				String mess = DataUtil.decode("02");
				Log.d("BBB", mess);
				
				try {
					flag = !flag;
					if (flag == false) {
	
						but2.setText("�ѹر�");
					} else{
						but2.setText("�Ѵ�");
					}
				mPrintWriterClient.print(mess);// ���͸�������
				mPrintWriterClient.flush();
					
				} catch (Exception e) {
					Toast.makeText(getActivity(), "�������ӷ�����", 2000).show();
					e.printStackTrace();
				}
				
			}
		});
		
		
		return view;
		
	}
	
	
	//��������
	public void openConnect(){
		
		isConnecting = true;
		//mThreadClient��һ���߳��࣬�ֻ�ʵ���ǿͻ���
		mThreadClient = new Thread(mRunnable);
		mThreadClient.start();
	}
	
	//��ȡ����������
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
						//�л�fragmentʱ���ͱ���

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
		private String str;
		
		//��д����������ջ���Ϣ
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0) {
//				recvText.append("Server: " + recvMessageServer); // ˢ��
			} else if (msg.what == 1) {
				try {
					//�������ݵľ���������������������
//					Log.d("WTF", recvMessageClient); 
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}//handle message
		
		
		
	};
	


	
}
