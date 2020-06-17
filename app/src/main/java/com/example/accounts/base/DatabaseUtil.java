package com.example.accounts.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.accounts.bean.Account;
import com.example.accounts.bean.Student;

import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    private static DatabaseUtil databaseUtils;
    private SQLiteDatabase db;

    /**
     *
     * @return
     */
    public static DatabaseUtil getInstance() {
        if (databaseUtils == null) {
            databaseUtils = new DatabaseUtil();
        }
        return databaseUtils;
    }

    /**
     *
     *
     * @param contenxt
     *
     */
    public void init(Context contenxt) {
        String path = contenxt.getCacheDir().getPath() + "/student.db";
        db = SQLiteDatabase.openOrCreateDatabase(path, null);
        String student = "create table if not exists user"
                + "(id integer primary key autoincrement,"
                + "name text,password text)";
        String account = "create table if not exists account"
                + "(id integer primary key autoincrement,"
                + "type text,price text,date text,remark text)";
        db.execSQL(student);//
        db.execSQL(account);
    }
    //添加学生
    public long inisertStudent(Student student){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", student.getName());
        contentValues.put("password", student.getPassword());
        long dataSize = db.insert("user", null, contentValues);
        return dataSize;
    }
    //添加账单
    public long inisertAccount(Account account){
        Account oldAccount = queryAccount(account.getDate(),account.getType());
        if (oldAccount.getPrice()!=null){
            updateAccount(account,oldAccount.getPrice());
            return -1;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", account.getType());
        contentValues.put("price", account.getPrice());
        contentValues.put("date", account.getDate());
        contentValues.put("remark", account.getRemark());
        long dataSize = db.insert("account", null, contentValues);
        return dataSize;
    }
     //修改
    private void updateAccount(Account account,String price) {
        Log.e("newPrice="+price,"oldPrice="+account.getPrice());
        double prices = Double.valueOf(price) + Double.valueOf(account.getPrice());
        ContentValues contentValues = new ContentValues();
        contentValues.put("price", prices+"");
        int index = db.update("account", contentValues, "date = ? and type=?",
                new String[] { account.getDate(),account.getType() });
//        return index;
    }

    public Account queryAccount(String dates,String types){
        Account user = new Account();
        Cursor cursor = db.query("account", null, "date = ? and type=?",
                new String[] { dates,types }, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            user.setType(type);
            user.setDate(date);
            user.setRemark(remark);
            user.setPrice(price);
            user.setId(id);
        }
        if (cursor!=null){
            cursor.close();
        }
        return user;
    }


    //登录判断
    public boolean isLogin(String name,String psw){
        Student user = queryStudent(name);
        if (user.getName().equals(name) && user.getPassword().equals(psw)){
            return true;
        }
        return false;
    }

    //根据姓名来查询用户
    public Student queryStudent(String names){
        Student user = new Student();
        Cursor cursor = db.query("user", null, "name = ?",
                new String[] { names }, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String psw = cursor.getString(cursor.getColumnIndex("password"));
            user.setName(name);
            user.setPassword(psw);
            user.setId(id);
        }
        if (cursor!=null){
            cursor.close();
        }
        return user;
    }
    public List<Account> getAllData() {
		ArrayList<Account> list = new ArrayList<>();
		Cursor cursor = db.query("account", null, null, null, null, null, null);
		while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String remark = cursor.getString(cursor.getColumnIndex("remark"));
            Account account = new Account();
            account.setType(type);
            account.setDate(date);
            account.setRemark(remark);
            account.setPrice(price);
            account.setId(id);
			list.add(account);
		}
		if (cursor != null) {
			cursor.close();
		}
		return list;
	}

}
