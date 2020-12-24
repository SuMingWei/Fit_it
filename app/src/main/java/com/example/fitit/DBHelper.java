package com.example.fitit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private Context nowContext;
    private final String name = "Name";
    private final String password = "Password";

    private final String uid = "ID";
    private final String upperlimb = "Upperlimb";
    private final String lowerlimb = "Lowerlimb";
    private final String softness = "Softness";
    private final String endurance = "Endurance";

    private final String date = "date";

    private static final String databaseName = "LocalDB";
    private static final int databaseVersion = 1;

    // account info
    private final String AccountTableName = "AccountInfo";
    private final String createAccountTableSQL = "CREATE TABLE IF NOT EXISTS " + this.AccountTableName
            + " ( " + this.name + " VARCHAR(255)," + this.password + " VARCHAR(255) ) ;";

    private final String deleteAccountTableSQL = "DROP TABLE IF EXISTS " + this.AccountTableName + ";";
    // pet info
    private final String PetTableName = "PetInfo";
    private final String createPetTableSQL = "CREATE TABLE IF NOT EXISTS " + this.PetTableName
            + " ( " + this.uid + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + this.upperlimb + " INTEGER,"
            + this.lowerlimb + " INTEGER,"
            + this.softness + " INTEGER,"
            + this.endurance + " INTEGER ) ;";

    private final String deletePetTableSQL = "DROP TABLE IF EXISTS " + this.PetTableName + ";";
    // diary info
    private final String DiaryTableName = "DiaryInfo";
    private final String createDiaryTableSQL = "CREATE TABLE IF NOT EXISTS " + this.DiaryTableName
            + " ( " + this.date + " VARCHAR(255),"
            + this.upperlimb + " INTEGER,"
            + this.lowerlimb + " INTEGER,"
            + this.softness + " INTEGER,"
            + this.endurance + " INTEGER ) ;";

    private final String deleteDiaryTableSQL = "DROP TABLE IF EXISTS " + this.DiaryTableName + ";";


    public DBHelper(Context context){
        super(context,databaseName,null,databaseVersion);
        this.nowContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(this.createAccountTableSQL);
            db.execSQL(this.createPetTableSQL);
            db.execSQL(this.createDiaryTableSQL);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(this.deleteAccountTableSQL);
            db.execSQL(this.deletePetTableSQL);
            db.execSQL(this.deleteDiaryTableSQL);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertToAccount(String name,String password){
        SQLiteDatabase myLocalDB = this.getReadableDatabase() ;

        ContentValues contentValues = new ContentValues();
        contentValues.put(this.name,name);
        contentValues.put(this.password,password);

        long nowID = myLocalDB.insert(this.AccountTableName,null,contentValues);

        final String successLog = "註冊成功！";
        Toast.makeText(this.nowContext,successLog,Toast.LENGTH_SHORT).show();
    }

    public ArrayList<AccountInfo> getAccountInfo(){
        ArrayList<AccountInfo> accountList = new ArrayList<AccountInfo>();

        SQLiteDatabase myLocalDB = this.getReadableDatabase();
        String[] myColumn = {this.name,this.password};

        Cursor myCursor = myLocalDB.query(this.AccountTableName,myColumn,null,null,null,null,null);

        while(myCursor.moveToNext()){
            String name = myCursor.getString(myCursor.getColumnIndex(this.name));
            String phone = myCursor.getString(myCursor.getColumnIndex(this.password));

            AccountInfo accountInfo = new AccountInfo();
            accountInfo.init(name,phone);
            accountList.add(accountInfo);
        }

        return accountList;
    }

    public void insertToPet(int upperlimb,int lowerlimb,int softness,int endurance){
        SQLiteDatabase myLocalDB = this.getReadableDatabase() ;

        ContentValues contentValues = new ContentValues();
        contentValues.put(this.upperlimb,upperlimb);
        contentValues.put(this.lowerlimb,lowerlimb);
        contentValues.put(this.softness,softness);
        contentValues.put(this.endurance,endurance);

        long nowID = myLocalDB.insert(this.PetTableName,null,contentValues);

        Toast.makeText(this.nowContext,"new pet",Toast.LENGTH_SHORT).show();
    }

    public void updateToPet(int id,int new_upperlimb,int new_lowerlimb,int new_softness,int new_endurance){
        SQLiteDatabase myLocalDB = this.getReadableDatabase() ;

        ContentValues contentValues = new ContentValues();
        contentValues.put(this.upperlimb,new_upperlimb);
        contentValues.put(this.lowerlimb,new_lowerlimb);
        contentValues.put(this.softness,new_softness);
        contentValues.put(this.endurance,new_endurance);

        String[] argu = {String.valueOf(id)};
        int affectRow = myLocalDB.update(this.PetTableName,contentValues,this.uid + " = ? ",argu);

        if(affectRow == 0){
            Toast.makeText(this.nowContext,"更新寵物失敗，請重新再試", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this.nowContext,"更新寵物成功", Toast.LENGTH_SHORT).show();
        }

    }

    public ArrayList<PetInfo> getPetInfo(){
        ArrayList<PetInfo> petList = new ArrayList<>();

        SQLiteDatabase myLocalDB = this.getReadableDatabase();
        String[] myColumn = {this.upperlimb,this.lowerlimb,this.softness,this.endurance};

        Cursor myCursor = myLocalDB.query(this.PetTableName,myColumn,null,null,null,null,null);

        while(myCursor.moveToNext()){
            int upperlimb = myCursor.getInt(myCursor.getColumnIndex(this.upperlimb));
            int lowerlimb = myCursor.getInt(myCursor.getColumnIndex(this.lowerlimb));
            int softness = myCursor.getInt(myCursor.getColumnIndex(this.softness));
            int endurance = myCursor.getInt(myCursor.getColumnIndex(this.endurance));

            PetInfo petInfo = new PetInfo();
            petInfo.init(upperlimb,lowerlimb,softness,endurance);
            petList.add(petInfo);
        }

        return petList;
    }

    public void insertToDiary(String date,int upperlimb,int lowerlimb,int softness,int endurance){
        SQLiteDatabase myLocalDB = this.getReadableDatabase() ;

        ContentValues contentValues = new ContentValues();
        contentValues.put(this.date,date);
        contentValues.put(this.upperlimb,upperlimb);
        contentValues.put(this.lowerlimb,lowerlimb);
        contentValues.put(this.softness,softness);
        contentValues.put(this.endurance,endurance);

        long nowID = myLocalDB.insert(this.DiaryTableName,null,contentValues);

        Toast.makeText(this.nowContext,"新增日誌",Toast.LENGTH_SHORT).show();
    }

    public void updateToDiary(String date,int new_upperlimb,int new_lowerlimb,int new_softness,int new_endurance){
        SQLiteDatabase myLocalDB = this.getReadableDatabase() ;

        ContentValues contentValues = new ContentValues();
        contentValues.put(this.date,date);
        contentValues.put(this.upperlimb,new_upperlimb);
        contentValues.put(this.lowerlimb,new_lowerlimb);
        contentValues.put(this.softness,new_softness);
        contentValues.put(this.endurance,new_endurance);

        String[] argu = {String.valueOf(date)};
        int affectRow = myLocalDB.update(this.DiaryTableName,contentValues,this.date + " = ? ",argu);

        if(affectRow == 0){
            Toast.makeText(this.nowContext,"更新日誌失敗，請重新再試", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this.nowContext,"更新日誌成功", Toast.LENGTH_SHORT).show();
        }

    }

    public ArrayList<DiaryInfo> getDiaryInfo(){
        ArrayList<DiaryInfo> DiaryList = new ArrayList<>();

        SQLiteDatabase myLocalDB = this.getReadableDatabase();
        String[] myColumn = {this.date,this.upperlimb,this.lowerlimb,this.softness,this.endurance};

        Cursor myCursor = myLocalDB.query(this.DiaryTableName,myColumn,null,null,null,null,null);

        while(myCursor.moveToNext()){
            String date = myCursor.getString(myCursor.getColumnIndex(this.date));
            int upperlimb = myCursor.getInt(myCursor.getColumnIndex(this.upperlimb));
            int lowerlimb = myCursor.getInt(myCursor.getColumnIndex(this.lowerlimb));
            int softness = myCursor.getInt(myCursor.getColumnIndex(this.softness));
            int endurance = myCursor.getInt(myCursor.getColumnIndex(this.endurance));

            DiaryInfo diaryInfo = new DiaryInfo();
            diaryInfo.init(date,upperlimb,lowerlimb,softness,endurance);
            DiaryList.add(diaryInfo);
        }

        return DiaryList;
    }




}
