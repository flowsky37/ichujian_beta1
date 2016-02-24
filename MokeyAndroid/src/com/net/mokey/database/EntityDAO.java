package com.net.mokey.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;

import com.net.mokey.util.BitmapUtil;

public class EntityDAO extends SQLiteOpenHelper {
	private static final int DB_VRESION = 1;
	Context c;
	/**
	 * 数据库名称
	 */
	private  final static String DB_NAME = "'mokey.db";
	/**
	 * 一键启动表名称,ID,app包名，名字，图片
	 */
	public  final String START_TABLE_NAME = "start";
	public final  String START_FIELD_id = "_id";
	public final  String START_PAGENAME="start_pageName";
	public final  String START_NAME = "start_name";
	public  final String START_PICTURE = "start_picture";
	
	/**
	 * 一键切换表名称,ID,app包名，名字，图片
	 */
	public  final String SWITCH_TABLE_NAME = "switch";
	public final  String SWITCH_FIELD_id = "_id";
	public final  String SWITCH_PAGENAME="switch_pageName";
	public final  String SWITCH_NAME = "switch_name";
	public  final String SWITCH_PICTURE = "switch_picture";
	
	/**
	 * 一键启动常用表名称,ID,app包名，名字，图片
	 */
	public final String NINE_TABLE_NAME = "nine";
	public final String NINE_FIELD_id = "_id";
	public final String NINE_PAGENAME="nine_pageName";
	public final String NINE_NAME = "nine_name";
	public final String NINE_PICTURE = "nine_picture";
	
	//扫描码
	public  final String QRTABLE = "qrTable";
	public  final String QRID = "_id";
	public  final String QRNUM = "qrnum";
	
	//设置单击和长按的标识
	//扫描码
	public  final String LOGO_TAB_NAME = "logo_tab_name";
	public  final String LOGO_ID = "_id";
	public  final String LOGO_CLICK_NUM = "logo_click_num";
	public  final String LOGO_LONG_CLICK_NUM = "logo_long_click_num";
	/**
	 *  创建数据库访问对象 它实际上没有创建数据库，马上返回。
	 */
	public EntityDAO(Context c) {
		super(c, DB_NAME, null, DB_VRESION);
		this.c = c;
	}
	/**
	 * 第一次调用 getWritableDatabase() 或 getReadableDatabase() 时调用
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO 创建数据库表
		/*String sql = "CREATE TABLE " + TABLE_NAME + "(" + FIELD_id
				+ " INTEGER	PRIMARY KEY autoincrement ," + PAGENAME
				+ " VARCHAR(10) NULL ," + PICTURE + " BLOB ," + NAME + " varchar(200) null" + ")";
		db.execSQL(sql);*/
		createTab(db, START_TABLE_NAME, START_FIELD_id, START_NAME, START_PAGENAME, START_PICTURE);
		createTab(db, SWITCH_TABLE_NAME, SWITCH_FIELD_id, SWITCH_NAME, SWITCH_PAGENAME, SWITCH_PICTURE);
		createTab(db, NINE_TABLE_NAME, NINE_FIELD_id, NINE_NAME, NINE_PAGENAME, NINE_PICTURE);
		
		String sql = "CREATE TABLE " + QRTABLE + "(" + QRID
				+ " INTEGER	PRIMARY KEY autoincrement ," + QRNUM
				+ " VARCHAR(10) NULL" + ")";
		db.execSQL(sql);
		
		/*String logo = "CREATE TABLE " + LOGO_TAB_NAME + "(" + LOGO_ID
				+ " INTEGER	PRIMARY KEY autoincrement ," + LOGO_CLICK_NUM
				+ " VARCHAR(10) NULL ," + LOGO_LONG_CLICK_NUM + " varchar(200) null" + ")";
		db.execSQL(logo);*/
	}
	private void createTab(SQLiteDatabase db,String tabName,String id,String name,String pageName,String picture){
		String sql = "CREATE TABLE " + tabName + "(" + id
				+ " INTEGER	PRIMARY KEY autoincrement ," + pageName
				+ " VARCHAR(10) NULL ," + picture + " BLOB ," + name + " varchar(200) null" + ")";
		db.execSQL(sql);
	}
	/**
	 * DB_VRESION 变化时调用此函数，此处不需要使用（但不能删除）
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		// onCreate(db);
	}

	// TODO增加一条数据 方法
	public long insert(String PAGENAME,String PICTURE,String NAME,String TABLE_NAME,String pageName, String name,Drawable drawable) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(PAGENAME, pageName);
		cv.put(PICTURE, BitmapUtil.getPicture(drawable));
		cv.put(NAME, name);
		long row = db.insert(TABLE_NAME, null, cv);
		// db.close();
		return row;
	}
	// TODO增加一条数据 方法
	public long insertNine(String pageName, String name,Drawable drawable) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(NINE_FIELD_id, pageName);
		cv.put(NINE_PAGENAME, pageName);
		cv.put(NINE_PICTURE, BitmapUtil.getPicture(drawable));
		cv.put(NINE_NAME, name);
		long row = db.insert(NINE_TABLE_NAME, null, cv);
		//db.close();
		return row;
	}
	
	public void deleteSQL() {

		c.deleteDatabase(DB_NAME);

	}

	// TODO删除一条数据 方法
	public void delete(int id,String FIELD_id,String TABLE_NAME) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_id + " = ?";
		String[] whereValue = { Integer.toString(id) };
		db.delete(TABLE_NAME, where, whereValue);
		db.close();
	}

	/*// TODO 更新一条数据 方法
	public void update(int id, String text, String text2) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = FIELD_id + " = ?";
		String[] whereValue = { Integer.toString(id) };
		 将修改的值放入ContentValues 
		ContentValues cv = new ContentValues();
		cv.put(PAGENAME, text);
		cv.put(NAME, text2);
		db.update(TABLE_NAME, cv, where, whereValue);
		db.close();
	}*/

	// TODO  查询一条数据 方法 
	 /*public Cursor selectone(long rowId) 
	  { 
	    SQLiteDatabase db = this.getReadableDatabase();
//	    Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
	    Cursor cursor = db.query(true,TABLE_NAME, new String[]{FIELD_id,PAGENAME,NAME}, FIELD_id + "=" + rowId, null, null, null, null,null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		// cursor.close();
		db.close();
		return cursor;
	}*/

	/**
	 * 删除数据库中表的数据
	 * 
	 * @DBName 数据库名字
	 * @TableName 表名字
	 */
	public boolean deleteTableByDBName(String tabName) {
		SQLiteDatabase dbDatabase = openDBByName(tabName);
		dbDatabase.delete(tabName, null, null);
		return false;
	}

	/**
	 * 打开数据库
	 * 
	 * @DBName 数据库名字
	 */
	public SQLiteDatabase openDBByName(String TABLE_NAME) {
		SQLiteDatabase db;
		// deleteDBByName(DBName);
		db = this.c.openOrCreateDatabase(TABLE_NAME, Context.MODE_PRIVATE, null);
		return db;
	}

	// TODO 查询所有数据 方法
	public Cursor select(String TABLE_NAME) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		return cursor;
	}
	/*public boolean check(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		if(!cursor.moveToFirst())
			return false;
		else
			return true;
	}*/
	// TODO增加一条数据 方法
	public long insertQr(String qrNum) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(QRNUM, qrNum);
		long row = db.insert(QRTABLE, null, cv);
		// db.close();
		return row;
	}
	// TODO增加一条数据 方法
	public long insertLogo(String logo_click_num,String logo_long_click_num) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(LOGO_CLICK_NUM, logo_click_num);
		cv.put(LOGO_LONG_CLICK_NUM, logo_long_click_num);
		long row = db.insert(LOGO_TAB_NAME, null, cv);
		// db.close();
		return row;
	}
}
