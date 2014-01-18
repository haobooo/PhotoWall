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
        //��תͼƬ ����  
        Matrix matrix = new Matrix();;  
        matrix.postRotate(angle);  
        // �����µ�ͼƬ  
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,  
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);  
        return resizedBitmap;  
    }
    /** 
     * ��ȡͼƬ���ԣ���ת�ĽǶ� 
     * @param path ͼƬ����·�� 
     * @return degree��ת�ĽǶ� 
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
           //��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
           newOpts.inJustDecodeBounds = true;  
           Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//��ʱ����bmΪ��  
             
           newOpts.inJustDecodeBounds = false;  
           int w = newOpts.outWidth;  
           int h = newOpts.outHeight;  
           //���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ  
           float hh = 800f;//�������ø߶�Ϊ800f  
           float ww = 480f;//�������ÿ��Ϊ480f  
           //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��  
           int be = 1;//be=1��ʾ������  
           if (w > h && w > ww) {//�����ȴ�Ļ����ݿ�ȹ̶���С����  
               be = (int) (newOpts.outWidth / ww);  
           } else if (w < h && h > hh) {//����߶ȸߵĻ����ݿ�ȹ̶���С����  
               be = (int) (newOpts.outHeight / hh);  
           }  
           if (be <= 0)  
               be = 1;  
           newOpts.inSampleSize = be;//�������ű���  
           //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
           bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
           return compressImage(bitmap);//ѹ���ñ�����С���ٽ�������ѹ��  
       } 
       public static Bitmap compressImageTo1m(Bitmap image) {  
           
           ByteArrayOutputStream baos = new ByteArrayOutputStream();         
           image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
           if( baos.toByteArray().length / 1024>1024) {//�ж����ͼƬ����1M,����ѹ������������ͼƬ��BitmapFactory.decodeStream��ʱ���    
               baos.reset();//����baos�����baos  
               image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//����ѹ��50%����ѹ��������ݴ�ŵ�baos��  
           }  
           ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
           BitmapFactory.Options newOpts = new BitmapFactory.Options();  
           //��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
           newOpts.inJustDecodeBounds = true;  
           Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
           newOpts.inJustDecodeBounds = false;  
           int w = newOpts.outWidth;  
           int h = newOpts.outHeight;  
           //���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ  
           float hh = 800f;//�������ø߶�Ϊ800f  
           float ww = 480f;//�������ÿ��Ϊ480f  
           //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��  
           int be = 1;//be=1��ʾ������  
           if (w > h && w > ww) {//�����ȴ�Ļ����ݿ�ȹ̶���С����  
               be = (int) (newOpts.outWidth / ww);  
           } else if (w < h && h > hh) {//����߶ȸߵĻ����ݿ�ȹ̶���С����  
               be = (int) (newOpts.outHeight / hh);  
           }  
           if (be <= 0)  
               be = 1;  
           newOpts.inSampleSize = be;//�������ű���  
           //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
           isBm = new ByteArrayInputStream(baos.toByteArray());  
           bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
           return compressImage(bitmap);//ѹ���ñ�����С���ٽ�������ѹ��  
       }  
       private static Bitmap compressImage(Bitmap image) {  
    	   
           ByteArrayOutputStream baos = new ByteArrayOutputStream();  
           image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��  
           int options = 100;  
           while ( baos.toByteArray().length / 1024>300) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��         
               baos.reset();//����baos�����baos  
               image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
               options -= 10;//ÿ�ζ�����10  
           }  
           ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ���������baos��ŵ�ByteArrayInputStream��  
           Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream��������ͼƬ  
           return bitmap;  
       }
       
       public static void compressImg(String srcPath) {  
    	   BitmapFactory.Options newOpts = new BitmapFactory.Options();  
           //��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
           newOpts.inJustDecodeBounds = true;  
           Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//��ʱ����bmΪ��  
             
           newOpts.inJustDecodeBounds = false;  
           int w = newOpts.outWidth;  
           int h = newOpts.outHeight;  
           Log.i("compress","w:"+w +" h:"+h);
           //���������ֻ��Ƚ϶���800*480�ֱ��ʣ����ԸߺͿ���������Ϊ  
           float hh = 800f;//�������ø߶�Ϊ800f  
           float ww = 480f;//�������ÿ��Ϊ480f  
           //���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��  
           int be = 1;//be=1��ʾ������  
           if (w > h && w > ww) {//�����ȴ�Ļ����ݿ�ȹ̶���С����  
               be = (int) (newOpts.outWidth / ww);  
           } else if (w < h && h > hh) {//����߶ȸߵĻ����ݿ�ȹ̶���С����  
               be = (int) (newOpts.outHeight / hh);  
           }  
           if (be <= 0)  
               be = 1;  
           newOpts.inSampleSize = be;//�������ű���  
           //���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
           bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
           bitmap = compressImage(bitmap);//ѹ���ñ�����С���ٽ�������ѹ��  
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
                   // ��SD���ϴ����ļ�����ҪȨ�ޣ�
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
    	//����ͼƬ����ת�Ƕȣ������䲻��ת������Ҳ������������ת�ĽǶȣ����Դ�ֵ��ȥ��
    	//������ת90�ȣ���ֵExifInterface.ORIENTATION_ROTATE_90����Ҫ�����ֵת��ΪString���͵�
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
			//��תͼƬ ����  
	        Matrix matrix = new Matrix();;  
	        matrix.postRotate(degree);  
	        // �����µ�ͼƬ  
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
