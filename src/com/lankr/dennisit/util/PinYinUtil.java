package com.lankr.dennisit.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

public class PinYinUtil {

	public static String getPinYin(String src) {
		if (src == null)
			return "";
		String t4 = "";
		try {
			char[] t1 = null;
			t1 = src.toCharArray();
			// System.out.println(t1.length);
			String[] t2 = new String[t1.length];
			HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
			t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			t3.setVCharType(HanyuPinyinVCharType.WITH_V);
			int t0 = t1.length;
			for (int i = 0; i < t0; i++) {
				if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					t4 += t2[0];
				} else {
					t4 += Character.toString(t1[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t4;
	}
	
//	public static void main(String args[]) {
//		String str1 = "天生丽质难自弃" ;
//		String str2 = PinYinUtil.getPinYin(str1) ;
//		System.out.println(str2);
//	}
}
