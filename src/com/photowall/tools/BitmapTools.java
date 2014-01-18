package com.photowall.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Log;

import com.photowall.lazyload.CacheDIR;

public class BitmapTools {

	public static  Bitmap rotaingImageView(int angle , Bitmap bitmap) {  
        //旋转图片 动作  
        Matrix matrix = new Matrix();;  
        matrix.postRotate(angle);  
        // 创建新的图片  
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,  
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);  
        return resizedBitmap;  
    }
    /** 
     * 读取图片属性：旋转的角度 
     * @param path 图片绝对路径 
     * @return degree旋转的角度 
     */  
       public static int readPictureDegree(String path) {  
           int degree  = 0;  
           try {  
                   ExifInterface exifInterface = new ExifInterface(path);  
                   int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);  
                   switch (orientation) {  
                   case ExifInterface.ORIENTATION_ROTATE_90:  
                           degree = 90;  
                           break;  
                   case ExifInterface.ORIENTATION_ROTATE_180:  
                           degree = 180;  
                           break;  
                   case ExifInterface.ORIENTATION_ROTATE_270:  
                           degree = 270;  
                           break;  
                   }  
           } catch (IOException e) {  
                   e.printStackTrace();  
           }  
           return degree;  
       }  
       public static  Bitmap getimage(String srcPath) {  
           BitmapFactory.Options newOpts = new BitmapFactory.Options();  
           //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
           newOpts.inJustDecodeBounds = true;  
           Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空  
             
           newOpts.inJustDecodeBounds = false;  
           int w = newOpts.outWidth;  
           int h = newOpts.outHeight;  
           //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
           float hh = 800f;//这里设置高度为800f  
           float ww = 480f;//这里设置宽度为480f  
           //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
           int be = 1;//be=1表示不缩放  
           if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
               be = (int) (newOpts.outWidth / ww);  
           } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
               be = (int) (newOpts.outHeight / hh);  
           }  
           if (be <= 0)  
               be = 1;  
           newOpts.inSampleSize = be;//设置缩放比例  
           //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
           bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
           return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
       } 
       public static Bitmap compressImageTo1m(Bitmap image) {  
           
           ByteArrayOutputStream baos = new ByteArrayOutputStream();         
           image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
           if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
               baos.reset();//重置baos即清空baos  
               image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中  
           }  
           ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
           BitmapFactory.Options newOpts = new BitmapFactory.Options();  
           //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
           newOpts.inJustDecodeBounds = true;  
           Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
           newOpts.inJustDecodeBounds = false;  
           int w = newOpts.outWidth;  
           int h = newOpts.outHeight;  
           //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
           float hh = 800f;//这里设置高度为800f  
           float ww = 480f;//这里设置宽度为480f  
           //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
           int be = 1;//be=1表示不缩放  
           if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
               be = (int) (newOpts.outWidth / ww);  
           } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
               be = (int) (newOpts.outHeight / hh);  
           }  
           if (be <= 0)  
               be = 1;  
           newOpts.inSampleSize = be;//设置缩放比例  
           //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
           isBm = new ByteArrayInputStream(baos.toByteArray());  
           bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
           return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
       }  
       private static Bitmap compressImage(Bitmap image) {  
    	   
           ByteArrayOutputStream baos = new ByteArrayOutputStream();  
           image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
           int options = 100;  
           while ( baos.toByteArray().length / 1024>300) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
               baos.reset();//重置baos即清空baos  
               image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
               options -= 10;//每次都减少10  
           }  
           ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
           Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
           return bitmap;  
       }
       
       public static void compressImg(String srcPath) {  
    	   BitmapFactory.Options newOpts = new BitmapFactory.Options();  
           //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
           newOpts.inJustDecodeBounds = true;  
           Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空  
             
           newOpts.inJustDecodeBounds = false;  
           int w = newOpts.outWidth;  
           int h = newOpts.outHeight;  
           Log.i("compress","w:"+w +" h:"+h);
           //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
           float hh = 800f;//这里设置高度为800f  
           float ww = 480f;//这里设置宽度为480f  
           //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
           int be = 1;//be=1表示不缩放  
           if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
               be = (int) (newOpts.outWidth / ww);  
           } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
               be = (int) (newOpts.outHeight / hh);  
           }  
           if (be <= 0)  
               be = 1;  
           newOpts.inSampleSize = be;//设置缩放比例  
           //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
           bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
           bitmap = compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
           Log.i("compress",bitmap.getWidth()+"");
           //save
           File file = getOutputCompressFile();
    	   try {
			OutputStream os = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);  
			os.flush();
			os.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	   
       }  
       
       
       /** Create a File for saving an image or video */
       public  static File getOutputCompressFile()
       {
           // To be safe, you should check that the SDCard is mounted
           // using Environment.getExternalStorageState() before doing this.

           File mediaStorageDir = null;
           try
           {
               // This location works best if you want the created images to be
               // shared
               // between applications and persist after your app has been
               // uninstalled.
               mediaStorageDir = new File(
                       Environment
                               .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                               CacheDIR.cachepath);


           }
           catch (Exception e)
           {
               e.printStackTrace();
           }

           // Create the storage directory if it does not exist
           if (!mediaStorageDir.exists())
           {
               if (!mediaStorageDir.mkdirs())
               {
                   // 在SD卡上创建文件夹需要权限：
                   // <uses-permission
                   // android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
                   return null;
               }
           }
           File mediaFile;
               mediaFile = new File(mediaStorageDir.getPath() + File.separator
                       + "tempcompress" + ".jpg");

//               if(mediaFile.exists())
//               {
//            	   mediaFile.delete();
//               }
//            
//            try {
//				mediaFile.createNewFile();
//			} catch (IOException e) {
//				e.printStackTrace();
//				return null;
//			}
           return mediaFile;
       }
       public static  void setPictureDegreeZero(String path){
    	    try {
    	ExifInterface exifInterface = new ExifInterface(path);
    	//修正图片的旋转角度，设置其不旋转。这里也可以设置其旋转的角度，可以传值过去，
    	//例如旋转90度，传值ExifInterface.ORIENTATION_ROTATE_90，需要将这个值转换为String类型的
    	exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, "no");
    	exifInterface.saveAttributes();
    	} catch (IOException e) {
    	// TODO Auto-generated catch block
    	e.printStackTrace();
    	}  
    	   
    	    }
       
       public static void saveRotateImage(int degree,String imagepath)
       {
    	   File file = new File(imagepath);
    	   try {
			Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
			//旋转图片 动作  
	        Matrix matrix = new Matrix();;  
	        matrix.postRotate(degree);  
	        // 创建新的图片  
	        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,  
	                bitmap.getWidth(), bitmap.getHeight(), matrix, true);  
	        //save
	            File outfile = getOutputCompressFile();
				OutputStream os = new FileOutputStream(outfile);
				resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);  
				os.flush();
				os.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
       }
       public static int getExifOrientation(String filepath) {
           int degree = 0;
           ExifInterface exif = null;
           try {
               exif = new ExifInterface(filepath);
           } catch (IOException ex) {
           }
           if (exif != null) {
               int orientation = exif.getAttributeInt(
                   ExifInterface.TAG_ORIENTATION, -1);
               if (orientation != -1) {
                   // We only recognize a subset of orientation tag values.
                   switch(orientation) {
                       case ExifInterface.ORIENTATION_ROTATE_90:
                           degree = 90;
                           break;
                       case ExifInterface.ORIENTATION_ROTATE_180:
                           degree = 180;
                           break;
                       case ExifInterface.ORIENTATION_ROTATE_270:
                           degree = 270;
                           break;
                   }

               }
               
//               else if(orientation == 0 && EXIF_ORIENTATION != 0) {
//                   switch (EXIF_ORIENTATION) {
//                   case 90:
//                       orientation = ExifInterface.ORIENTATION_ROTATE_90;
//                       degree = 90;
//                       break;
//                   case 180:
//                       orientation = ExifInterface.ORIENTATION_ROTATE_180;
//                       degree = 180;
//                       break;
//                   case 270:
//                       orientation = ExifInterface.ORIENTATION_ROTATE_270;
//                       degree = 270;
//                       break;
//                   }
//                   exif.setAttribute(ExifInterface.TAG_ORIENTATION, Integer.toString(orientation));
//                   try {
//                       exif.saveAttributes();
//                   } catch (IOException e) {
//                   }
//               }
           }
           return degree;
       }
}
