package com.example.photogram.util;

public class Script {

    // 경고창 하나 띄우고 뒤로 돌아간다.
    public static String back(String msg){
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('"); // alert 시작
        sb.append(msg); // msg 삽입
        sb.append("');"); // alert 끝
        sb.append("history.back();");
        sb.append("</script>");
        return sb.toString();
    }
}
