package com.example.lcd.gab.Friend_list;

/**
 * Created by LCD on 2016-01-08.
 */
public class InitialSoundSearcher_ForFriendList {

    private static final char KOREAN_BEGIN_UNI = 44032;
    private static final char KOREAN_LAST_UNI = 55203;
    private static final char KOREAN_BASE_UNIT = 588;
    private static final char[] INITIAL_SOUND = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ' };

    public InitialSoundSearcher_ForFriendList(){}

    public static boolean patternMatching(String value, String search){
        int t = 0;
        int seof = value.length() - search.length();
        int slen = search.length();
        if(seof < 0)
            return false;
        for(int i = 0;i <= seof;i++){
            t = 0;
            while(t < slen){
                if(isInitialSound(search.charAt(t))==true && isKorean(value.charAt(i+t))){
                    if(getInitialSound(value.charAt(i+t))==search.charAt(t))
                        t++;
                    else
                        break;
                } else {
                    if(value.charAt(i+t)==search.charAt(t))
                        t++;
                    else
                        break;
                }
            }
            if(t == slen)
                return true;
        }
        return false;
    }

    private static boolean isInitialSound(char input){
        for(char c:INITIAL_SOUND){
            if(c == input){
                return true;
            }
        }
        return false;
    }

    private static char getInitialSound(char c){
        int begin = c-KOREAN_BEGIN_UNI;
        int index = begin / KOREAN_BASE_UNIT;
        return INITIAL_SOUND[index];
    }

    private static boolean isKorean(char c){
        return KOREAN_BEGIN_UNI <= c && c <= KOREAN_LAST_UNI;
    }
}
