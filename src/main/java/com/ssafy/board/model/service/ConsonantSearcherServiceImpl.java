package com.ssafy.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ConsonantSearcherServiceImpl implements ConsonantSearcherService {
	private static ConsonantSearcherService consonantSearcherService = new ConsonantSearcherServiceImpl();
	
	static enum CodeType {
		chosung, jungsung, jongsung
	}
	/** 모음 */
	private final int VOWEL = 21;
	/** 표음문자 */
	private final int PHONOGRAM = 28;

	/** 초성 키 맵 */
	private final Map<Integer, String> initialConsonantList = new HashMap<Integer, String>();
	/** 중성 키 맵 */
	private final Map<Integer, String> syllableNucleusList = new HashMap<Integer, String>();
	/** 종성 키 맵 */
	private final Map<Integer, String> finalConsonantList = new HashMap<Integer, String>();
	/** 단일 글자 키 맵 */
	private final Map<Integer, String> compatibilityList = new HashMap<Integer, String>();
	
	private static final char HANGUL_BEGIN_UNICODE = 44032; // 가 
	private static final char HANGUL_LAST_UNICODE = 55203; // 힣
	private static final char HANGUL_BASE_UNIT = 588;//각자음 마다 가지는 글자수
	//자음
	private static final char[] INITIAL_SOUND = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };
	
	public static ConsonantSearcherService getSSearcher() {
		return consonantSearcherService;
	}

	public boolean isInitialSound(char searchar){ 
		for(char c:INITIAL_SOUND){ 
			if(c == searchar){ 
				return true; 
			} 
		} 
		return false; 
	}

	private char getInitialSound(char c) { 
		int hanBegin = (c - HANGUL_BEGIN_UNICODE); 
		int index = hanBegin / HANGUL_BASE_UNIT; 
		return INITIAL_SOUND[index]; 
	}

	private boolean isHangul(char c) { 
		return HANGUL_BEGIN_UNICODE <= c && c <= HANGUL_LAST_UNICODE; 
	} 
	
	/**
	 * 생성자.
	 */
	public ConsonantSearcherServiceImpl() { } 
	
	/** * 검색을 한다. 초성 검색 완벽 지원함. 
	 * @param value : 검색 대상 ex> 초성검색합니다 
	 * @param search : 검색어 ex> ㅅ검ㅅ합ㄴ 
	 * @return 매칭 되는거 찾으면 true 못찾으면 false. */ 
	public boolean matchString(String value, String search){ 
		int t = 0; 
		int seof = value.length() - search.length(); 
		int slen = search.length(); 
		if(seof < 0) 
			return false; //검색어가 더 길면 false를 리턴한다. 
		for(int i = 0;i <= seof;i++){ 
			t = 0; 
			while(t < slen){ 
				if(isInitialSound(search.charAt(t))==true && isHangul(value.charAt(i+t))){ 
					//만약 현재 char이 초성이고 value가 한글이면
					if(getInitialSound(value.charAt(i+t))==search.charAt(t)) 
						//각각의 초성끼리 같은지 비교한다
						t++; 
					else 
						break; 
				} else { 
					//char이 초성이 아니라면
					if(value.charAt(i+t)==search.charAt(t)) 
						//그냥 같은지 비교한다. 
						t++; 
					else 
						break; 
				} 
			} 
			if(t == slen) 
				return true; //모두 일치한 결과를 찾으면 true를 리턴한다. 
			} 
		return false; //일치하는 것을 찾지 못했으면 false를 리턴한다.
	}
	
	// ------------------
	// 영한변환
	@Override
	public String engToKor(String eng) throws Exception {

		String ignoreChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

		StringBuffer sb = new StringBuffer();
		int initialCode = 0, medialCode = 0, finalCode = 0;
		int tempMedialCode, tempFinalCode;

		for (int i = 0; i < eng.length(); i++) {
			// 숫자특수문자 처리
			if (ignoreChars.indexOf(eng.substring(i, i + 1))== -1) {
				sb.append(eng.substring(i, i + 1));
				continue;
			}
			// 초성코드 추출
			initialCode = getCode(CodeType.chosung, eng.substring(i, i + 1));
			i++; // 다음문자로

			// 중성코드 추출
			tempMedialCode = getDoubleMedial(i, eng); // 두 자로 이루어진 중성코드 추출

			if (tempMedialCode != -1) {
				medialCode = tempMedialCode;
				i += 2;
			} else { // 없다면,
				medialCode = getSingleMedial(i, eng); // 한 자로 이루어진 중성코드 추출
				i++;
			}

			// 종성코드 추출
			tempFinalCode = getDoubleFinal(i, eng); // 두 자로 이루어진 종성코드 추출
			if (tempFinalCode != -1) {
				finalCode = tempFinalCode;
				// 그 다음의 중성 문자에 대한 코드를 추출한다.
				tempMedialCode = getSingleMedial(i + 2, eng);
				if (tempMedialCode != -1) { // 코드 값이 있을 경우
					finalCode = getSingleFinal(i, eng); // 종성 코드 값을 저장한다.
				} else {
					i++;
				}
			} else { // 코드 값이 없을 경우 ,
				tempMedialCode = getSingleMedial(i + 1, eng);
				// 그 다음의 중성 문자에 대한 코드 추출.
				if (tempMedialCode != -1) { // 그 다음에 중성 문자가 존재할 경우,
					finalCode = 0; // 종성 문자는 없음.
					i--;
				} else {
					finalCode = getSingleFinal(i, eng); // 종성 문자 추출
					if (finalCode == -1) {
						finalCode = 0;
						i--; // 초성,중성 + 숫자,특수문자,
								// 기호가 나오는 경우 index를 줄임.
					}
				}
			}
			// 추출한 초성 문자 코드,
			// 중성 문자 코드, 종성 문자 코드를 합한 후 변환하여 스트링버퍼에 넘김
			sb.append((char) (0xAC00 + initialCode + medialCode + finalCode));
		}
		return sb.toString();
	}

	static private int getCode(CodeType type, String c) {
		// 초성
		String init = "rRseEfaqQtTdwWczxvg";
		// 중성
		String[] mid = { "k", "o", "i", "O", "j", "p", "u", "P", "h", "hk", "ho", "hl", "y", "n", "nj", "np", "nl", "b",
				"m", "ml", "l" };
		// 종성
		String[] fin = { "r", "R", "rt", "s", "sw", "sg", "e", "f", "fr", "fa", "fq", "ft", "fx", "fv", "fg", "a", "q",
				"qt", "t", "T", "d", "w", "c", "z", "x", "v", "g" };

		switch (type) {
		case chosung:
			int index = init.indexOf(c);
			if (index != -1) {
				return index * 21 * 28;
			}
			break;
		case jungsung:

			for (int i = 0; i < mid.length; i++) {
				if (mid[i].equals(c)) {
					return i * 28;
				}
			}
			break;
		case jongsung:
			for (int i = 0; i < fin.length; i++) {
				if (fin[i].equals(c)) {
					return i + 1;
				}
			}
			break;
		default:
			System.out.println("잘못된 타입 입니다");
		}

		return -1;
	}

	static private int getSingleMedial(int i, String eng) {
		if ((i + 1) <= eng.length()) {
			return getCode(CodeType.jungsung, eng.substring(i, i + 1));
		} else {
			return -1;
		}
	}

	// 두 자로 된 중성을 체크하고, 있다면 값을 리턴한다.
	// 없으면 리턴값은 -1
	static private int getDoubleMedial(int i, String eng) {
		int result;
		if ((i + 2) > eng.length()) {
			return -1;
		} else {
			result = getCode(CodeType.jungsung, eng.substring(i, i + 2));
			if (result != -1) {
				return result;
			} else {
				return -1;
			}
		}
	}

	// 한 자로된 종성값을 리턴한다
	// 인덱스를 벗어낫다면 -1을 리턴
	static private int getSingleFinal(int i, String eng) {
		if ((i + 1) <= eng.length()) {
			return getCode(CodeType.jongsung, eng.substring(i, i + 1));
		} else {
			return -1;
		}
	}

	// 두 자로된 종성을 체크하고, 있다면 값을 리턴한다.
	// 없으면 리턴값은 -1
	static private int getDoubleFinal(int i, String eng) {
		if ((i + 2) > eng.length()) {
			return -1;
		} else {
			return getCode(CodeType.jongsung, eng.substring(i, i + 2));
		}
	}

	

	@Override
	public String koToEn(String ko) {
		initList();
		final List<_CharZone> lst = this.divideKoreanTypo(ko);

		final StringBuilder sb = new StringBuilder();

		for (_CharZone item : lst) {
			sb.append(item);
		}

		return sb.toString();
	}

	public void initList() {
		// 초성
		initialConsonantList.put(0x1100, "r");
		initialConsonantList.put(0x1101, "R");
		initialConsonantList.put(0x1102, "s");
		initialConsonantList.put(0x1103, "e");
		initialConsonantList.put(0x1104, "E");
		initialConsonantList.put(0x1105, "f");
		initialConsonantList.put(0x1106, "a");
		initialConsonantList.put(0x1107, "q");
		initialConsonantList.put(0x1108, "Q");
		initialConsonantList.put(0x1109, "t");
		initialConsonantList.put(0x110a, "T");
		initialConsonantList.put(0x110b, "d");
		initialConsonantList.put(0x110c, "w");
		initialConsonantList.put(0x110d, "W");
		initialConsonantList.put(0x110e, "c");
		initialConsonantList.put(0x110f, "z");
		initialConsonantList.put(0x1110, "x");
		initialConsonantList.put(0x1111, "v");
		initialConsonantList.put(0x1112, "g");
		initialConsonantList.put(0x1113, "sr");
		initialConsonantList.put(0x1114, "ss");
		initialConsonantList.put(0x1115, "se");
		initialConsonantList.put(0x1116, "sq");
		initialConsonantList.put(0x1117, "er");
		initialConsonantList.put(0x1118, "fs");
		initialConsonantList.put(0x1119, "ff");
		initialConsonantList.put(0x111a, "fg");
		initialConsonantList.put(0x111c, "aq");
		initialConsonantList.put(0x111e, "qr");
		initialConsonantList.put(0x111f, "qs");
		initialConsonantList.put(0x1120, "qe");
		initialConsonantList.put(0x1121, "qt");
		initialConsonantList.put(0x1122, "qtr");
		initialConsonantList.put(0x1123, "qte");
		initialConsonantList.put(0x1124, "qtq");
		initialConsonantList.put(0x1125, "qtt");
		initialConsonantList.put(0x1126, "qtw");
		initialConsonantList.put(0x1127, "qw");
		initialConsonantList.put(0x1128, "qc");
		initialConsonantList.put(0x1129, "qx");
		initialConsonantList.put(0x112a, "qv");
		initialConsonantList.put(0x112d, "tr");
		initialConsonantList.put(0x112e, "ts");
		initialConsonantList.put(0x112f, "te");
		initialConsonantList.put(0x1130, "tf");
		initialConsonantList.put(0x1131, "ta");
		initialConsonantList.put(0x1132, "tq");
		initialConsonantList.put(0x1133, "tqr");
		initialConsonantList.put(0x1134, "ttt");
		initialConsonantList.put(0x1135, "td");
		initialConsonantList.put(0x1136, "tw");
		initialConsonantList.put(0x1137, "tc");
		initialConsonantList.put(0x1138, "tz");
		initialConsonantList.put(0x1139, "tx");
		initialConsonantList.put(0x113a, "tv");
		initialConsonantList.put(0x113b, "tg");
		initialConsonantList.put(0x113c, "t");
		initialConsonantList.put(0x113d, "T");
		initialConsonantList.put(0x113e, "t");
		initialConsonantList.put(0x113f, "T");
		initialConsonantList.put(0x1141, "dr");
		initialConsonantList.put(0x1142, "de");
		initialConsonantList.put(0x1143, "da");
		initialConsonantList.put(0x1144, "dq");
		initialConsonantList.put(0x1145, "dt");
		initialConsonantList.put(0x1147, "dd");
		initialConsonantList.put(0x1148, "dw");
		initialConsonantList.put(0x1149, "dc");
		initialConsonantList.put(0x114a, "dx");
		initialConsonantList.put(0x114b, "dv");
		initialConsonantList.put(0x114c, "d");
		initialConsonantList.put(0x114d, "wd");
		initialConsonantList.put(0x114e, "w");
		initialConsonantList.put(0x114f, "W");
		initialConsonantList.put(0x1150, "w");
		initialConsonantList.put(0x1151, "W");
		initialConsonantList.put(0x1152, "cz");
		initialConsonantList.put(0x1153, "cg");
		initialConsonantList.put(0x1154, "c");
		initialConsonantList.put(0x1155, "vq");
		initialConsonantList.put(0x1156, "vg");
		initialConsonantList.put(0x1158, "gg");
		initialConsonantList.put(0x115a, "re");
		initialConsonantList.put(0x115b, "st");
		initialConsonantList.put(0x115c, "sw");
		initialConsonantList.put(0x115d, "sg");
		initialConsonantList.put(0x115e, "ef");

		// 중성
		syllableNucleusList.put(0x1161, "k");
		syllableNucleusList.put(0x1162, "o");
		syllableNucleusList.put(0x1163, "i");
		syllableNucleusList.put(0x1164, "O");
		syllableNucleusList.put(0x1165, "j");
		syllableNucleusList.put(0x1166, "p");
		syllableNucleusList.put(0x1167, "u");
		syllableNucleusList.put(0x1168, "P");
		syllableNucleusList.put(0x1169, "h");
		syllableNucleusList.put(0x116a, "hk");
		syllableNucleusList.put(0x116b, "ho");
		syllableNucleusList.put(0x116c, "hl");
		syllableNucleusList.put(0x116d, "y");
		syllableNucleusList.put(0x116e, "n");
		syllableNucleusList.put(0x116f, "nj");
		syllableNucleusList.put(0x1170, "np");
		syllableNucleusList.put(0x1171, "nl");
		syllableNucleusList.put(0x1172, "b");
		syllableNucleusList.put(0x1173, "m");
		syllableNucleusList.put(0x1174, "ml");
		syllableNucleusList.put(0x1175, "l");
		syllableNucleusList.put(0x1176, "hk");
		syllableNucleusList.put(0x1177, "nk");
		syllableNucleusList.put(0x1178, "hi");
		syllableNucleusList.put(0x1179, "yi");
		syllableNucleusList.put(0x117a, "hj");
		syllableNucleusList.put(0x117b, "nj");
		syllableNucleusList.put(0x117c, "mj");
		syllableNucleusList.put(0x117d, "hu");
		syllableNucleusList.put(0x117e, "nu");
		syllableNucleusList.put(0x117f, "hj");
		syllableNucleusList.put(0x1180, "nP");
		syllableNucleusList.put(0x1181, "hP");
		syllableNucleusList.put(0x1182, "hh");
		syllableNucleusList.put(0x1183, "hn");
		syllableNucleusList.put(0x1184, "yi");
		syllableNucleusList.put(0x1185, "yO");
		syllableNucleusList.put(0x1186, "yu");
		syllableNucleusList.put(0x1187, "yh");
		syllableNucleusList.put(0x1188, "yl");
		syllableNucleusList.put(0x1189, "nk");
		syllableNucleusList.put(0x118a, "no");
		syllableNucleusList.put(0x118b, "njm");
		syllableNucleusList.put(0x118c, "nP");
		syllableNucleusList.put(0x118d, "nn");
		syllableNucleusList.put(0x118e, "bk");
		syllableNucleusList.put(0x118f, "bj");
		syllableNucleusList.put(0x1190, "bp");
		syllableNucleusList.put(0x1191, "bu");
		syllableNucleusList.put(0x1192, "bP");
		syllableNucleusList.put(0x1193, "bn");
		syllableNucleusList.put(0x1194, "bl");
		syllableNucleusList.put(0x1195, "mn");
		syllableNucleusList.put(0x1196, "mm");
		syllableNucleusList.put(0x1197, "mln");
		syllableNucleusList.put(0x1198, "lk");
		syllableNucleusList.put(0x1199, "li");
		syllableNucleusList.put(0x119a, "hl");
		syllableNucleusList.put(0x119b, "nl");
		syllableNucleusList.put(0x119c, "ml");
		syllableNucleusList.put(0x11a3, "mk");
		syllableNucleusList.put(0x11a4, "ni");
		syllableNucleusList.put(0x11a5, "ui");
		syllableNucleusList.put(0x11a6, "hi");
		syllableNucleusList.put(0x11a7, "hO");

		// 종성
		finalConsonantList.put(0x11a8, "r");
		finalConsonantList.put(0x11a9, "R");
		finalConsonantList.put(0x11aa, "rt");
		finalConsonantList.put(0x11ab, "s");
		finalConsonantList.put(0x11ac, "sw");
		finalConsonantList.put(0x11ad, "sg");
		finalConsonantList.put(0x11ae, "e");
		finalConsonantList.put(0x11af, "f");
		finalConsonantList.put(0x11b0, "fr");
		finalConsonantList.put(0x11b1, "fa");
		finalConsonantList.put(0x11b2, "fq");
		finalConsonantList.put(0x11b3, "ft");
		finalConsonantList.put(0x11b4, "fx");
		finalConsonantList.put(0x11b5, "fv");
		finalConsonantList.put(0x11b6, "fg");
		finalConsonantList.put(0x11b7, "a");
		finalConsonantList.put(0x11b8, "q");
		finalConsonantList.put(0x11b9, "qt");
		finalConsonantList.put(0x11ba, "t");
		finalConsonantList.put(0x11bb, "T");
		finalConsonantList.put(0x11bc, "d");
		finalConsonantList.put(0x11bd, "w");
		finalConsonantList.put(0x11be, "c");
		finalConsonantList.put(0x11bf, "z");
		finalConsonantList.put(0x11c0, "x");
		finalConsonantList.put(0x11c1, "v");
		finalConsonantList.put(0x11c2, "g");
		finalConsonantList.put(0x11c3, "rf");
		finalConsonantList.put(0x11c4, "rtr");
		finalConsonantList.put(0x11c5, "sr");
		finalConsonantList.put(0x11c6, "se");
		finalConsonantList.put(0x11c7, "st");
		finalConsonantList.put(0x11c9, "sx");
		finalConsonantList.put(0x11ca, "er");
		finalConsonantList.put(0x11cb, "ef");
		finalConsonantList.put(0x11cc, "frt");
		finalConsonantList.put(0x11cd, "fs");
		finalConsonantList.put(0x11ce, "fe");
		finalConsonantList.put(0x11cf, "feg");
		finalConsonantList.put(0x11d0, "ff");
		finalConsonantList.put(0x11d1, "far");
		finalConsonantList.put(0x11d2, "fat");
		finalConsonantList.put(0x11d3, "fqt");
		finalConsonantList.put(0x11d4, "fqg");
		finalConsonantList.put(0x11d6, "fT");
		finalConsonantList.put(0x11d8, "fz");
		finalConsonantList.put(0x11da, "ar");
		finalConsonantList.put(0x11db, "af");
		finalConsonantList.put(0x11dc, "aq");
		finalConsonantList.put(0x11dd, "at");
		finalConsonantList.put(0x11de, "aT");
		finalConsonantList.put(0x11e0, "ac");
		finalConsonantList.put(0x11e1, "ag");
		finalConsonantList.put(0x11e3, "qf");
		finalConsonantList.put(0x11e4, "qv");
		finalConsonantList.put(0x11e5, "qg");
		finalConsonantList.put(0x11e7, "tr");
		finalConsonantList.put(0x11e8, "te");
		finalConsonantList.put(0x11e9, "tf");
		finalConsonantList.put(0x11ea, "tq");
		finalConsonantList.put(0x11f3, "vq");
		finalConsonantList.put(0x11f5, "gs");
		finalConsonantList.put(0x11f6, "gf");
		finalConsonantList.put(0x11f7, "ga");
		finalConsonantList.put(0x11f8, "gq");
		finalConsonantList.put(0x11fa, "rs");
		finalConsonantList.put(0x11fb, "rq");
		finalConsonantList.put(0x11fc, "rc");
		finalConsonantList.put(0x11fd, "rz");
		finalConsonantList.put(0x11fe, "rg");
		finalConsonantList.put(0x11ff, "ss");

		// 자음만
		compatibilityList.put(0x3131, "r");
		compatibilityList.put(0x3132, "R");
		compatibilityList.put(0x3133, "rt");
		compatibilityList.put(0x3134, "s");
		compatibilityList.put(0x3135, "sw");
		compatibilityList.put(0x3136, "sg");
		compatibilityList.put(0x3137, "e");
		compatibilityList.put(0x3138, "E");
		compatibilityList.put(0x3139, "f");
		compatibilityList.put(0x313a, "fr");
		compatibilityList.put(0x313b, "fa");
		compatibilityList.put(0x313c, "fq");
		compatibilityList.put(0x313d, "ft");
		compatibilityList.put(0x313e, "fx");
		compatibilityList.put(0x313f, "fv");
		compatibilityList.put(0x3140, "fg");
		compatibilityList.put(0x3141, "a");
		compatibilityList.put(0x3142, "q");
		compatibilityList.put(0x3143, "Q");
		compatibilityList.put(0x3144, "qt");
		compatibilityList.put(0x3145, "t");
		compatibilityList.put(0x3146, "T");
		compatibilityList.put(0x3147, "d");
		compatibilityList.put(0x3148, "w");
		compatibilityList.put(0x3149, "W");
		compatibilityList.put(0x314a, "c");
		compatibilityList.put(0x314b, "z");
		compatibilityList.put(0x314c, "x");
		compatibilityList.put(0x314d, "v");
		compatibilityList.put(0x314e, "g");
		compatibilityList.put(0x314f, "k");
		compatibilityList.put(0x3150, "o");
		compatibilityList.put(0x3151, "i");
		compatibilityList.put(0x3152, "O");
		compatibilityList.put(0x3153, "j");
		compatibilityList.put(0x3154, "p");
		compatibilityList.put(0x3155, "u");
		compatibilityList.put(0x3156, "P");
		compatibilityList.put(0x3157, "h");
		compatibilityList.put(0x3158, "hk");
		compatibilityList.put(0x3159, "ho");
		compatibilityList.put(0x315a, "hl");
		compatibilityList.put(0x315b, "y");
		compatibilityList.put(0x315c, "n");
		compatibilityList.put(0x315d, "nj");
		compatibilityList.put(0x315e, "np");
		compatibilityList.put(0x315f, "nl");
		compatibilityList.put(0x3160, "b");
		compatibilityList.put(0x3161, "m");
		compatibilityList.put(0x3162, "ml");
		compatibilityList.put(0x3163, "l");
		compatibilityList.put(0x3165, "ss");
		compatibilityList.put(0x3166, "se");
		compatibilityList.put(0x3167, "st");
		compatibilityList.put(0x3169, "frt");
		compatibilityList.put(0x316a, "fe");
		compatibilityList.put(0x316b, "fqt");
		compatibilityList.put(0x316e, "aq");
		compatibilityList.put(0x316f, "at");

		compatibilityList.put(0x3185, "gg");
		compatibilityList.put(0x3187, "yi");
		compatibilityList.put(0x3188, "yO");
		compatibilityList.put(0x3189, "yl");
		compatibilityList.put(0x318a, "bu");
		compatibilityList.put(0x318b, "bP");
		compatibilityList.put(0x318c, "bl");

	}

	private class _CharZone {
		/** 초성 */
		private int initialConsonant;
		/** 중성 */
		private int syllableNucleus;
		/** 종성 */
		private int finalConsonant;
		/** Single 초성 */
		private int compatibility;
		/** 한글 아님 */
		private int notKorean;

		/**
		 * @Override 해당 키 값으로 변환 하도록 한다.
		 */
		@Override
		public String toString() {

			if (notKorean > 0) {
				return String.valueOf((char) notKorean);
			}

			String result;

			try {
				if (compatibility > 0) {
					return compatibilityList.get(compatibility);
				}

				result = initialConsonantList.get(initialConsonant);
				result += syllableNucleusList.get(syllableNucleus);

				if (finalConsonant > 0) {
					result += finalConsonantList.get(finalConsonant);
				}
			} catch (Exception e) {
				System.err.println("Convert Error : " + e.getMessage());
				result = "";
			}

			return result;
		}

	}

	private List<_CharZone> divideKoreanTypo(String typo) {
		// 리스트로 받아오기 위한 변수
		final List<_CharZone> result = new ArrayList<_CharZone>();

		// typo 스트링의 글자수 만큼 list에 담아둡니다.
		for (int i = 0; i < typo.length(); i++) {
			final _CharZone cz = new _CharZone();
			result.add(cz);

			final int comVal = (int) typo.charAt(i);

			// 한글을 검색한다.
			if ((comVal >= 0xAC00) && (comVal <= 0xD79F)) {
				final int uniVal = (int) (comVal - 0xAC00);
				final int uniIndex = (int) (uniVal % PHONOGRAM);

				// 유니코드표에 맞추어 초성 중성 종성을 분리한다.
				final int cho = (int) ((((uniVal - uniIndex) / PHONOGRAM) / VOWEL) + 0x1100);
				final int jung = (int) ((((uniVal - uniIndex) / PHONOGRAM) % VOWEL) + 0x1161);
				final int jong = (int) (uniIndex + 0x11A7);

				cz.initialConsonant = cho;
				cz.syllableNucleus = jung;
				cz.finalConsonant = (uniIndex != 0) ? jong : 0;

			} else if ((comVal >= 0x3130) && (comVal < 0x318F)) {
				// 초성 또는 중성만 있을 경우
				cz.compatibility = comVal;
			} else {
				// 한글이 아닌 것들...
				cz.notKorean = comVal;
			}
		}

		return result;
	}
}