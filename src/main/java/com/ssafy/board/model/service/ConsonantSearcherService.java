package com.ssafy.board.model.service;

public interface ConsonantSearcherService {
	boolean matchString(String value, String search);
	boolean isInitialSound(char searchar);
	String engToKor(String eng) throws Exception;
	String koToEn(String ko) throws Exception;
}
