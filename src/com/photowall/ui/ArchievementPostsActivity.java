package com.photowall.ui;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.photowall.adapters.ArchievenmentPostsAdapter;
import com.photowall.adapters.ArchievenmentPostsAdapter.LikeClickListener;
import com.photowall.bean.AsyncVideoItemImageLoader;
import com.photowall.bean.AsyncVideoItemImageLoader.ImageCallback;
import com.photowall.net.AchmInfo;
import com.photowall.net.ErrCode;
import com.photowall.net.HomePostInfo;
import com.photowall.net.HttpSession;
import com.photowall.net.UserContent;
import com.photowall.photowallcommunity.CreateActivity;
import com.photowall.photowallcommunity.PhotoWallApplication;
import com.photowall.photowallcommunity.R;

public class ArchievementPostsActivity extends Activity 
implements OnClickListener,LikeClickListener
{
	//guide
	private RelativeLayout guide_layer;
	private ImageView guide_hand;
	
	///
	private ImageView back;
	private ImageView more;
	private ImageView archi_cover_image;
	private LinearLayout archi_type_image;
	private TextView archi_title;
	private TextView archi_detals;
	private TextView num_chanlleng;
	private TextView num_archieve;
	
	private Button achm_achieve;
	
	public ProgressDialog mProgressDialog;
	
	///////////////////////////////////////////////////
	private PhotoWallApplication app;
	private int[] screensize;
	private AchmInfo machmInfo;
	private UserContent user;
	private HttpSession httpSession;
	 private ErrCode err;
	private String likepostid;
	
	 //////////////////////////////////
	private GridView gridView;
	private ArchievenmentPostsAdapter adapter;
	private List<HomePostInfo> mpostInfolist = new ArrayList<HomePostInfo>();
	
	private AsyncVideoItemImageLoader asyncImageLoader;
	
	private final int MSG_GET_POST_SUCCESS = 100;
	private final int MSG_GET_DATA_FAILED = 101;
	private final int MSG_GET_IMAGE_SUCCESS = 102;
	private final int MSG_UPDATE_LIKE = 103;
	private final int MSG_UPDATE_LIKE_ERROR = 104;
	
	private final int POST_RESULT=1000;
	private boolean isPost = false;
	
	private Handler mainHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			switch (msg.what) {
				case MSG_GET_POST_SUCCESS:
					if(isPost&&mProgressDialog.isShowing())
						{
						mProgressDialog.dismiss();
						isPost = false;
						}
					adapter.notifyDataSetChanged();
					break;
				case MSG_GET_IMAGE_SUCCESS:
				{
					if(mProgressDialog.isShowing())mProgressDialog.dismiss();
					break;
				}
				case MSG_GET_DATA_FAILED:
				{
					String text="";
                    try {
                        if (err.getErrcode()!=null) {
                            text = err.getErrcode()+"\n"+new String(err.getError().getBytes(),"UTF-8");
                        }else {
                            text = "服务器无响应";
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    
                    Toast.makeText(ArchievementPostsActivity.this, text, Toast.LENGTH_SHORT).show();
					break;
				}
				case MSG_UPDATE_LIKE:
				{
					if(mProgressDialog.isShowing())
						mProgressDialog.dismiss();
					HomePostInfo postinfo = mpostInfolist.get(msg.arg1);
					postinfo.setPlikecount((Integer.valueOf(postinfo.getPlikecount())+1)+"");
					adapter.notifyDataSetChanged();
					 Toast.makeText(ArchievementPostsActivity.this, "update like success!", Toast.LENGTH_SHORT).show();
					break;
				}
				case MSG_UPDATE_LIKE_ERROR:
				{
					if(mProgressDialog.isShowing())
						mProgressDialog.dismiss();
					String text="";
                    try {
                        if (err!=null && err.getErrcode()!=null && err.getError()!=null) {
                            text = err.getErrcode()+"\n"+new String(err.getError().getBytes(),"UTF-8");
                        }else {
                            text = "服务器无响应";
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    
                    Toast.makeText(ArchievementPostsActivity.this, text, Toast.LENGTH_SHORT).show();
					break;
				}
				default:
					break;
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.archievement_post_layout);
		//
		app = PhotoWallApplication.getPhotoWallApplication();
		screensize = app.getScreenwh();
		machmInfo = app.getCurrAchminfo();
		user = app.getUserContent();
		httpSession = app.getHttpSession();
		
		asyncImageLoader = new AsyncVideoItemImageLoader(getApplicationContext());
		//
		initViews();
		
		if(PhotoWallApplication.getPhotoWallApplication().isNet())
		{
			initImage();
			getAchiPost();
		}
		
		if(PhotoWallApplication.getPhotoWallApplication().isLeadingMode())
		{
			guide_layer.setVisibility(View.VISIBLE);
			guide_layer.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
//					Toast.makeText(ArchievementPostsActivity.this,
//							"post", Toast.LENGTH_SHORT).show();
					
					postImageToserver();
				}
			});
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!PhotoWallApplication.getPhotoWallApplication().isLeadingMode())
		{
			guide_layer.setVisibility(View.GONE);
		}
	}
	public void initViews()
	{
		guide_layer = (RelativeLayout) findViewById(R.id.guide_layer);
    	guide_hand = (ImageView) findViewById(R.id.guide_hand);
		
		back = (ImageView) findViewById(R.id.back);
		more = (ImageView) findViewById(R.id.more);
		
		archi_cover_image = (ImageView) findViewById(R.id.archi_cover_image);
		archi_type_image = (LinearLayout) findViewById(R.id.archi_type_image);
		
		archi_title = (TextView) findViewById(R.id.archi_title);
		archi_detals = (TextView) findViewById(R.id.archi_detals);
		
		num_chanlleng = (TextView) findViewById(R.id.num_chanlleng);
		num_archieve = (TextView) findViewById(R.id.num_archieve);
		//
		gridView = (GridView) findViewById(R.id.gridView);
		adapter = new ArchievenmentPostsAdapter(this, mpostInfolist, 0);
		adapter.setLikeListener(this);
		gridView.setAdapter(adapter);
		//
		if(machmInfo != null)
		{
		archi_title.setText(machmInfo.getAchiname());
		archi_detals.setText(machmInfo.getAchides());
		}
		//
		achm_achieve = (Button) findViewById(R.id.achm_achieve);
		achm_achieve.setOnClickListener(this);
		back.setOnClickListener(this);
		//###################
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
			}
		});
		
	}
	public void initImage()
	{
		archi_cover_image.setTag(machmInfo.getAchiimg());
		Drawable cachedImage = asyncImageLoader.loadDrawable(machmInfo.getAchiimg(), new ImageCallback() { 
            @Override
            public void imageLoaded(Drawable imageDrawable, String imageUrl) {
            	//
            	Bitmap bitmap = ((BitmapDrawable)imageDrawable).getBitmap();
            	if(bitmap.getWidth()!=0)
            	{
	            	float ast = ((screensize[0]*1.0f)/bitmap.getWidth());
	            	Matrix matrix = new Matrix();
	            	 matrix.postScale(ast,ast); 
	            	Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
	            	archi_cover_image.setImageBitmap(resizeBmp);
	            	mainHandler.sendEmptyMessage(MSG_GET_IMAGE_SUCCESS);
            	}
            }  
        });
	}
	
	public void getAchiPost()
    {
		if(mProgressDialog == null)
		{
			mProgressDialog = new ProgressDialog(this);
;			mProgressDialog.setMessage(getString(R.string.tip_please_wait_content));
			mProgressDialog.setCanceledOnTouchOutside(false);
		}
        mProgressDialog.show();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("userid",user.getUserId());
                        Log.i("photowall","id: "+user.getUserId() );
                        params.put("achid",machmInfo.getAchid());
                        params.put("start", "0");
                        params.put("offset", "1000");
                        
                       boolean flag =  httpSession.getAchiPost(HttpSession.POST_LIST_BY_ACHID, params);
                        
                       if(!flag)
                       {
                          err =  httpSession.getErrCode();
                          mainHandler.sendEmptyMessage(MSG_GET_DATA_FAILED);
                       } else {
                    	   mpostInfolist.clear();
                    	   mpostInfolist.addAll(httpSession.getmHomePostInfos());
                           mainHandler.sendEmptyMessage(MSG_GET_POST_SUCCESS);
                       }
                    }
                }
                ).start();
    }

	public void updateLike(final int pos,final String likeid)
	{
		if(!mProgressDialog.isShowing())
		mProgressDialog.show();
		new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("userid",user.getUserId());
                        params.put("postid",likeid);
                        boolean flag =  httpSession.updatePostLike(HttpSession.UPDATE_POST_LIKE, params);
                        
                        if(!flag)
                        {
                        	err = httpSession.getErrCode();
                            mainHandler.sendEmptyMessage(MSG_UPDATE_LIKE_ERROR);
                        }
                        else
                        {
                             Message msg = new Message();
                             msg.what = MSG_UPDATE_LIKE;
                             msg.arg1 = pos;
                             mainHandler.sendMessage(msg);
                        }
                       
                    }
                }
                ).start();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.achm_achieve:
			postImageToserver();
			break;
		case R.id.back:
		{
			//by black crystal
			finish();
		}
		default:
			break;
		}
		
	}
	
	public void postImageToserver()
	{
		Intent achieveIntent = new Intent();
        achieveIntent.setClass(ArchievementPostsActivity.this, CreateActivity.class);
        achieveIntent.putExtra("title", "Achieve");
//        achieveIntent.putExtra("achid", machmInfo.getAchid());
        startActivityForResult(achieveIntent, POST_RESULT);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode != RESULT_OK)return;
		switch (requestCode) {
		case POST_RESULT:
		{
			isPost = true;
			getAchiPost();
		}
			break;

		default:
			break;
		}
	}

	@Override
	public void onclickBypostID(int pos,String id) {
		// TODO Auto-generated method stub
		updateLike(pos,id);
	}
	@Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	if(hasFocus && PhotoWallApplication.getPhotoWallApplication().isLeadingMode() 
//    			&&  guide_hand.getVisibility() == View.GONE
    			)
    	{  int[] location = new int[2];
    		achm_achieve.getLocationOnScreen(location);
    	    int x = location[0];
		    int y = location[1];
		    guide_hand.setVisibility(View.VISIBLE);
		    guide_hand.layout(x, y, x+guide_hand.getWidth(), y+guide_hand.getHeight());
    	}
    	super.onWindowFocusChanged(hasFocus);
    }
}
