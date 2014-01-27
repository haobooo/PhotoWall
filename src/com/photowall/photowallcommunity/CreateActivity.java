package com.photowall.photowallcommunity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lightbox.android.camera.activities.CameraAction;
import com.photowall.bean.PhotoWallTools;
import com.photowall.lazyload.CacheDIR;
import com.photowall.net.AchmInfo;
import com.photowall.net.ErrCode;
import com.photowall.net.HttpSession;
import com.photowall.net.HttpSession.PostImageListener;
import com.photowall.net.UserContent;
import com.photowall.tools.BitmapTools;
import com.photowall.widget.ui.HSingleAblum_Main;
import com.photowall.widget.utils.ToastManager;


public class CreateActivity extends Activity implements OnClickListener, OnItemSelectedListener
    ,OnCheckedChangeListener,PostImageListener
    {
	private ImageView settingsImage;
	
	//guide
	private ViewGroup guide_layer;
	private boolean isTakePhoto = false;
	//
    private static final String TAG = "CreateActivity";
    private ImageView mBackImageView;
    private ImageView mMenuImageView;
    private ImageView mTakePhotoImageView;
    private ImageView mUploadImageView;
//    private Spinner mSpinner;
    private ImageView mPhotoShowImage;
    private View mCreateButton;
    private TextView mInputTitle;
    private RadioGroup mRadioGroup;
    private EditText mCommentEdit;
    private TextView mTitleTextView;
//    private LinearLayout mInputLayout;
    private LinearLayout mPhotoLayout;
    private String[] str;
    private String[] str_id;
    private Bitmap mPicBitmap;
    private String mTitleString;
    private String mAchmMarkString;
    private String mAchmMarkId;
    private String mLocatString;
    private String mAchmComments;
    private boolean isLocation = false;
    private boolean isAchieve = false;
    private boolean isUpload = false;
    private final int IMAGE_TAKE_PHOTO = 1;
    private final int IMAGE_UPLOAD_PHOTO = 2;
    private String mImagePath;
    private String mAchid;
    
    private final int MSG_UPLOAD_POST_FAILED = 0x01;
    private final int MSG_UPLOAD_POST_SUCCESS = 0x02;
    
    private PhotoWallApplication app;
    private HttpSession httpSession;
    private ErrCode err;
    private TypedArray images;
    private AchmInfo achmInfo;
    
    public ProgressDialog mProgressDialog;
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int MEDIA_TYPE_PROGRESS = 3;
    
    
    private Handler mainHandler = new Handler(){

        public void handleMessage(android.os.Message msg) {
//            mProgressDialog.dismiss();
            switch (msg.what) {
                case MSG_UPLOAD_POST_FAILED:
                { 
//                	hideUpoadDialog();
                	if(mProgressDialog.isShowing())
                	mProgressDialog.dismiss();
                    String text="";
                try {
                	if(err!=null)
                    text = err.getErrcode() + "\n"
                            + new String(err.getError().getBytes(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    
                    Toast.makeText(CreateActivity.this, text+"\n"+getString(R.string.tip_upload_error_retry), Toast.LENGTH_SHORT).show();
                    break;
                }
                
                case MSG_UPLOAD_POST_SUCCESS:
                { 
                	if(mProgressDialog.isShowing())
                	mProgressDialog.dismiss();
                    String text="�ϴ��ɹ�";
//                    Toast.makeText(CreateActivity.this, text, Toast.LENGTH_SHORT).show();
                    ToastManager.showToast(CreateActivity.this);
//                    setResult(RESULT_OK);
//                    finish();
                    break;
                }
                case MEDIA_TYPE_PROGRESS:
                {
                	//Toast.makeText(CreateActivity.this, (int)((Double)msg.obj*100)+"%", Toast.LENGTH_SHORT).show();
                	int per = (int)((Double)msg.obj*100);
                	percent.setText(per+"%");
            		progressBar.setProgress(per);
                	
                	break;
                }
            }
        };
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.create_photo);
        initView();
        setTitle();
        app = PhotoWallApplication.getPhotoWallApplication();
        httpSession = app.getHttpSession();
        achmInfo = app.getCurrAchminfo();
//        httpSession.setPostImageListener(this);
        initUploadDialog();
        
        if(PhotoWallApplication.getPhotoWallApplication().isGuideMode())
		{
			guide_layer.setVisibility(View.VISIBLE);
			guide_layer.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if(!isTakePhoto)
					mTakePhotoImageView.performClick();
				}
			});
		}
        else
        {
        	guide_layer.setVisibility(View.GONE);
        }
    }
    
    private void initView() {
    	
    	settingsImage = (ImageView) findViewById(R.id.title_settings);
    	settingsImage.setOnClickListener(this);
		
    	
    	guide_layer = (ViewGroup) findViewById(R.id.guide_layer);
    	
        str = getResources().getStringArray(R.array.photo_classify_name);
        str_id = getResources().getStringArray(R.array.photo_classify_id);
        mBackImageView = (ImageView) findViewById(R.id.title_back);
        mMenuImageView = (ImageView) findViewById(R.id.title_settings);
        mTakePhotoImageView = (ImageView) findViewById(R.id.take_photo);
        mUploadImageView = (ImageView) findViewById(R.id.upload_photo);
        mPhotoShowImage = (ImageView) findViewById(R.id.back_photo_show);
        mCreateButton = findViewById(R.id.create_btn);
        mCreateButton.setOnClickListener(this);
        mInputTitle = (TextView) findViewById(R.id.input_photo_title);
        mInputTitle.addTextChangedListener(titlewatcher);
        mTitleTextView = (TextView) findViewById(R.id.title_text);
//        mInputLayout = (LinearLayout) findViewById(R.id.input_title_layout);
        mPhotoLayout = (LinearLayout) findViewById(R.id.photo_layout);
        
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mRadioGroup.setOnCheckedChangeListener(this);
        mCommentEdit = (EditText) findViewById(R.id.input_photo_comments);
        mCommentEdit.addTextChangedListener(commentwatcher);
        
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.tip_upload_image));
        mProgressDialog.setCanceledOnTouchOutside(false);
        
        images = getResources().obtainTypedArray(R.array.photo_classify_drawable);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.simple_spinner_item, str){
            @Override  
            public View getDropDownView(int position, View convertView, ViewGroup parent) {  
                if(convertView==null){
                    convertView = getLayoutInflater().inflate(R.layout.spinner_dropdown_item, parent, false);  
                }  
                TextView label = (TextView) convertView.findViewById(R.id.label);  
                label.setText(getItem(position));  
                ImageView imageView = (ImageView) convertView.findViewById(R.id.icon);
                Drawable drawable = images.getDrawable(position);
                imageView.setImageDrawable(drawable);
                return convertView;
            }  
       };   
        
//        mSpinner = (Spinner) findViewById(R.id.photo_spinnered);
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
//        mSpinner.setAdapter(adapter);
//        mSpinner.setOnItemSelectedListener(this);
        
        mBackImageView.setOnClickListener(this);
        mMenuImageView.setOnClickListener(this);
        mTakePhotoImageView.setOnClickListener(this);
        mUploadImageView.setOnClickListener(this);
        
    }
    
    private void setTitle() {
        Intent intent = getIntent();
//        if ("Achieve".equals(intent.getStringExtra("title"))) {
//            mTitleTextView.setText(intent.getCharSequenceExtra("title"));
//            mCreateButton.setText("Post");
//            mSpinner.setVisibility(View.GONE);
//            mInputLayout.setVisibility(View.GONE);
//            mAchid = intent.getStringExtra("achid");
//            isAchieve = true;
//        }else {
            mTitleTextView.setText("POST A PHOTO");
//            mSpinner.setVisibility(View.VISIBLE);
//            mInputLayout.setVisibility(View.VISIBLE);
            isAchieve = false;
//        }
        setCreateButtonEnabled();
    }
    
    private void setCreateButtonEnabled(){
        String titleStr = mInputTitle.getText().toString();
        if (isAchieve) {
            boolean isCreateButtonEnabled = (mAchmComments != null && 
                    (!"".equals(mAchmComments) || mAchmComments.trim().equals(""))
                    && isUpload);
//            mCreateButton.setEnabled(isCreateButtonEnabled);
        }else{
            boolean isCreateButtonEnabled = (mAchmComments != null && 
                    (!"".equals(mAchmComments) || mAchmComments.trim().equals(""))
                    && isUpload);
//            mCreateButton.setEnabled(isCreateButtonEnabled);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.title_back:
            finish();
            break;
        case R.id.title_settings:
//            boolean[] isMark = {false,false,true,false,true,true,true,true};
//            PhotoWallTools.showPopupWindow(CreateActivity.this, mMenuImageView, 300, 500, isMark);
        	settings();
        	break;
        case R.id.take_photo:
        	takePicture();
            break;
        case R.id.upload_photo:
            Intent intentUpload = new Intent();
            intentUpload.setClass(CreateActivity.this, HSingleAblum_Main.class);
            startActivityForResult(intentUpload, IMAGE_UPLOAD_PHOTO);
            break;
        case R.id.create_btn:
        	
        	if(PhotoWallApplication.getPhotoWallApplication().isGuideMode())
        	{
        		PhotoWallApplication.getPhotoWallApplication().setGuideMode(false);
        	}
//            mTitleString = mInputTitle.getText().toString();
//                uploadPost();
        	
        	mainHandler.sendEmptyMessage(MSG_UPLOAD_POST_SUCCESS);
            break;
        default:
            break;
        }
    }
    public void settings()
	{
		Intent intent = new Intent(this,PhotoWallSetting.class);
		startActivity(intent);
	}
    public void uploadPost()
    {
    	if(!mProgressDialog.isShowing())
    	 mProgressDialog.show();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                    	
                    	
                    	if(!PhotoWallApplication.getPhotoWallApplication().isNet())
                    	{
                    		mainHandler.sendEmptyMessage(MSG_UPLOAD_POST_SUCCESS);
                    		return;
                    	}
                    	
                        Map<String, String> params = new HashMap<String, String>();
                        Map<String, File> files = new HashMap<String, File>();
                        
                        UserContent userContent = PhotoWallApplication.
                        	getPhotoWallApplication().getUserContent();//PhotoWallTools.mUserContent;
                        if (isAchieve) {
                        	
                        	Log.i("hygupload","isAchieve....");
                            params.put("userid", userContent.getUserId());
                            params.put("user_folder", userContent.getUserFolder());
                            params.put("description", mAchmComments);
                            params.put("achid", achmInfo.getAchid());
                            
                            File file = new File(mImagePath);
                            files.put("imagename", file);
                            boolean flag =  httpSession.uploadPost(HttpSession.UPLOAD_ACHID_POST, params, files);
                            
                            Log.i("hygupload","flag: "+flag);
                            if(!flag)
                            {
                                err =  httpSession.getErrCode();
                                mainHandler.sendEmptyMessage(MSG_UPLOAD_POST_FAILED);
                            } else {
                                mainHandler.sendEmptyMessage(MSG_UPLOAD_POST_SUCCESS);
                            }
                        }else {
                            params.put("userid", userContent.getUserId());
                            params.put("user_folder", userContent.getUserFolder());
                            params.put("description", mAchmComments);
                            params.put("achi_name", mTitleString);
                            params.put("lifestyleid", mAchmMarkId);
                            
                            File file = new File(mImagePath);
                            files.put("imagename", file);
                            boolean flag =  httpSession.uploadPost(HttpSession.UPLOAD_ACHID_POST, params, files);
                            if(!flag)
                            {
                                err =  httpSession.getErrCode();
                                mainHandler.sendEmptyMessage(MSG_UPLOAD_POST_FAILED);
                            } else {
                                mainHandler.sendEmptyMessage(MSG_UPLOAD_POST_SUCCESS);
                            }
                        }
                    }
                }
                ).start();
    }
    
    public static Bitmap cameraBitmap;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

        	//take pictrue
            if (requestCode == IMAGE_TAKE_PHOTO) 
            {
                mPhotoShowImage.setVisibility(View.VISIBLE);
                Bitmap tempPriviewbitmap = null;
                
                if (data != null) {
                	
                       Bundle bundle = data.getExtras();    
                        if (bundle != null) {                 
                        	tempPriviewbitmap = (Bitmap) bundle.get("data"); //get bitmap  
                        } 
                       
                    
                }
                else
                {
                	tempPriviewbitmap = cameraBitmap;
                }
                mImagePath = fileUri.getPath();
                
                mUploadImageView.setVisibility(View.GONE);
                mTakePhotoImageView.setVisibility(View.GONE);
                
                //bitmap too big 
                if(tempPriviewbitmap ==null)
                {
                	BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();  
                	bitmapOptions.inSampleSize = 4;  
                	File file = new File(savepath);  
                	try {
						tempPriviewbitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, bitmapOptions);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
                }
                //rotate degree
                int degree =  BitmapTools.readPictureDegree(savepath);
                Toast.makeText(CreateActivity.this, degree+"", Toast.LENGTH_SHORT).show();
                
                if(degree!=0)
                tempPriviewbitmap = BitmapTools.rotaingImageView(degree,tempPriviewbitmap);
                
                if(tempPriviewbitmap!=null)
                {
                	mPhotoShowImage.setImageBitmap(tempPriviewbitmap);
                }
                
                
                //guide
                if( PhotoWallApplication.getPhotoWallApplication().isGuideMode() )
                {
                	guide_layer.setVisibility(View.GONE);
                	isTakePhoto = true;
                	Toast.makeText(CreateActivity.this, getString(R.string.guide_publish), 
                			Toast.LENGTH_SHORT).show();
                	PhotoWallApplication.getPhotoWallApplication().setGuideMode(false);
                	
                }
            }
            
            //upload
            if (requestCode == IMAGE_UPLOAD_PHOTO) {
//                mImagePath = data.getStringExtra("uri");
//                Bitmap b = (Bitmap) data.getParcelableExtra("bitmap");
                

                
                
				try {
				
					mImagePath = BitmapTools.getOutputCompressFile().getAbsolutePath();
			    
					Bitmap b = BitmapFactory.decodeStream(new FileInputStream(
							BitmapTools.getOutputCompressFile()));
	                mPicBitmap = b;
	                mPhotoShowImage.setVisibility(View.VISIBLE);
	                mPhotoShowImage.setImageBitmap(b);
	                mUploadImageView.setVisibility(View.GONE);
	                mTakePhotoImageView.setVisibility(View.GONE);
                
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
            }
            isUpload = true;
        }else{
            isUpload = false;
        }
        setCreateButtonEnabled();
    }
    
    
    public Bitmap getBitmapFromCameraURI(String path)
    {
			    	Log.d(TAG,
			        "data IS null, file saved on target position.");
			// If there is no thumbnail image data, the image
			// will have been stored in the target output URI.
			
			// Resize the full image to fit in out image view.
			int width = mPhotoLayout.getWidth();
			int height = mPhotoLayout.getHeight();
			
			BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
			factoryOptions.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(fileUri.getPath(), factoryOptions);
			
			int imageWidth = factoryOptions.outWidth;
			int imageHeight = factoryOptions.outHeight;
			
			// Determine how much to scale down the image
			int scaleFactor = Math.min(imageWidth / width, imageHeight
			        / height);
			
			// Decode the image file into a Bitmap sized to fill the
			// View
			factoryOptions.inJustDecodeBounds = false;
			factoryOptions.inSampleSize = scaleFactor;
			factoryOptions.inPurgeable = true;
			
			Bitmap tempbitmap = BitmapFactory.decodeFile(path,
			        factoryOptions);
			
			return tempbitmap;

    }
    //edittext �¼�����
    private TextWatcher titlewatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable arg0) {
            
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {
            
        }

        @Override
        public void onTextChanged(CharSequence text, int arg1, int arg2,
                int arg3) {
            setCreateButtonEnabled();
        }
        
    };
    private TextWatcher commentwatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable arg0) {
            if (isLocation) {
                mLocatString = mCommentEdit.getText().toString();
            }else{
                mAchmComments = mCommentEdit.getText().toString();
            }
            setCreateButtonEnabled();
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence text, int arg1, int arg2,
                int arg3) {
            
        }
        
    };

    //spinner �¼�����
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
            long arg3) {
        mAchmMarkString = str[arg2];
        mAchmMarkId = str_id[arg2];
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        
    }
    //RadioGroup�����¼�
    @Override
    public void onCheckedChanged(RadioGroup radiogroup, int checkedId){
        switch (checkedId) {
        case R.id.location_btn:
            isLocation = true;
            mCommentEdit.setText("");
            break;
        case R.id.tag_btn:
            isLocation = false;
            mCommentEdit.setText("");
            break;

        default:
            break;
        }
    }
    
    public static String savepath;
    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type)
    {
    	File mediafile = getOutputMediaFile(type);
    	savepath = mediafile.getAbsolutePath();
        return Uri.fromFile(mediafile);
    }
    
    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type)
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

            Log.d(TAG, "Successfully created mediaStorageDir: "
                    + mediaStorageDir);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d(TAG, "Error in Creating mediaStorageDir: "
                    + mediaStorageDir);
        }

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists())
        {
            if (!mediaStorageDir.mkdirs())
            {
                // ��SD���ϴ����ļ�����ҪȨ�ޣ�
                // <uses-permission
                // android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
                Log.d(TAG,
                        "failed to create directory, check if you have the WRITE_EXTERNAL_STORAGE permission");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        }
        else if (type == MEDIA_TYPE_VIDEO)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        }
        else
        {
            return null;
        }

        return mediaFile;
    }
    
    
    
    /**
     * take photo
     */
    
    public void takePicture()
    {
    	Intent intentTakephoto = new Intent(CameraAction.IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intentTakephoto.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intentTakephoto, IMAGE_TAKE_PHOTO);
    }

	@Override
	public void uploadingProgress(double percent) {
		
		Message msg = new Message();
		msg.what = MEDIA_TYPE_PROGRESS;
		msg.obj = percent;
		mainHandler.sendMessage(msg);
	}

	@Override
	public void uploadComplete() {
		
	}
	
	//
	
	private AlertDialog dialog;
	private TextView percent;
	private ProgressBar progressBar;
	public void initUploadDialog()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(false);
		View view = LayoutInflater.from(this).inflate(R.layout.upload_dialog, null);
		percent = (TextView) view.findViewById(R.id.upload_percent);
		progressBar = (ProgressBar)view.findViewById(R.id.upload_progressbar);
		builder.setView(view);
		builder.setTitle("�ļ��ϴ�");
		dialog = builder.create();
	}
	public void showUploadDialog()
	{
		percent.setText("0%");
		progressBar.setProgress(0);
		dialog.show();
	}
	public void hideUpoadDialog()
	{
		dialog.dismiss();
	}
	
	@Override
    public void onWindowFocusChanged(boolean hasFocus) {
    	
    	super.onWindowFocusChanged(hasFocus);
    }
}




