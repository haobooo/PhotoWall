package com.photowall.oauth.util;

import java.lang.ref.WeakReference;

import android.content.Context;

/**
 * 鍏ㄥ眬绫�
 * 
 * @author 06peng
 * 
 */
public class AppContext {

	public static boolean FIRTH_INIT = false;
	private int OAUTH_TYPE;
	
	public static final int oauth_bind = 10011;
	
	public static boolean auto_locate = true;

	public int getOAUTH_TYPE() {
		return OAUTH_TYPE;
	}

	public void setOAUTH_TYPE(int oAUTH_TYPE) {
		OAUTH_TYPE = oAUTH_TYPE;
	}

	private static AppContext instance = new AppContext();

	public static AppContext getInstance() {
		if (instance == null) {
			instance = new AppContext();
		}
		return instance;
	}

	public static final String UNAVAILABLE = "unavailable";
	public static final String AVAILABLE = "available";

	/**
	 * 鑾峰彇 绯荤粺涓婁笅鏂�
	 */
	private WeakReference<Context> context;

	/**
	 * 鑾峰彇 绯荤粺涓婁笅鏂�
	 * 
	 * @return
	 */
	public static Context getContext() {
		if (getInstance().context == null) {
			return null;
		}
		return getInstance().context.get();
	}

	/**
	 * 璁剧疆 绯荤粺涓婁笅鏂�
	 * 
	 * @return
	 */
	public static void setContext(Context contextx) {
		getInstance().context = null;
		getInstance().context = new WeakReference<Context>(contextx);
	}

	public static int language;

	public static void setLanguage(int language) {
		AppContext.language = language;
	}
	
	public static final String SD_PATH = "mobibottle";
	/**鐢ㄦ埛澶村儚鍥剧墖璺緞*/
	public static final String USERHEADICON = SD_PATH + "/userhead/icons";
	/**鐡跺瓙绫诲瀷鐩稿叧鍥剧墖璺緞*/
	public static final String BottleTypeIcon = SD_PATH + "/bottle_type/icons";
	
	/**鎹炵綉绫诲瀷鐩稿叧鍥剧墖璺緞*/
	public static final String NetTypeIcon = SD_PATH + "/nettype/icons";
	
	/**Bottle Timeline鐩稿叧鍥剧墖璺緞*/
	public static final String BottleTimelineIcon = SD_PATH + "/bttimeline/icons";
	
	/**鏌ョ湅鐡跺瓙鍥炲鍥剧墖璺緞*/
	public static final String BottleFeedlistIcon = SD_PATH + "/btfeedlists";
	
	/**Timeline big鍥剧墖璺緞*/
	public static final String BottleBigPic = SD_PATH + "/bttimeline";
	
	/**Exchange Timeline鐩稿叧鍥剧墖璺緞*/
	public static final String ExchangeTimelineIcon = SD_PATH + "/extimeline";
	
	/**BTFriend Timeline鐩稿叧鍥剧墖璺緞*/
	public static final String BtFriendTimelineIcon = SD_PATH + "/btfriendtimeline";
	
	/**鐢ㄦ埛澶村儚缂撳瓨*/
	public static final String UserAvatarIcon = SD_PATH + "/user_avatar";
	
	/**鐩稿唽璺緞*/
	public static final String AlbumIcon = SD_PATH + "/albums";
	/**濂藉弸鍔ㄦ�璺緞*/
	public static final String FriendFeedIcon = SD_PATH + "/friendicon";
	
	
	private int login_uid;
	private String login_token;
	private String deviceId;
	private int currentItem;

	private boolean isFromExchangeTime;
	private boolean isFromBottleTime;
	private boolean isFromBtFriend;
	
	
	public int getLogin_uid() {
		return login_uid;
	}

	public void setLogin_uid(int login_uid) {
		this.login_uid = login_uid;
	}

	public String getLogin_token() {
		return login_token;
	}

	public void setLogin_token(String login_token) {
		this.login_token = login_token;
	}
	
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public int getCurrentItem() {
		return currentItem;
	}

	public void setCurrentItem(int currentItem) {
		this.currentItem = currentItem;
	}

	public static final int CHOOSE_FRIEND_THROWBOTTLE_TYPE = 1;
	public static final int CHOOSE_FRIEND_EXCHANGE_INFO_TYPE = 2;
	public static final int CHOOSE_FRIEND_EXCHANGE_PHOTO_TYPE = 3;
	public static final int CHOOSE_FRIEND_FORWARD_TYPE = 4;
	
	public static final String SEND_BT = "send_bt";  //鎵旂摱瀛�
	public static final String REPLY_BT = "reply_bt"; //鍥炲鐡跺瓙
	public static final String COPYMANUAL_BT = "copymanual_bt"; //鎵嬪姩浼犻�鐡跺瓙
	public static final String TRANSMIT_BT = "transmit_bt"; //杞浇鐡跺瓙
	public static final String REPLY_INFOEX = "reply_infoex"; //鍥炲簲璧勬枡浜ゆ崲 鍖呭惈浜嗭細鍥炲簲璧勬枡浜ゆ崲銆佸彂璧疯祫鏂欎氦鎹袱涓姛鑳姐�
	public static final String APPLY_PICEX = "apply_picex"; //鍙戣捣鐓х墖浜ゆ崲
	public static final String REPLY_PICEX = "reply_picex"; //鍥炲簲鐓х墖浜ゆ崲 鍖呭惈浜嗗洖搴旂収鐗囦氦鎹�鎺ュ彈鐓х墖浜ゆ崲涓や釜鍔熻兘銆�
	public static final String REPLYPIC_PICEX = "replypic_picex"; //鍥炲簲鐓х墖浜ゆ崲涓殑鐓х墖
	public static final String FORWARPIC_PICEX = "forwardpic_picex"; //杞彂鐓х墖浜ゆ崲涓殑鐓х墖
	public static final String REPLY_PHOTO = "reply_photo"; //璇勮鐩稿唽鐓х墖
	public static final String REPLY_COMMENT = "reply_comment"; //鍥炲璇勮
	public static final String APPLY_FRIEND = "apply_friend"; //鐢宠鍔犲ソ鍙�
	public static final String SEND_MESSAGE = "send_message"; //鍙戣〃绉佷俊
	public static final String ADD_ALBUM = "add_album"; //娣诲姞鐩稿唽
	public static final String EDIT_ALBUM = "edit_album"; //缂栬緫鐩稿唽
	public static final String UPLOAD_PHOTO = "upload_photo"; //涓婁紶鐓х墖
	public static final String EDIT_PHOTO = "edit_photo"; //缂栬緫鐩稿唽
	public static final String EDIT_MEMO = "edit_memo"; //缂栬緫鐢ㄦ埛澶囨敞
	public static final String SET_DOING = "set_doing"; //璁剧疆蹇冩儏

	public void setFromBottleTime(boolean isFromBottleTime) {
		this.isFromBottleTime = isFromBottleTime;
	}

	public boolean isFromBottleTime() {
		return isFromBottleTime;
	}

	public void setFromExchangeTime(boolean isFromExchangeTime) {
		this.isFromExchangeTime = isFromExchangeTime;
	}

	public boolean isFromExchangeTime() {
		return isFromExchangeTime;
	}

	public void setFromBtFriend(boolean isFromBtFriend) {
		this.isFromBtFriend = isFromBtFriend;
	}

	public boolean isFromBtFriend() {
		return isFromBtFriend;
	}
	
	public void setFromExchangePicDetail(boolean fromExchangePicDetail) {
		this.fromExchangePicDetail = fromExchangePicDetail;
	}

	public boolean isFromExchangePicDetail() {
		return fromExchangePicDetail;
	}

	private boolean fromExchangePicDetail;

	private boolean fromExchangeTimeToChangeFriend;
	public boolean isFromExchangeTimeToChangeFriend() {
		return fromExchangeTimeToChangeFriend;
	}

	public void setFromExchangeTimeToChangeFriend(boolean b) {
		this.fromExchangeTimeToChangeFriend = b;
	}

}
