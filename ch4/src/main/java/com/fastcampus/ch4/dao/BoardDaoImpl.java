package com.fastcampus.ch4.dao;

import com.fastcampus.ch4.domain.BoardDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDaoImpl implements BoardDao {
    @Autowired
    SqlSession session;

    String namespace="com.fastcampus.ch4.dao.BoardMapper.";

    @Override
    public BoardDto select(int bno) throws Exception{ //id가 같아야 하고, parameterType이 맞아야 하고, resultType이 BoardDto여야 한다//
       return session.selectOne(namespace+"select", bno);//session을 이용해서 하나만 갖고 오기
    }
}
