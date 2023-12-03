package org.user.demo.utility;

import java.util.Base64;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.HashMap;

public class Utility {

	public static Map<String,String> resolveHeaders(String token) throws Exception {
		String au = token;
		au= au.replaceFirst("Basic","");
		Decoder d = Base64.getMimeDecoder();
		byte[]  au1 = d.decode(au);
		String au2 =new String(au1);
		
		Map<String,String> map = new HashMap<String,String>();
		try {
			StringTokenizer st = new StringTokenizer(au2, ":");
			map.put("userId", st.nextToken());
		} catch (Exception ex) {
			throw new Exception("Authorization Not Found");
		}
		return map;
	}
	
	public static String getBase64Encoded(String token) throws Exception{
		Encoder en = Base64.getMimeEncoder();
		byte[] encodedToken = en.encode(token.getBytes());
		return new String(encodedToken);
	}
}
