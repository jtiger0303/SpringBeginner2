//package com.fastcampus.ch4.domain;
//
//import com.fastcampus.ch4.domain.PageHandler;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class PageHandlerTest {
//    @Test
//    public void test(){
//        PageHandler ph= new PageHandler(250, 1);
//        assertTrue(ph.getBeginPage() ==1);
//        assertTrue(ph.getEndPage() ==10);
//    }
//
//    @Test
//    public void test2(){
//        PageHandler ph= new PageHandler(250, 11);
//        assertTrue(ph.getBeginPage() ==11);
//        assertTrue(ph.getEndPage() ==20);
//    }
//    @Test
//    public void test3(){
//        PageHandler ph= new PageHandler(255, 10);
//        assertTrue(ph.getBeginPage() ==1);
//        assertTrue(ph.getEndPage() ==10);
//    }
//}