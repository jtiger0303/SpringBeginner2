package com.fastcampus.ch3;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DBConnectionTest2Test {
    @Autowired
    DataSource ds;

    @Test
    public void insertUserTest() throws Exception {
        User user= new User("asdfg", "12345", "abc", "aaa@aaa.com", new Date(), "instagram", new Date());
        deleteAll();//어떨 때는 test 돌아가고 안 돌아가는 상황을 방지~~, 반복적으로 테스트 가능//
        int rowCnt=insertUser(user);

        assertTrue(rowCnt==1);
    }

    @Test
    public void selectUserTest() throws Exception{ //Test는 계속 진행할 때마다 실패 없이 해야한다~~//
        deleteAll();
        User user= new User("asdfg", "12345", "abc", "aaa@aaa.com", new Date(), "instagram", new Date());

        int rowCnt=insertUser(user);
        User user2=selectUser("asdfg");

        assertTrue(user.getId().equals("asdfg"));
    }
    @Test
    public void deleteUserTest() throws Exception{
        deleteAll();
        int rowCnt=deleteUser("asdf");

        assertTrue(rowCnt==0);

        User user= new User("asdfg", "12345", "abc", "aaa@aaa.com", new Date(), "instagram", new Date());
        rowCnt=insertUser(user);
        assertTrue(rowCnt==1);

        rowCnt=deleteUser(user.getId());
        assertTrue(rowCnt==1); //1이면 삭제 된 것

        assertTrue(selectUser(user.getId())==null);


    }

    //매개변수로 받은 사용자 정보로 user_info 테이블을 update하는 메서드
    public int updateUser(User user) throws Exception{
        return 0;
    }
    public int deleteUser(String id) throws Exception{
        Connection conn=ds.getConnection();
        //실행할 sql문 작성

        String sql="delete from user_info where id= ? ";
        PreparedStatement pstmt=conn.prepareStatement(sql);
        pstmt.setString(1, id);
        //int rowCnt=pstmt.executeUpdate();//sql문이 얼마나 영향, 성공하면 1아니면 0
        //return rowCnt;
        return pstmt.executeUpdate();
    }
    public User selectUser(String id) throws Exception{
        Connection conn=ds.getConnection();
        //실행할 sql문 작성

        String sql="select * from user_info where id= ? ";

        //sql문 실행(PreparedStatement는 ?로 표시하고 setString으로 값을 채움//
        PreparedStatement pstmt=conn.prepareStatement(sql); //장점: SQL Injection 공격, 성능향상
        pstmt.setString(1, id);
        ResultSet rs=pstmt.executeQuery();//sql문이 얼마나 영향, 성공하면 1아니면 o, executeQuery()는 select문일 때만, ResultSet(테이블 형태로 갖고 옴)

        if(rs.next()){ //결과가 있으면 받아옴//
            User user=new User();
            user.setId(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setName(rs.getString(3));
            user.setEmail(rs.getString(4));
            user.setBirth(new Date(rs.getDate(5).getTime()));
            user.setSns(rs.getString(6));
            user.setReg_date(new Date(rs.getTimestamp(7).getTime()));

            return user;
        }
        return null;
    }
    private void deleteAll() throws Exception {
        Connection conn=ds.getConnection();
        //실행할 sql문 작성

        String sql="delete from user_info ";

        //sql문 실행(PreparedStatement는 ?로 표시하고 setString으로 값을 채움//
        PreparedStatement pstmt=conn.prepareStatement(sql); //장점: SQL Injection 공격, 성능향상
        pstmt.executeUpdate();//sql문이 얼마나 영향, 성공하면 1아니면 o
    }
    //사용자 정보를 user-info테이블에 저장하는 메서드
    public int insertUser(User user)throws Exception{
        Connection conn=ds.getConnection();

    //    insert into user_info (id, pwd, name, email, birth, sns, reg_date)
    //    values ('asdf22', '1234', 'smith', 'aaa@aaa.com', '2022-01-01', 'facebook', now())
        //실행할 sql문 작성성
        String sql="insert into user_info values (?, ?, ?, ?, ?, ?, now()) ";

        //sql문 실행(PreparedStatement는 ?로 표시하고 setString으로 값을 채움//
        PreparedStatement pstmt=conn.prepareStatement(sql); //장점: SQL Injection 공격, 성능향상
        pstmt.setString(1, user.getId());
        pstmt.setString(2, user.getPwd());
        pstmt.setString(3, user.getName());
        pstmt.setString(4, user.getEmail());
        pstmt.setDate(5, new java.sql.Date(user.getBirth().getTime()));//setDate이 util_Date가 아닌 sql_date이므로 변환해주어야 한다//
        pstmt.setString(6, user.getSns());

        int rowCnt=pstmt.executeUpdate();//sql문이 얼마나 영향, 성공하면 1아니면 o

        return rowCnt;
    }
    @Test
    public void main() throws Exception{

    //    ApplicationContext ac = new GenericXmlApplicationContext("file:src/main/webapp/WEB-INF/spring/**/root-context.xml");
    //    DataSource ds = ac.getBean(DataSource.class);

        Connection conn = ds.getConnection(); // 데이터베이스의 연결을 얻는다.

        System.out.println("conn = " + conn);
        assertTrue(conn!=null);// 괄호 안의 조건식이 true면 성공 아니면 실패

    }
}