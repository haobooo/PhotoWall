package com.photowall.widget.ui;
/**
 * @author yaogang.hao@tct-nj.com
 * This is the main class to show all pictures be to checked.
 * 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.photowall.photowallcommunity.R;
import com.photowall.tools.BitmapTools;


public class HSingleAblum_Main extends Activity {
    
    //scrolling flag, judget if the gridview is scrolling.
    public static boolean isScrolling = false;
    /**************Hander Message IDs***************/
    private final int MSG_LOAD_ALBUM_SUCCESS = 0X01;
    private final int MSG_LOAD_ALBUMLIST_SUCCESS = 0X02;
    private final int MSG_HANDLE_IMAGE = 0X03;
    
    private final int MSG_ADD_SELECTED_IMAGE = 0X11;
    private final int MSG_DEL_SELECTED_IMAGE = 0X12;
    
    /**************Widget views********************/
    private FrameLayout album_content;
    private ListView ablumListView;
    private GridView album_grid;
    private ProgressDialog progressDialog;
    
    private Button btn_back;
    private Button btn_cancel;
    private TextView album_title;
    private TextView tv_selected;
    
    /********************Data list*****************/
    List<ImageAlbum> albumList = new ArrayList<ImageAlbum>();
    List<Image>  albumchirldlist = null; 
    Map<Integer, String> selectedImageMap = new HashMap<Integer, String>();
    List<Image> selectedImagelist = new ArrayList<Image>();
    static ArrayList<String> pathlist = new ArrayList<String>();
    /********************Data Adapter******************/
    AlbumListAdater albumListAdater = null;
    ImageGridAdapter imageGridAdapter = null;
    /*******************Flags status ***********************/
  
    private final int LIST_VIEW = 1;
    private final int GRID_VIEW = 2;
    private int CURR_VIEW = LIST_VIEW;
    private int CURR_ALBUM_POSITION = -1;
    
    /******** Message handler****************/
    private Handler mainHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
            case MSG_LOAD_ALBUM_SUCCESS:
                {
                    albumListAdater = new AlbumListAdater(HSingleAblum_Main.this, albumList);
                    ablumListView.setAdapter(albumListAdater);
                    progressDialog.dismiss();
                    break;
                }
            case MSG_LOAD_ALBUMLIST_SUCCESS:
                {
                    imageGridAdapter = new ImageGridAdapter(HSingleAblum_Main.this, albumchirldlist);
                    album_grid.setAdapter(imageGridAdapter);
                    album_grid.setOnItemClickListener(albumChildListener);
                    //add scroll listener
                    album_grid.setOnScrollListener(scrollListener);
                    progressDialog.dismiss();
                    break;
                }
            case MSG_ADD_SELECTED_IMAGE:
                {
                    break;
                }
            case MSG_DEL_SELECTED_IMAGE:
                {
                    break;
                }
            case MSG_HANDLE_IMAGE:
            {
//            	 Intent data = new Intent();
//                 data.putExtra("uri", albumchirldlist.get(pos).getPath());
//                 data.putExtra("bitmap", albumchirldlist.get(pos).getBitmap());
                 setResult(RESULT_OK);
                 finish();
            	break;
            }
            
            }
        };
    };

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.single_album_main);
        createDialog();
       
        findViews();
        getAlbumList();
        setListeners();
     }
    public void findViews()
    {
        album_content = (FrameLayout) findViewById(R.id.album_content);
        ablumListView = (ListView)findViewById(R.id.album_list);
        album_grid = (GridView)findViewById(R.id.album_grid);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        
        btn_back.setOnClickListener(buttonListeners);
        btn_cancel.setOnClickListener(buttonListeners);
        
        album_title = (TextView) findViewById(R.id.album_title);
        
        btn_back.setVisibility(View.GONE);
    }
    public void createDialog()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.tip_please_wait_content));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
    }
    public void setListeners()
    {
        ablumListView.setOnItemClickListener(albumListener);
    }
    ////////////////////////////////////Threads /////////////////////////////////
    public void getAlbumList()
    {
        //show dialog for a tip
        progressDialog.show();
        //start a Asynchronized thread
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                ImageProvider provider = new ImageProvider(HSingleAblum_Main.this);
                albumList = (List<ImageAlbum>) provider.getAlbumList();
                for(ImageAlbum tempalbum : albumList)
                {
                    int id =  tempalbum.getImageid();
                    Bitmap thumbNail = MediaStore.Images.Thumbnails.getThumbnail(
                            getContentResolver(), 
                            id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
                    if(thumbNail!=null)
                    thumbNail = BitmapTools.rotaingImageView(tempalbum.getDegree(), thumbNail);
                    
                    tempalbum.setThumbNail(thumbNail);
                }
                mainHandler.sendEmptyMessage(MSG_LOAD_ALBUM_SUCCESS);
            }
        }).start();
    }
    /**
     * 
     * @author yaogang.hao
     * get the album list
     */
    private class AlbumListThread extends Thread
    {
        private ImageAlbum mImageAlbum;
        
        public AlbumListThread(ImageAlbum album)
        {
            this.mImageAlbum = album;
            start();
        }
        
        @Override
        public void run() {
            //judge if already get the children image list.
            if(null == this.mImageAlbum.getChildImageslist())
            {
                List<Image> albumchildlist = getAlbumChildList(this.mImageAlbum);
                this.mImageAlbum.setChildImageslist(albumchildlist);
            }
            albumchirldlist = this.mImageAlbum.getChildImageslist();
            mainHandler.sendEmptyMessage(MSG_LOAD_ALBUMLIST_SUCCESS);
        }
    }
    /**
     * get the children of album
     * @param imageAlbum
     * @return
     */
    public List<Image> getAlbumChildList(ImageAlbum imageAlbum)
    {
        List<Image>  list = new ArrayList<Image>();
        ImageProvider provider = new ImageProvider(HSingleAblum_Main.this);
        list = provider.getAlbumChildList(imageAlbum);
        
        return list;
    }
    ////////////////////////////////////////////////////
    ///  Listeners 
    ////////////////////////////////////////////////////
    
    public void switchView(int flag)
    {
        if(flag == GRID_VIEW)
        {
            album_content.bringChildToFront(ablumListView);
            album_grid.setVisibility(View.GONE);
            ablumListView.setVisibility(View.VISIBLE);
            CURR_VIEW = LIST_VIEW;
            btn_back.setText("home");
            btn_back.setVisibility(View.GONE);
            album_title.setText(R.string.select_album);
            
            if(albumchirldlist != null)
            {
                albumchirldlist = null;
            }
            imageGridAdapter = null;
            album_grid.setOnItemClickListener(null);
            album_grid.setAdapter(null);
            CURR_ALBUM_POSITION = -1;
            
        }else if(flag == LIST_VIEW)
        {
            album_content.bringChildToFront(album_grid);
            ablumListView.setVisibility(View.GONE);
            album_grid.setVisibility(View.VISIBLE);
            CURR_VIEW = GRID_VIEW;
            btn_back.setText(R.string.btn_return);
            btn_back.setVisibility(View.VISIBLE);
        }
    }
    AdapterView.OnItemClickListener albumListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
            
            //switch view
            switchView(LIST_VIEW);
            CURR_ALBUM_POSITION = pos;
            ImageAlbum tempalbum = albumList.get(pos);
            album_title.setText(tempalbum.getAblumName());
            progressDialog.show();
            new AlbumListThread(tempalbum);
        }
    };
    View.OnClickListener buttonListeners = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
            int id = v.getId();
            
            switch (id) {
            
                case R.id.btn_back://return back button
                {
                    if(CURR_VIEW == GRID_VIEW)
                    {
                        switchView(GRID_VIEW);
                    }
                    else
                    {
                        goback();
                    }
                    break;
                }
                case R.id.btn_cancel:
                {
                    goback();
                    break;
                }

            }
        }
    };
    
    AdapterView.OnItemClickListener albumChildListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
            
            if(CURR_ALBUM_POSITION == -1 && albumchirldlist!= null) return;
            final int selectedpos = pos;
          //yaogang.hao for image
            new Thread(
            		new Runnable() {
						
						@Override
						public void run() {
							//1 compress
							BitmapTools.compressImg(albumchirldlist.get(selectedpos).getPath());
							//2 rotate
//							int degree = BitmapTools.readPictureDegree(BitmapTools.
//									getOutputCompressFile().getAbsolutePath());
							int degree = albumchirldlist.get(selectedpos).getDegree();
							
								BitmapTools.saveRotateImage(degree,BitmapTools.
										getOutputCompressFile().getAbsolutePath());
								
							mainHandler.sendEmptyMessage(MSG_HANDLE_IMAGE);
						}
					}).start();
           
        }
    };
    /**
     * Scroll listeners
     * 
     */
    AbsListView.OnScrollListener scrollListener =  new AbsListView.OnScrollListener() {
        
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //no scrolling, idle state
            if(scrollState ==  AbsListView.OnScrollListener.SCROLL_STATE_IDLE)
            {
                isScrolling = false;
                if(imageGridAdapter != null)
                {
                    imageGridAdapter.notifyDataSetChanged();
                }
            }
            //custom is fling the view. scrolling state
            else if(scrollState ==  AbsListView.OnScrollListener.SCROLL_STATE_FLING|| scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
            {
                isScrolling = true;
            }
                
            
        }
        
        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                int totalItemCount) {
            
        }
    };
    /////////////////////////
    
    protected void onDestroy() {
        
        if(albumList != null)
            albumList.clear();
        if(albumchirldlist != null)
            albumchirldlist.clear();
        if(selectedImagelist != null)
            selectedImagelist.clear();
        super.onDestroy();
    };
    @Override
    public void onBackPressed() {
        goback();
        super.onBackPressed();
    }
    public void goback()
    {
//        Intent go_back = new Intent();
//        go_back.setClass(HAblum_Main.this, com.jrdcom.android.gallery3d.app.Gallery.class);
//        //yaogang.hao for PR 499412 Need tap back key twice to exit gallery after exit joint screen.
//        go_back.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        startActivity(go_back);
//        finish();
    }
    
}

