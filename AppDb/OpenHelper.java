package com.autolog.AppDb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper {


	public static SQLiteOpenHelper singleton;
	static Context context;
	static SQLiteDatabase db;
	public static final String DATABASE_NAME = "autolog.sqlite";
	public static final int DATABASE_VERSION = 1;
	
    public static SQLiteDatabase getDataHelper(Context context_) {
    	context = context_;
    	try {copyDataBase(context);} catch (IOException e) {}
    	
            if (singleton == null) {
            	openDb();
            }
            if(db!=null){
            	if(!db.isOpen()){
            		openDb();
            	}
            }else{
            	openDb();
            }
            return db ;
    }
    
    static void openDb(){
    	
    	singleton = new SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
			
    		@Override
    		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    		}
    		@Override
    		public void onCreate(SQLiteDatabase db) {
    		}
    	};
    	db = singleton.getWritableDatabase();
    }
    
    @SuppressLint("SdCardPath")
	public static void copyDataBase(Context context) throws IOException {

        try {
        	 File file1 = new File("/data/data/"+context.getPackageName()+"/databases/"+DATABASE_NAME);
             if(!file1.exists()){
            	 File file = new File("/data/data/"+context.getPackageName()+"/databases/");
                 if (!file.exists()) {
                     if (!file.mkdirs()) {
//                         Log.e("Error ", "Problem creating folder");
                     }
                 }

                InputStream myInput = context.getAssets().open(DATABASE_NAME);
                
                // Path to the just created empty db
                String outFileName = "/data/data/"+context.getPackageName()+"/databases/"+DATABASE_NAME;
                OutputStream myOutput = new FileOutputStream(outFileName);

                // transfer bytes from the inputfile to the outputfile
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                
                myOutput.flush();
                myOutput.close();
                myInput.close();
//                Log.e("DB ", "COPying DONe...");
            }
             
             
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
}
