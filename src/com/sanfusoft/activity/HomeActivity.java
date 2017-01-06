package com.sanfusoft.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.sanfusoft.fragment.DataFragment;
import com.sanfusoft.fragment.HandFragment;
import com.sanfusoft.fragment.HistoryFragment;
import com.sanfusoft.fragment.SettingFragment;
import com.sanfusoft.jishe.R;

//����ϵͳ����ʾ��������,ʹ��fragment��ͼ
public class HomeActivity extends FragmentActivity {
	
	private DataFragment dataFragment = null;
	private HandFragment handFragment = null;
	private SettingFragment settingFragment = null;
	private HistoryFragment historyFragment = null;
	
	private String str="���������ð�";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		//������ͷ��actionbar
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		//����Fragment�������õ�iflate����
		dataFragment = new DataFragment();
		
		//��ȡ�����࣬��ʼת������dataFragment�滻��Ӧid�Ĳ��֡�
		//ע�⣬���Ҫ�ύ
		getSupportFragmentManager().beginTransaction().replace(R.id.home_content, dataFragment).commit();
		
		//��ȡ��radioGroup����
		RadioGroup tabRg = (RadioGroup) findViewById(R.id.tab_menu);
		
		//���ü�����
		tabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			//����������
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				//��֧�ж�
				switch (checkedId) {
				
				case R.id.data_radio:
						dataFragment = new DataFragment();
						getSupportFragmentManager().beginTransaction().replace(R.id.home_content, dataFragment).commit();
					break;
					
				case R.id.hand_radio:
						handFragment = new HandFragment();
						getSupportFragmentManager().beginTransaction().replace(R.id.home_content, handFragment).commit();
					break;
					
				case R.id.setting_radio:
						settingFragment = new SettingFragment();
						getSupportFragmentManager().beginTransaction().replace(R.id.home_content, settingFragment).commit();
					break;
					
				case R.id.history_radio:
						historyFragment= new HistoryFragment();
						getSupportFragmentManager().beginTransaction().replace(R.id.home_content, historyFragment).commit();
					break;
					
				default:
					break;
				}//switch�ṹ
				
			}//������
		});//���ü�����
		
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
	
}
