package com.photowall.photowallcommunity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.lightbox.android.camera.util.MyLocation;
import com.lightbox.android.camera.util.MyLocation.LocationResult;
import com.photowall.net.AchmInfo;
import com.photowall.net.HttpSession;
import com.photowall.net.UserContent;

import android.app.Application;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.OrientationEventListener;

public class PhotoWallApplication extends Application {
 //#############3
	
	@SuppressWarnings("unused")
	private static final String TAG = "CameraApplication";

	public static final int JPEG_HIGH_QUALITY = 90;
	
	private MyOrientationEventListener mOrientationEventListener;
	private long mLocationLastUpdateTime = 0;
	private ArrayList<OrientationChangeListener> mOrientationChangeListeners = new ArrayList<OrientationChangeListener>();
	private int mLastKnownOrientation = -1;

	// Current location & nearby places
	public double lat = 0;
	public double lng = 0;
	public String locStr = "";
	//ÊÇ·ñÐèÒªÍøÂç
	private boolean isNet = false;
	private boolean isLeadingMode = false;
	//
    private static PhotoWallApplication app ;
    private boolean isLogin = false;
    
    private HttpSession session;
    
    //current user info
    private UserContent userContent = new UserContent();
    //current achievement information
    private AchmInfo  currAchminfo;
    
    private int[] screenwh= new int[2];
    
    private boolean isGuideMode = false;
    
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        session = new HttpSession();
        //
        mOrientationEventListener = new MyOrientationEventListener(getApplicationContext());
		mOrientationEventListener.enable();
    }
    
    public static PhotoWallApplication getPhotoWallApplication()
    {
        return app;
    }
    public HttpSession getHttpSession()
    {
        return session;
    }

    public UserContent getUserContent() {
        return userContent;
    }

    public void setUserContent(UserContent userContent) {
        this.userContent = userContent;
    }

	public AchmInfo getCurrAchminfo() {
		return currAchminfo;
	}

	public void setCurrAchminfo(AchmInfo currAchminfo) {
		this.currAchminfo = currAchminfo;
	}

	public int[] getScreenwh() {
		return screenwh;
	}

	public void setScreenwh(int[] screenwh) {
		this.screenwh = screenwh;
	}
    
    public void login()
    {
    	isLogin = true;
    }
    public void loginOut()
    {
    	isLogin = false;
    }
    /////
    public void registerOrientationChangeListener(OrientationChangeListener listener) {
		mOrientationChangeListeners.add(listener);
	}
	
	public void deregisterOrientationChangeListener(OrientationChangeListener listener) {
		mOrientationChangeListeners.remove(listener);
	}
	
	public interface OrientationChangeListener {
		public void onOrientationChanged(int orientation);
	}
	
	public int getLastKnownOrientation() {
		return mLastKnownOrientation;
	}
	
	public void requestLocationUpdate(boolean forceUpdate) {
		// Only request the location once an hour unless forceUpdate is set
		if (!forceUpdate && (System.currentTimeMillis() - mLocationLastUpdateTime < 1000 * 60 * 60)) {
			return;
		}

		mLocationLastUpdateTime = System.currentTimeMillis();
		
		MyLocation myLocation = new MyLocation(this);
		myLocation.requestCurrentLocation(new LocationResult() {
			@Override
			public void gotLocation(final android.location.Location location) {
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						updateWithNewLocation(location);
					}
				});
				thread.start();				
			}
		});
    }
	
	public void setLocation(double _lat, double _lng, String _locStr) {
		lat = _lat;
		lng = _lng;
		locStr = _locStr;
	}
	
	public void updateWithNewLocation(android.location.Location loc) {
		String locStr;

		if (loc != null) {
			double lat = loc.getLatitude();
			double lng = loc.getLongitude();
			String addrStr = "";

			Geocoder gc = new Geocoder(this, Locale.getDefault());
			try {
				List<Address> addresses = gc.getFromLocation(lat, lng, 1);
				StringBuilder sb = new StringBuilder();
				if (addresses.size() > 0) {
					Address addr = addresses.get(0);

					for (int i = 0; i < addr.getMaxAddressLineIndex() && i < 1; i++) {
						sb.append(addr.getAddressLine(i) + " ");
					}
					addrStr = sb.toString();
				}
			} catch (IOException e) {
			}

			locStr = addrStr;

			this.lat = lat;
			this.lng = lng;
		} else {
			locStr = "Location not found";

			this.lat = 0;
			this.lng = 0;
		}
		this.locStr = locStr;
	}
	

	//----------------------------------------------
	// MyOrientationEventListener

	public class MyOrientationEventListener extends OrientationEventListener {
		public MyOrientationEventListener(Context context) {
			super(context);
		}

		@Override
		public void onOrientationChanged(int orientation) {
			mLastKnownOrientation = orientation;
			for (OrientationChangeListener listener : mOrientationChangeListeners) {
				listener.onOrientationChanged(orientation);
			}
		}
	}

	public boolean isNet() {
		return isNet;
	}

	public void setNet(boolean isNet) {
		this.isNet = isNet;
	}

	public boolean isLeadingMode() {
		return isLeadingMode;
	}

	public void setLeadingMode(boolean isLeadingMode) {
		this.isLeadingMode = isLeadingMode;
	}

	public boolean isGuideMode() {
		return isGuideMode;
	}

	public void setGuideMode(boolean isGuideMode) {
		this.isGuideMode = isGuideMode;
	}
	
	
	
}
