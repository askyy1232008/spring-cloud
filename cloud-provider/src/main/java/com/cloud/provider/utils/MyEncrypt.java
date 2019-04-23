package com.cloud.provider.utils;

import java.util.HashMap;
import java.util.Map;

public final class MyEncrypt {
	private static Map<String, String> getEncryptMapData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("0", "ぁ");
		map.put("1", "あ");
		map.put("2", "ァ");
		map.put("3", "Γ");
		map.put("4", "Д");
		map.put("5", "é");
		map.put("6", "Δ");
		map.put("7", "ガ");
		map.put("8", "う");
		map.put("9", "ウ");
		return map;
	}

	private static Map<String, String> getDencryptMapData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("ぁ", "0");
		map.put("あ", "1");
		map.put("ァ", "2");
		map.put("Γ", "3");
		map.put("Д", "4");
		map.put("é", "5");
		map.put("Δ", "6");
		map.put("ガ", "7");
		map.put("う", "8");
		map.put("ウ", "9");
		return map;
	}

	public static String encrypt(String password) {
		String name = "";
		for (int i = 0; i < password.length(); i++) {
			String tmp = String.valueOf(password.charAt(i));
			if(isNumeric(tmp)){
				name = name + getEncryptMapData().get(tmp);
			}else{
				name = name + tmp;
			}
		}
		return name;
	}

	public static String dencrypt(String password) {
		String name = "";
		for (int i = 0; i < password.length(); i++) {
			String tmp = String.valueOf(password.charAt(i));
			if(isNumeric(tmp)){
				name = name + getDencryptMapData().get(tmp);
			}else{
				name = name + tmp;
			}
		}
		return name;
	}

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			int chr = str.charAt(i);
			if (chr < 48 || chr > 57)
				return false;
		}
		return true;
	}
}
