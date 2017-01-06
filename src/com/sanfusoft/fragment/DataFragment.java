package com.sanfusoft.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.sanfusoft.jishe.R;
import com.sanfusoft.util.DataBaseUtil;
import com.sanfusoft.util.MySQLiteOpenHelper;

import entity.ItemData;

//一定要继承fragmentActivity，不然Activity的属性都不能用
public class DataFragment extends Fragment{
	
	//list用来存sprinner的值
	private List<String> list = new ArrayList<String>();
	private Spinner spinner ;
	private ArrayAdapter<String> adapter;
	
	private TextView wenduText;
	private TextView shiduText;
	private TextView lightText;
	
	boolean isFirst = true;
	
	DataBaseUtil dataBaseUtil = new DataBaseUtil();
	ItemData itemData = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	//动态的改变创建view的视图
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getActivity(), "irvingDB", null, 1);
		dataBaseUtil.setMySQLiteOpenHelper(mySQLiteOpenHelper);		
		
		list.add("1号");
		list.add("2号");
		View mview = inflater.inflate(R.layout.fragmentdata,null);
		
		wenduText = (TextView) mview.findViewById(R.id.wenduText);
		shiduText = (TextView) mview.findViewById(R.id.shiduText);
		lightText = (TextView) mview.findViewById(R.id.lightText);
		
		spinner = (Spinner) mview.findViewById(R.id.sprinner_id);
		//给spiiner设置对应的值
		adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item , list);
		
		spinner.setAdapter(adapter);
		
		//spinner注册每一项的点击事件
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				switch (position) {
				case 0:
					//处理第一次默认选中第一项的问题
					if(isFirst){
						isFirst=false;
					}else{
//						Log.d("test", dataBaseUtil.findData());
//						Toast.makeText(getActivity(), dataBaseUtil.findData(), 0).show();
						itemData = dataBaseUtil.findData();
						System.out.println("XXXXXX"+itemData.getLight());
						wenduText.setText(itemData.getWendu());
						shiduText.setText(itemData.getShidu());
						lightText.setText(itemData.getLight());
					}
					break;
					
				case 1:
					itemData = dataBaseUtil.findDataSec();
					wenduText.setText(itemData.getWendu());
					shiduText.setText(itemData.getShidu());
					lightText.setText(itemData.getLight());
					break;
					
				default:
					break;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		return mview;
	}
	

}
