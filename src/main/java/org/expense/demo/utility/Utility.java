package org.expense.demo.utility;

import java.util.Base64;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Base64.Decoder;
import java.util.HashMap;

public class Utility {

	public static Map<String,String> resolveHeaders(String token) {
		String au = token;
		au= au.replaceFirst("Basic","");
		Decoder d = Base64.getMimeDecoder();
		byte[]  au1 = d.decode(au);
		String au2 =new String(au1);
		
		Map<String,String> map = new HashMap<String,String>();
		StringTokenizer st = new StringTokenizer(au2, ":");
		map.put("userId", st.nextToken());
		
		return map;
	}
}
