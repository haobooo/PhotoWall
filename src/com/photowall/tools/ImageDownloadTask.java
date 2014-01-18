package com.photowall.tools;

import java.io.ByteArrayOutputStream; 
import java.io.IOException; 
import java.io.InputStream; 
import java.net.MalformedURLException; 
import java.net.URL; 
import android.graphics.Bitmap; 
import android.graphics.BitmapFactory; 
import android.os.AsyncTask; 
import android.widget.ImageView; 
public class ImageDownloadTask extends AsyncTask<Object, Object, Bitmap> { 
private ImageView imageView = null; 
/*** 
* �����ȡ���ֻ��ķֱ��ʴ�С 
* */ 
public void setDisplayWidth(int width) { 
	_displaywidth = width; 
} 
public int getDisplayWidth() { 
	return _displaywidth; 
} 
public void setDisplayHeight(int height) { 
	_displayheight = height; 
} 
public int getDisplayHeight() { 
	return _displayheight; 
} 
public int getDisplayPixels() { 
	return _displaypixels; 
} 
private int _displaywidth = 480; 
private int _displayheight = 800; 
private int _displaypixels = _displaywidth * _displayheight; 
@Override 
protected Bitmap doInBackground(Object... params) { 
// TODO Auto-generated method stub 
	Bitmap bmp = null; 
	imageView = (ImageView) params[1]; 
	try { 
	String url = (String) params[0]; 
	bmp = getBitmap(url, _displaypixels,true); 
	} catch (Exception e) { 
	return null; 
	} 
	return bmp; 
} 
protected void onPostExecute(Bitmap result) { 
	if (imageView != null&&result!=null) { 
	imageView.setImageBitmap(result); 
	if (null != result && result.isRecycled() == false) 
	System.gc(); 
	} 
} 
/** 
* ͨ��URL�������ͼƬ����:http://www.xxxxxx.com/xx.jpg 
* */ 
public Bitmap getBitmap(String url, int displaypixels, Boolean isBig) throws MalformedURLException, IOException { 
	Bitmap bmp = null; 
	BitmapFactory.Options opts = new BitmapFactory.Options(); 
	InputStream stream = new URL(url).openStream(); 
	byte[] bytes = getBytes(stream); 
	//��3���Ǵ���ͼƬ�����begin( �������Ҫ�������ֱ�� opts.inSampleSize=1;) 
	opts.inJustDecodeBounds = true; 
	//by crystal
	BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts); 
	opts.inSampleSize = computeSampleSize(opts, -1, displaypixels); 
	//end 
	opts.inJustDecodeBounds = false; 
	opts.inPurgeable = true;
	opts.inInputShareable = true;  
     // 3. ���ٶ�Aphla ͨ��  
//	opts.inPreferredConfig = Bitmap.Config.RGB_565; 
	//by black crystal
	bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts); 
//	bmp = BitmapFactory.decodeStream(stream,null,opts);
	return bmp; 
} 
/** 
* ������ת��btyle[]���� 
* */ 
private byte[] getBytes(InputStream is) { 
	ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
	byte[] b = new byte[2048]; 
	int len = 0; 
	try { 
	while ((len = is.read(b, 0, 2048)) != -1) { 
	baos.write(b, 0, len); 
	baos.flush(); 
	} 
	} catch (IOException e) { 
		//by black crystal ignore error
//	e.printStackTrace(); 
	} 
	byte[] bytes = baos.toByteArray(); 
	return bytes; 
} 
/**** 
* ����ͼƬbitmap size exceeds VM budget ��Out Of Memory �ڴ������ 
*/ 
private int computeSampleSize(BitmapFactory.Options options, 
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
private int computeInitialSampleSize(BitmapFactory.Options options, 
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
} 
