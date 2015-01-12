package com.wood.test;

import java.io.File;

import net.sf.json.JSONObject;

import com.wood.pojo.Token;
import com.wood.util.WeChatApiUtil;

/**
 * 
 * @title       :Test
 * @description :
 * @update      :2014-10-15 上午9:28:56
 * @author      :wang_ll
 * @version     :1.0.0
 * @since       :2014-10-15
 */
public class Test {
	public static void main(String[] args) {
		File file = new File(
				"D:/Program Files/FileZillaServer/FtpHome/0096/00/2c9185c948357dad0148357dd3db0002.mp4");
		Token token = WeChatApiUtil.getToken("wx7a45ffbe1ca6dbf4",
				"f66e397162bb791652aef1dd9fb0aedb");
		System.out.println(token.toString());
		File image = new File("src/Snoopy.jpg");
//		String media1 = WeChatApiUtil.uploadMedia(file, token.getAccessToken(), "video").getString("media_id");
//		System.out.println("mediaId:"+media1);
		JSONObject uploadMedia = WeChatApiUtil.uploadMedia(image, token.getAccessToken(), "image");
		String media2 = uploadMedia.getString("media_id");
		System.out.println("mediaId:"+media2);
		/*if(uploadMedia!=null){
			
//		WeChatApiUtil.downloadMedia("D:/videoMedia.MP4", token.getAccessToken(), media1);
			WeChatApiUtil.downloadMedia("D:/imageMedia.jpg", token.getAccessToken(), media2);
			
		}*/
		
		//二维码生成
	/*	String qCodeRequest = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
		String qCodeUrl = WeChatApiUtil.getQCodeUrl(token.getAccessToken());
		JSONObject o = JSONObject.fromObject(WeChatApiUtil.httpsRequestToString(qCodeUrl, "POST", qCodeRequest));
		String ticketId = o.getString("ticket");
		System.out.println(o.toString());
		
		ticketId = URLEncoder.encode(ticketId);
		String ticketUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticketId;
		System.out.println("url ="+ticketUrl);
		File result = WeChatApiUtil.httpsRequestToFile("D:/test.jpg",ticketUrl, "GET", null);*/
	
	    //获取用户信息列表
//		String fansUrl = WeChatApiUtil.getFansInfoUrl(token.getAccessToken(), "");
		
		
	}

}
