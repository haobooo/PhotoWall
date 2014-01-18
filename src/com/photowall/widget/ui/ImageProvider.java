package com.photowall.widget.ui;
/**
 * @author yaogang.hao@tct-nj.com
 * This class is a helper class for image get data from media database
 * 
 */
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class ImageProvider implements AbstructProvider {
    private Context context;

    public ImageProvider(Context context) {
        this.context = context;
    }

    @Override
    public List<?> getList() {
        List<Image> list = null;
        if (context != null) {
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, null);
            if (cursor != null) {
                list = new ArrayList<Image>();
                while (cursor.moveToNext()) {
                    int id = cursor
                            .getInt(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                    String title = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));
                    String path = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    String displayName = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                    String mimeType = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE));
                    String dirname = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                    
                    long size = cursor
                            .getLong(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));
                    
                    int degree = cursor
                    .getInt(cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION));
                    Image audio = new Image(id, title, displayName, mimeType,path
                            ,degree);
                    
                    list.add(audio);
                }
                cursor.close();
            }
        }
        return list;
    }
    
    
    public List<ImageAlbum> getAlbumList()
    {
        List<ImageAlbum> list = null;
        if (context != null) {
            String[] projection = {
                    
                    MediaStore.Images.Media._ID, 
                    MediaStore.Images.Media.TITLE,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.MIME_TYPE,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.Media.BUCKET_ID,
                   
                    "COUNT(*) AS totalNum",
                    MediaStore.Images.Media.ORIENTATION,
                    
            };
            String where ="0==0) Group by ("+MediaStore.Images.Media.BUCKET_ID;
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, where,
                    null, null);
            list = new ArrayList<ImageAlbum>();
            if (cursor != null) {
               
                while (cursor.moveToNext()) {
                    int id = cursor
                            .getInt(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                    String title = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));
                    String path = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    String displayName = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                    String mimeType = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE));
                    String dirname = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                    if(path.lastIndexOf("/") != -1)
                        path = path.substring(0, path.lastIndexOf("/"));
                    String buket_id = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID));
                    String count = cursor.getString(7);
                    
                    int degree = cursor
                    .getInt(cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION));
                    
                    
                    ImageAlbum album = new ImageAlbum(count, dirname, id, buket_id, path,degree);
                    list.add(album);
                }
                cursor.close();
            }
        }
        return list;
    }
    
    public List<Image> getAlbumChildList(ImageAlbum imageAlbum)
    {
        List<Image> list = null;
        if (context != null) {
            String[] projection = {
                    
                    MediaStore.Images.Media._ID, 
                    MediaStore.Images.Media.TITLE,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.MIME_TYPE,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.Media.BUCKET_ID,
                    MediaStore.Images.Media.ORIENTATION
                    
            };
            String where =MediaStore.Images.Media.BUCKET_ID + "="+imageAlbum.getBuket_id();
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, where,
                    null, null);
            if (cursor != null ) {
                list = new ArrayList<Image>();
                while (cursor.moveToNext()) {
                    int id = cursor
                            .getInt(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                    String title = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.TITLE));
                    String path = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    String displayName = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                    String mimeType = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE));
                    String dirname = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                    String buket_id = cursor
                            .getString(cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID));
                    int degree = cursor
                    .getInt(cursor
                            .getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION));
                    
                    Image image = new Image(id, title, displayName, mimeType,path,degree);
                    list.add(image);
                }
                cursor.close();
            }
            
        }
        return list;
    }

}
