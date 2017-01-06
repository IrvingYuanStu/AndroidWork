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

//进入系统后显示的主界面,使用fragment视图
public class HomeActivity extends FragmentActivity {
	
	private DataFragment dataFragment = null;
	private HandFragment handFragment = null;
	private SettingFragment settingFragment = null;
	private HistoryFragment historyFragment = null;
	
	private String str="明明可以用啊";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		//隐藏题头的actionbar
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		
		//创建Fragment对象，利用的iflate方法
		dataFragment = new DataFragment();
		
		//获取管理类，开始转换，用dataFragment替换对应id的部分。
		//注意，最后要提交
		getSupportFragmentManager().beginTransaction().replace(R.id.home_content, dataFragment).commit();
		
		//获取到radioGroup对象
		RadioGroup tabRg = (RadioGroup) findViewById(R.id.tab_menu);
		
		//设置监听器
		tabRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			//创建监听器
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				//分支判断
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
				}//switch结构
				
			}//监听器
		});//配置监听器
		
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
	
}
