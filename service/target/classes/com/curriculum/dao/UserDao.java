package com.curriculum.dao;

import com.curriculum.domain.PageBean;
import com.curriculum.domain.User;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserDao
{
   String TABLE = "user";

  @Insert(""
          +" insert into "
          +TABLE+" ( username,role_id,password,email,tel,last_login_time,login_time ) "
          +" values ( #{user.username},#{user.roleId},#{user.password},#{user.email},#{user.tel},#{user.lastLoginTime},#{user.loginTime})"
  )
  @Options(useGeneratedKeys=true, keyProperty="user.id")
   int addUser(@Param("user") User user);

  @Select(""
          +" select  id,username,role_id,password,email,tel,last_login_time,login_time "
          +" from "+TABLE
          +" where username = #{username}")
   User findUserByUsername(@Param("username") String username);

  @Select(""
          +" select  id,username,role_id,password,email,tel,last_login_time,login_time "
          +" from "+TABLE
          +" where id = #{id} "
  )
   User findUserById(@Param("id") int id);

  @Update(""
          +" update "+TABLE
          +" set status = #{user.status},password = #{user.password},email = #{user.email},tel = #{user.tel},last_login_time = #{user.lastLoginTime},login_time = #{user.loginTime}"
          +" where username = #{user.username} "
  )
   int changeUserInfo(@Param("user") User user);

  @Select(""
          +" select  count(1) "
          +" from " +TABLE
  )
   int getUsersCount();

  @Select(""
          +" select  id,username,status,role_id,email,create_time,last_login_time,login_time,tel "
          +" from "+TABLE
          +" where role_id != 0 "
          +" limit #{pageBean.recordIndex},#{pageBean.pageSize} "
  )
   List<User> getUsersByPage(@Param("pageBean") PageBean pageBean);
}
