package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UsedDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UsedDaoImpl();

    @Override
    public boolean regist(User user) {
        //根据用户名查询用户对象
        User u = userDao.findByUsername(user.getUsername());
        //判断u是否为null
        if (u != null) {
            //用户名已存在
            return false;
        }
        //设置激活码
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        //发送邮件
        String content = "<a href='http://localhost/travel/activeUserServlet?code=" + user.getCode() + "'>点击激活账号</a>";
        MailUtils.sendMail(user.getEmail(), content, "激活邮件");
        //保存用户信息
        userDao.save(user);
        return true;
    }

    @Override
    public boolean activc(String code) {
        //根据激活码查询用户对象
        User user = userDao.findByCode(code);
        if (user != null) {
            userDao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User login(User user) {
        User u = null;
        try {
            u = userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }
}
