package com.study.erum.security;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@Runwith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main.webapp.WEB-INF/spring/root-context.xml",
                        "file:src/main.webapp.WEB-INF/spring/security-context.xml"})
@Log4j
public class MemberTests {

  private PasswordEncoder pwencoder;
  
  @Autowired
  private DataSource ds;
  
  @Test
  public void testInsertMember() {
    
    String sql= "insert into tbl_member(userid, userpw, username) values(?,?,?)";
    
    for(int i =0; i<100; i++) {
      Connection con = null;
      PreparedStatement pstmt = null;
      
      try {
        
        con =ds.getConnection();
        pstmt = con.prepareStatement(sql);
        
        
        pstmt.setString(2, pwencoder.encode("pw" + i));
        
        if (i<80) {
          pstmt.setString(1, "user"+i);
          pstmt.setString(1, "일반사용자"+i);
        }
        if (i<90) {
          pstmt.setString(1, "manager"+i);
          pstmt.setString(1, "운영자"+i);
        }
        
        pstmt.executeUpdate();
      } catch (Exception e) {
        
      } finally {
        if(pstmt != null) {try {pstmt.close();} catch (Exception e) {}}
        if(con != null) {try {con.close();} catch (Exception e) {}}
      }
    }
  }
  
}
