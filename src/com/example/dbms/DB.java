package com.example.dbms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DB {	
	
	private static final String Study = "Study.db"; //��Ʈw�W��
	private static final int version = 1; //��Ʈw����
	public static final String Student="student",Target="target",Study_stu="tar_stu_stu"; //Table name
	public static final String SNO="sno", Password="password", Start_time="start_time"; //Student item
	public static final String LNO="lno", MNO="mno", mapNO="mapno", M_URL="m_url", map_RUL="map_url"; //Target name
	private SQLiteDatabase sqlitedatabase; 
	private Context context;
	private DBHelper dbHelper;

	private static final String stu = //�ǥ͸�ƪ�
			"CREATE TABLE student ( sno INTEGER NOT NULL,password CHAR(15) NOT NULL,start_time DATETIME NOT NULL,PRIMARY KEY(sno));"; 
	private static final String tar = //�Ъ���ƪ�
			"CREATE TABLE target (lno INTEGER NOT NULL PRIMARY KEY,mno CHAR(15) NOT NULL, mapno CHAR(15) NOT NULL,m_url VARCHAR(50) NOT NULL,map_url VARCHAR(50) NOT NULL);";
	//�ǲ����Y��ƪ�
	private static final String sstudy = 
			"CREATE TABLE Study_stu(SNO INTEGER NOT NULL,LNO INTEGER NOT NULL,NNO INTEGER NOT NULL,solution CHAR(10),ans_time DATETIME,in_time DATETIME NOT NULL,out_time DATETIME,check_tar CHAR(10) NOT NULL,FOREIGN KEY (SNO) REFERENCES student,FOREIGN KEY (LNO) REFERENCES target,PRIMARY KEY(SNO,LNO));";

	private static final String select =
			"SELECT * FROM student ORDER BY sno";
	
	public DB openToWrite() throws android.database.SQLException{
		
		dbHelper = new DBHelper(context,Study,null,version);
		sqlitedatabase = dbHelper.getWritableDatabase();
		
		return this;
	}
	public  DB(Context c){
		context = c;
	}

	public DB openToRead() throws android.database.SQLException{ //�}�Ҹ�Ʈw��Ū
		
		dbHelper = new DBHelper(context,Study,null,version);
		sqlitedatabase = dbHelper.getReadableDatabase();
		
		return this;
	}
	
	public void close(){  //������Ʈw
		dbHelper.close();
	}
	
	public String All(){ 
		Cursor cursor = sqlitedatabase.rawQuery(select, null);
		String result = "";
		for(cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()){
			result = result + cursor.getString(0) +"�G" 
					+ cursor.getString(1) + 
					"�G" + cursor.getString(2) + "\n";
		}
		return result;
	}
	
	public long insert(String v1,String v2,String v3){ //�s�W
	
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("SNO", v1);
		contentvalues.put("Password", v2);
		contentvalues.put("Start_time", v3);
		return sqlitedatabase.insert(Student, null, contentvalues);
	}
    
	public long delete(String v){ //�R��
		
		String[] delv = new String[] {v};
		return sqlitedatabase.delete(Student, SNO+ "=?", delv);
	}
	
	
	public long update(String newv,String oldv){ //�ק�
		
		ContentValues contentvalues = new ContentValues();
		contentvalues.put(Password, newv);
		String[] oldvelue = new String[] {oldv};
		return sqlitedatabase.update(Student, contentvalues, SNO+ "=?", oldvelue);
	}

	public class DBHelper extends SQLiteOpenHelper{
		
		public DBHelper(Context context, String name,CursorFactory factory, int version){ //DBHelper�غc�l
			super(context,Study,null,version); //��Ʈw�W��=Study�A�ثe����=1
		}
		@Override
		public void onCreate(SQLiteDatabase db){ //�Ĥ@���䤣��A�إ�"�s���"
			
			db.execSQL(stu); //�إ�student
			db.execSQL(tar); //�إ�target
			db.execSQL(sstudy); //�إ�study
		}
		public void onUpgrade(SQLiteDatabase db,int oldv,int newV){ 
		}
	}
}



