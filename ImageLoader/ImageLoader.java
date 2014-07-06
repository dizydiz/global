package com.sportsbetting.ImageLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Stack;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.sportsbetting.R;




public class ImageLoader {
    
    //the simplest in-memory cache implementation. This should be replaced with something like SoftReference or BitmapOptions.inPurgeable(since 1.6)
    private HashMap<String, Bitmap> cache=new HashMap<String, Bitmap>();
    
    private File cacheDir;
    private final Bitmap myBitmap;
    private int targetWitdh,targetHeight;
    private Context context;
    
    public ImageLoader(Context context_,int stub){
        //Make the background thead low priority. This way it will not affect the UI performance
        photoLoaderThread.setPriority(Thread.NORM_PRIORITY-1);
        
        context = context_;
        
        myBitmap = BitmapFactory.decodeResource(context_.getResources(), stub);
        //Find the dir to save cached images
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),context.getString(R.string.app_name));
        else
            cacheDir=context_.getCacheDir();
        if(!cacheDir.exists())
            cacheDir.mkdirs();
    }
    
    
//    final int stub_id=R.drawable.stub;
//    final int no_img=R.drawable.no_image;
    public void DisplayImage(String url, Activity activity, ImageView imageView,int targetWitdh,int targetHeight)
    
    {
    	this.targetWitdh = targetWitdh;
    	this.targetHeight = targetHeight;
    	
    	String filename1=String.valueOf(url.hashCode());
        File f1=new File(cacheDir, filename1);
        //from SD cache
        Bitmap b1 = decodeFile(f1);
        if(b1!=null){
        	 imageView.setImageBitmap(b1);
        }
        else
        {
            queuePhoto(url, activity, imageView);
            try {
            	 imageView.setImageBitmap(myBitmap);
			} catch (OutOfMemoryError e) {
				System.gc();
				imageView.setImageBitmap(myBitmap);
			}
           
        }    
    }
    
        
    private void queuePhoto(String url, Activity activity, ImageView imageView)
    {
        //This ImageView may be used for other images before. So there may be some old tasks in the queue. We need to discard them. 
        photosQueue.Clean(imageView);
        PhotoToLoad p=new PhotoToLoad(url, imageView);
        synchronized(photosQueue.photosToLoad){
            photosQueue.photosToLoad.push(p);
            photosQueue.photosToLoad.notifyAll();
        }
        
        //start thread if it's not started yet
        if(photoLoaderThread.getState()==Thread.State.NEW)
            photoLoaderThread.start();
    }
    
    private Bitmap getBitmap(String url) 
    {
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename=String.valueOf(url.hashCode());
        File f=new File(cacheDir, filename);
        
        //from SD cache
        Bitmap b = decodeFile(f);
        if(b!=null)
            return b;
        
        //from web
        try {
            Bitmap bitmap=null;
            InputStream is=new URL(url).openStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            
            
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Exception ex){
           ex.printStackTrace();
//           Log.e("here",""+ex.toString());
           return null;
        }
    }

    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
//            o.inSampleSize=2;
            try {
            	BitmapFactory.decodeStream(new FileInputStream(f),null,o);
			} catch (OutOfMemoryError e) {
				// TODO: handle exception
				System.gc();
				BitmapFactory.decodeStream(new FileInputStream(f),null,o);
			}
            
            
            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE= targetWitdh;
            Log.v("require_size",">"+targetWitdh);
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale++;
            }
            
            BitmapFactory.Options o2 = new BitmapFactory.Options();
//            if(scale>1){
//            	scale = scale-1;
//            }
            o2.inSampleSize=scale;
            try {
            	Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);

            	if(bm!=null){
            		if(bm.getHeight()>=bm.getWidth()){
            	    	if(targetHeight<bm.getHeight()){
            	 	    	bm = Bitmap.createScaledBitmap(bm, (targetHeight*bm.getWidth())/bm.getHeight(),targetHeight, true);
            	 	    }
            	    }else{
            	    	if(targetWitdh<bm.getWidth()){
            	 	    	bm = Bitmap.createScaledBitmap(bm, targetWitdh,(targetWitdh*bm.getHeight())/bm.getWidth(), true);
            	 	    }
            	    }
            	}
            	return bm;
			} catch (OutOfMemoryError e) {
				// TODO: handle exception
				System.gc();
				return null;
			}
        } catch (FileNotFoundException e) {}
        catch (OutOfMemoryError e) {e.printStackTrace();
        return null;
        }
        return null;
    }
    
    //Task for the queue
    private class PhotoToLoad
    {
        public String url;
        public ImageView imageView;
        public PhotoToLoad(String u, ImageView i){
            url=u; 
            imageView=i;
        }
    }
    
    PhotosQueue photosQueue=new PhotosQueue();
    
    public void stopThread()
    {
        photoLoaderThread.interrupt();
    }
    
    //stores list of photos to download
    class PhotosQueue
    {
        private Stack<PhotoToLoad> photosToLoad=new Stack<PhotoToLoad>();
        
        //removes all instances of this ImageView
        public void Clean(ImageView image)
        {
            for(int j=0 ;j<photosToLoad.size();){
                if(photosToLoad.get(j).imageView==image)
                    photosToLoad.remove(j);
                else
                    ++j;
            }
        }
    }
    
    class PhotosLoader extends Thread {
        public void run() {
            try {
                while(true)
                {
                    //thread waits until there are any images to load in the queue
                    if(photosQueue.photosToLoad.size()==0)
                        synchronized(photosQueue.photosToLoad){
                            photosQueue.photosToLoad.wait();
                        }
                    if(photosQueue.photosToLoad.size()!=0)
                    {
                        PhotoToLoad photoToLoad;
                        synchronized(photosQueue.photosToLoad){
                            photoToLoad=photosQueue.photosToLoad.pop();
                        }
                        Bitmap bmp=getBitmap(photoToLoad.url);
                       // if(bmp!=null)
                        cache.put(photoToLoad.url, bmp);
//                        Log.e("check",""+cache.get(photoToLoad.url));
                        //Log.e("bmp",""+bmp.toString());
                        if(((String)photoToLoad.imageView.getTag()).equals(photoToLoad.url)){
                            BitmapDisplayer bd=new BitmapDisplayer(bmp, photoToLoad.imageView);
                            Activity a=(Activity)photoToLoad.imageView.getContext();
                            a.runOnUiThread(bd);
                        }
                    }
                    if(Thread.interrupted())
                        break;
                }
            } catch (InterruptedException e) {
            	
                //allow thread to exit
            }
        }
    }
    
    PhotosLoader photoLoaderThread=new PhotosLoader();
    
    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        ImageView imageView;
        public BitmapDisplayer(Bitmap b, ImageView i){bitmap=b;imageView=i;}
        public void run()
        {
            if(bitmap!=null){
            	imageView.setImageBitmap(bitmap);
            }
                
            else{
            	imageView.setImageBitmap(myBitmap);
            }
            	
        }
    }

    public void clearCache() {
        //clear memory cache
        cache.clear();
        
        //clear SD cache
        File[] files=cacheDir.listFiles();
        for(File f:files)
            f.delete();
    }

}
