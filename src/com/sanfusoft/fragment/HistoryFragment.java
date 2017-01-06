package com.sanfusoft.fragment;

import com.sanfusoft.jishe.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class HistoryFragment extends Fragment {
	
	private ListView historyList;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragmenthistory, null);
		
		historyList = (ListView) view.findViewById(R.id.historyList);
		
		historyList.setAdapter(new ListViewAdapter());
		
		return view;
		
	}

	class ListViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return 13;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View view = convertView;
			
			//判断是否需要填充
			if(convertView == null){
				//如果没有，就生成一个，这是第一个view
				view = getActivity().getLayoutInflater().inflate(R.layout.historylist, null);
			}
			
			return view;
		}}
}
