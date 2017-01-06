package com.sanfusoft.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import entity.ItemData;

public class DataBaseUtil {
	
	private MySQLiteOpenHelper mySQLiteOpenHelper;
	
	public void insert(String str){
		//获取到可写的数据库
		SQLiteDatabase writableDatabase = mySQLiteOpenHelper.getReadableDatabase();
		if(writableDatabase.isOpen()){
			writableDatabase.execSQL("insert into t_datas(content) values(?)", new Object[]{str});
			writableDatabase.close();
		}
		
	}
	
	//方法的重载
	public void insert(ItemData itemData){
		//获取到可写的数据库
		SQLiteDatabase writableDatabase = mySQLiteOpenHelper.getReadableDatabase();
		if(writableDatabase.isOpen()){
			writableDatabase.execSQL("insert into t_datas(dotNum, wendu, shidu, light) values(?,?,?,?)", new Object[]{
					itemData.getDotNum(),
					itemData.getWendu(),
					itemData.getShidu(),
					itemData.getLight()
					});
			writableDatabase.close();
		}
		
	}
	
	public ItemData findData(){
		
		SQLiteDatabase readbleDatabase = mySQLiteOpenHelper.getReadableDatabase();
		
		ItemData itemData= new ItemData();
		
		if(readbleDatabase.isOpen()){
			
			//同过游标查找数据
			Cursor cursor = readbleDatabase.rawQuery("select wendu, shidu, light from t_datas where dotNum=1 order by id desc limit 1", new String[]{});
			
			if(cursor!=null && cursor.getCount()>0){
				
				while(cursor.moveToNext()){
					itemData.setWendu(cursor.getString(0)); 
					itemData.setShidu(cursor.getString(1));
					itemData.setLight(cursor.getString(2));
				}
			}
			
			readbleDatabase.close();
		}
		
		return itemData;
	}

	
	public ItemData findDataSec(){
		
		SQLiteDatabase readbleDatabase = mySQLiteOpenHelper.getReadableDatabase();
		
		ItemData itemData= new ItemData();
		
		if(readbleDatabase.isOpen()){
			
			//同过游标查找数据
			Cursor cursor = readbleDatabase.rawQuery("select wendu, shidu, light from t_datas where dotNum=2 order by id desc limit 1", new String[]{});
			
			if(cursor!=null && cursor.getCount()>0){
				
				while(cursor.moveToNext()){
					itemData.setWendu(cursor.getString(0)); 
					itemData.setShidu(cursor.getString(1));
					itemData.setLight(cursor.getString(2));
				}
			}
			
			readbleDatabase.close();
		}
		
		return itemData;
	}
	
	
	public MySQLiteOpenHelper getMySQLiteOpenHelper() {
		return mySQLiteOpenHelper;
	}

	public void setMySQLiteOpenHelper(MySQLiteOpenHelper mySQLiteOpenHelper) {
		this.mySQLiteOpenHelper = mySQLiteOpenHelper;
	}
	
	

}
