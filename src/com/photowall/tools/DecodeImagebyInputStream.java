package com.photowall.tools;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DecodeImagebyInputStream {

	public static Bitmap decodeImage(InputStream is,int displaypixels)
	{
		Bitmap bmp = null; 
		BitmapFactory.Options opts = new BitmapFactory.Options(); 
		InputStream stream = is; 
		byte[] bytes = getBytes(stream); 
		//这3句是处理图片溢出的begin( 如果不需要处理溢出直接 opts.inSampleSize=1;) 
		opts.inJustDecodeBounds = true; 
		BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts); 
		opts.inSampleSize = computeSampleSize(opts, -1, displaypixels); 
		//end 
		opts.inJustDecodeBounds = false; 
		bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts); 
		return bmp;
	}
	private static byte[] getBytes(InputStream is) { 
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		byte[] b = new byte[2048]; 
		int len = 0; 
		try { 
		while ((len = is.read(b, 0, 2048)) != -1) { 
		baos.write(b, 0, len); 
		baos.flush(); 
		} 
		} catch (IOException e) { 
		e.printStackTrace(); 
		} 
		byte[] bytes = baos.toByteArray(); 
		return bytes; 
		} 
	private static int computeSampleSize(BitmapFactory.Options options, 
			int minSideLength, int maxNumOfPixels) { 
			int initialSize = computeInitialSampleSize(options, minSideLength, 
			maxNumOfPixels); 
			int roundedSize; 
			if (initialSize <= 8) { 
			roundedSize = 1; 
			while (roundedSize < initialSize) { 
			roundedSize <<= 1; 
			} 
			} else { 
			roundedSize = (initialSize + 7) / 8 * 8; 
			} 
			return roundedSize; 
			} 
	
	private static int computeInitialSampleSize(BitmapFactory.Options options, 
			int minSideLength, int maxNumOfPixels) { 
			double w = options.outWidth; 
			double h = options.outHeight; 
			int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math 
			.sqrt(w * h / maxNumOfPixels)); 
			int upperBound = (minSideLength == -1) ? 128 : (int) Math.min( 
			Math.floor(w / minSideLength), Math.floor(h / minSideLength)); 
			if (upperBound < lowerBound) { 
			return lowerBound; 
			} 
			if ((maxNumOfPixels == -1) && (minSideLength == -1)) { 
			return 1; 
			} else if (minSideLength == -1) { 
			return lowerBound; 
			} else { 
			return upperBound; 
			} 
			} 
	
	public static boolean saveImage(Bitmap photo, String spath) {  
        try {  
            BufferedOutputStream bos = new BufferedOutputStream(  
                    new FileOutputStream(spath, false));  
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);  
            bos.flush();  
            bos.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
        return true;  
    } 
}
