package com.lankr.dennisit.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public final class Tools {

	public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static String EMPTY_STRING = "";
	public final static String SPACE_STRING = " ";

	public static String formatYMDDate(Date date) {
		return df.format(date);
	}

	public static String formatYMDHMSDate(Date date) {
		if (date == null)
			return "";
		return df1.format(date);
	}

	public static String getSHA1String(String input) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA1");
			byte[] data = input.getBytes();
			md.update(data, 0, data.length);
			BigInteger i = new BigInteger(1, md.digest());
			return String.format("%1$032X", i).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String encryptPassword(String originPassword) {
		return getSHA1String(originPassword);
	}

	public static void main(String[] args)
			throws BadHanyuPinyinOutputFormatCombination {
		// System.out.println(getUUID());

		makeQr(new File("I:\\test111.png"), "http://192.168.1.119:8080/bb");
	}

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

	public static String getPinYinHeadChar(String str) {
		String convert = "";
		for (int j = 0; j < str.length(); j++) {
			char word = str.charAt(j);
			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
			if (pinyinArray != null) {
				convert += pinyinArray[0].charAt(0);
			} else {
				convert += word;
			}
		}
		return convert;
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String getCnASCII(String cnStr) {
		StringBuffer strBuf = new StringBuffer();

		byte[] bGBK = cnStr.getBytes();
		for (int i = 0; i < bGBK.length; i++) {
			strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
		}
		return strBuf.toString();
	}

	public static String mkAccessToken() {
		return Base64.getEncoder().encodeToString(
				(getUUID() + getRandomNumber()).getBytes());
	}

	public static int getRandomNumber() {
		return new Random().nextInt(10);
	}

	public static String encryption() {
		return getSHA1String(getUUID() + System.currentTimeMillis());
	}

	public final static String makeGetUrl(String url,
			Map<String, String> parameters) {
		if (parameters != null && parameters.size() > 0) {
			StringBuffer sb = new StringBuffer(url);
			sb.append("?");
			Iterator<String> it = parameters.keySet().iterator();
			String key = EMPTY_STRING;
			while (it.hasNext()) {
				key = it.next();
				sb.append(key).append("=").append(parameters.get(key))
						.append("&");
			}
			// remove last character '&'
			url = sb.substring(0, sb.length() - 1);
		}
		return url;
	}

	public static String nullValueFilter(String value) {
		return value == null ? EMPTY_STRING : value;
	}

	public static void makeQr(File file, String text) {
		try {
			if (text == null)
				return;
			int size = 480;
			String fileType = "png";
			Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			text = new String(text.getBytes("UTF-8"), "ISO-8859-1");
			BitMatrix byteMatrix = qrCodeWriter.encode(text,
					BarcodeFormat.QR_CODE, size, size, hintMap);
			int CrunchifyWidth = byteMatrix.getWidth();
			BufferedImage image = new BufferedImage(CrunchifyWidth,
					CrunchifyWidth, BufferedImage.TYPE_INT_RGB);
			image.createGraphics();
			Graphics2D graphics = (Graphics2D) image.getGraphics();
			graphics.setColor(Color.WHITE);
			graphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);
			graphics.setColor(Color.BLACK);
			for (int i = 0; i < CrunchifyWidth; i++) {
				for (int j = 0; j < CrunchifyWidth; j++) {
					if (byteMatrix.get(i, j)) {
						graphics.fillRect(i, j, 1, 1);
					}
				}
			}
			ImageIO.write(image, fileType, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 校验正浮点数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isFloat(String str) {
		Pattern pattern = Pattern
				.compile("^((\\d+\\.\\d*[1-9]\\d*)|(\\d*[1-9]\\d*\\.\\d+)|(\\d*[1-9]\\d*))$");
		return pattern.matcher(str).matches();
	}

	static Pattern p = Pattern.compile("^1[^1|^2|^6\\D]\\d{9}$");

	public static boolean isValidMobile(String mobile) {
		return p.matcher(mobile).matches();
	}

	/**
	 * 字符串转换成十六进制字符串
	 */

	public static String str2HexStr(String str) {

		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString();
	}

	/**
	 * 
	 * 十六进制转换字符串
	 */

	public static String hexStr2Str(String hexStr) {
		String str = "0123456789ABCDEF";
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int n;
		for (int i = 0; i < bytes.length; i++) {
			n = str.indexOf(hexs[2 * i]) * 16;
			n += str.indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (n & 0xff);
		}
		return new String(bytes);
	}

	public static String[] chars = new String[] { "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X",
			"Y", "Z" };

	/**
	 * @param length
	 *            length必须大于5，因为切割的16进制不能大于0x7fffffff
	 * 
	 * @return
	 */
	public static String generateShortUuid(int length) {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = getUUID().replaceAll("-", "");
		int max = uuid.length();
		// 长度需大于int的长度
		length = Math.min(max, Math.max(5, length));
		int sep = max / length;
		for (int i = 0; i < length; i++) {
			String str = uuid.substring(i * sep, Math.min(i * sep + sep, max));
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % chars.length]);
		}
		return shortBuffer.toString();
	}

	public static boolean isBlank(CharSequence chars) {
		return StringUtils.isBlank(chars);

	}

	public static String encodeBase64UrlSafeString(String data) {
		if (data == null)
			return null;
		return org.apache.commons.codec.binary.Base64
				.encodeBase64URLSafeString(data
						.getBytes(StandardCharsets.UTF_8));
	}

	public static Date getCurrentDate() {
		return Calendar.getInstance(Locale.getDefault()).getTime();
	}

	public static String replaceSubString(String str, int n) {
		String sub = "";
		try {
			sub = str.substring(0, str.length() - n);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < n; i++) {
				sb = sb.append("*");
			}
			sub += sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sub;
	}

	/**
	 * @author Kalean.Xiang
	 * @createDate 2016年5月12日
	 * @modifyDate 2016年5月12日
	 * @return true if collection is null or empty
	 */
	public static boolean isEmpty(Collection<?> c) {
		if (c != null) {
			return c.isEmpty();
		}
		return true;
	}

	public static String subString(String str, int len) {
		if (str == null)
			return null;
		if (len >= str.length()) {
			return str;
		}
		return str.substring(0, len);
	}

	public static String intToBinary(int i) {
		return Integer.toBinaryString(i);
	}
}
