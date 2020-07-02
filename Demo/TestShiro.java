import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

import java.util.Scanner;

/**
 * @description:
 * @Classname TestShiro
 * @author: LJP
 * @time: 2020/7/2 15:29
 * @Created by 小星星
 */
public class TestShiro {
    public static void main(String[] args) {
        Logger log = Logger.getLogger(TestShiro.class);

        Scanner input = new Scanner(System.in);
        System.out.println("请输入账号：");
        String userName = input.next();
        System.out.println("请输入密码：");
        String pwd = input.next();

        //1、创建安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //2、创建realm  读取shiro.ini文件读取到iniRealm中
        IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
        //3、将realm设置给安全管理器
        securityManager.setRealm(iniRealm);
        //4、将realm设置给securityUtils工具
        SecurityUtils.setSecurityManager(securityManager);
        //5、通过securityUtil工具类获取subject对象
        Subject subject = SecurityUtils.getSubject();


        //认证流程
        //1、将认证账号和密码封装到token对象中
        UsernamePasswordToken token = new UsernamePasswordToken(userName, pwd);
        //2、通过subject对象调用login方法进行认证申请
        subject.login(token);

        //3.进行异常捕获判断登录验证，成功为true  IncorrectCredentialsException
        log.debug(subject.isAuthenticated());


        //授权      只有zhangsan才是这个角色   登录才为true
        log.debug(subject.hasRole("seller"));

        //判断登录的权限   有为true
        log.debug(subject.isPermitted("order-del"));
    }
}
