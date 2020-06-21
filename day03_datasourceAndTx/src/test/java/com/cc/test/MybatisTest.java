package com.cc.test;

import com.cc.dao.IUserDao;
import com.cc.domain.QueryVo;
import com.cc.domain.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * 测试 Mybatis 的 CRUD 操作
 */
public class MybatisTest {

    private InputStream is;
    private SqlSession sqlSession;
    private IUserDao userDao;

    @Before//用于在测试方法之前执行
    public void init() throws IOException {
        //1、读取配置文件，生成字节输入流
        is = Resources.getResourceAsStream("SqlMapConfig.xml");

        //2、获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);

        //3、获取SqlSession对象
        sqlSession = factory.openSession(true);

        //4、获取dao的代理对象
        userDao = sqlSession.getMapper(IUserDao.class);
    }

    @After//用于在测试方法之后执行
    public void destory() throws IOException {
        //提交事务
        //sqlSession.commit();
        //6、释放资源
        sqlSession.close();
        is.close();
    }

    /**
     * 测试查询所有
     */
    @Test
    public void testFindAll() throws IOException {
        //5、执行查询所有方法
        List<User> users = userDao.findAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 测试保存操作
     */
    @Test
    public void testSaveUser() {
        User user = new User();
        user.setSex("男");
        user.setAddress("南京市");
        user.setUsername("autocommit");
        user.setBirthday(new Date());

        //5、执行保存方法
        userDao.saveUser(user);
    }

    /**
     * 测试更新操作
     */
    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(50);
        user.setSex("女");
        user.setAddress("北京市");
        user.setUsername("mary");
        user.setBirthday(new Date());

        //5、执行保存方法
        userDao.updateUser(user);
    }

    /**
     * 测试删除用户
     */
    @Test
    public void testDeleteUser() {
        //5、执行保存方法
        userDao.deleteUser(48);
    }

    /**
     * 测试根据id查询用户
     */
    @Test
    public void testFindById() {
        //5、执行保存方法
        User user = userDao.findById(50);
        System.out.println(user);
    }

    /**
     * 测试根据用户名称查询用户
     */
    @Test
    public void testFindByName() {
        //5、执行保存方法
//        List<User> users = userDao.findByName("%王%");
        List<User> users = userDao.findByName("王");
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 查询总记录条数
     */
    @Test
    public void testFindTotal() {
        //5、执行保存方法
        Integer count = userDao.findTotal();
        System.out.println(count);
    }

    /**
     * 测试保存操作
     */
    @Test
    public void testLastInsertId() {
        User user = new User();
        user.setSex("男");
        user.setAddress("南京市");
        user.setUsername("cheng last insertId");
        user.setBirthday(new Date());
        System.out.println("保存操作之前：" + user);
        //5、执行保存方法
        userDao.saveUser(user);
        System.out.println("保存操作之后：" + user);
    }

    /**
     * 测试根据用户名称查询用户
     */
    @Test
    public void testFindByVo() {
        QueryVo vo = new QueryVo();
        User user = new User();
        user.setUsername("%王%");
        vo.setUser(user);
        //5、执行保存方法
        List<User> users = userDao.findUserByVo(vo);
        for (User u : users) {
            System.out.println(u);
        }
    }
}
