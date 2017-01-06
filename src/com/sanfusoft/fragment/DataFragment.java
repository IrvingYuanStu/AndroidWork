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

//һ��Ҫ�̳�fragmentActivity����ȻActivity�����Զ�������
public class DataFragment extends Fragment{
	
	//list������sprinner��ֵ
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
	
	//��̬�ĸı䴴��view����ͼ
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		MySQLiteOpenHelper mySQLiteOpenHelper = new MySQLiteOpenHelper(getActivity(), "irvingDB", null, 1);
		dataBaseUtil.setMySQLiteOpenHelper(mySQLiteOpenHelper);		
		
		list.add("1��");
		list.add("2��");
		View mview = inflater.inflate(R.layout.fragmentdata,null);
		
		wenduText = (TextView) mview.findViewById(R.id.wenduText);
		shiduText = (TextView) mview.findViewById(R.id.shiduText);
		lightText = (TextView) mview.findViewById(R.id.lightText);
		
		spinner = (Spinner) mview.findViewById(R.id.sprinner_id);
		//��spiiner���ö�Ӧ��ֵ
		adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item , list);
		
		spinner.setAdapter(adapter);
		
		//spinnerע��ÿһ��ĵ���¼�
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
				switch (position) {
				case 0:
					//�����һ��Ĭ��ѡ�е�һ�������
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
