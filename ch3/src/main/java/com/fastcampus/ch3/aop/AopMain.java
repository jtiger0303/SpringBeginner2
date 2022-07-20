package com.fastcampus.ch3.aop;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AopMain {
    public static void main(String[] args) throws Exception{
        MyAdvice myAdvice=new MyAdvice();

        Class myClass= Class.forName("com.fastcampus.ch3.aop.MyClass");//myClass의 객체를 가져온다//
        Object obj=myClass.newInstance();

        for(Method m: myClass.getDeclaredMethods()){
            myAdvice.invoke(m, obj, null);
        }

    }
}
class MyAdvice{
    Pattern p= Pattern.compile("a.*");//여러 메소드 중에서 특정 메소드만 실행(a로 시작하는 문자열만)//

    boolean matches(Method m){
        Matcher matcher=p.matcher(m.getName());
        return matcher.matches();
    }

    void invoke(Method m, Object obj, Object... args)throws Exception{
        if(matches(m))//패턴 일치 하는 경우에만//
            System.out.println("[before]{");
        m.invoke(obj, args);///aaa(), aaa2(), aaa3() 호출
        if(matches(m))
            System.out.println("}[after]");
    }
}
class MyClass{
    void aaa(){
        System.out.println("aaa() is called");
    }
    void aaa2(){
        System.out.println("aaa2() is called");
    }
    void bbb(){
        System.out.println("bbb() is called");
    }

}
