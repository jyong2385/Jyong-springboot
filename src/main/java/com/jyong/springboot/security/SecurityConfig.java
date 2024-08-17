//package com.jyong.springboot.security;
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    /**
//     * 实例：
//     * level1 level1 level1 三个文件夹下有不同的功能页面
//     * 角色： vip1 vip2 vip3
//     *
//     * @param http
//     * @throws Exception
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //配置规则
//        http.authorizeRequests()
//
//
//        http.formLogin();  //当用户没有权限时,跳回到首页
//    }
//
//
//
//    /**
//     * 认证
//     *
//     * @param auth
//     * @throws Exception
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //将密码进行编码
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String password = bCryptPasswordEncoder.encode("123456");
//        User.UserBuilder user = User.withDefaultPasswordEncoder();
//        //设置认证，此处的用户名和密码都应从数据库中获取
//        auth.inMemoryAuthentication()
//                .withUser("root").password(password).roles("vip1", "vip2", "vip3")
//                .and()
//                .withUser("guest").password(password).roles("vip2");
//
//
//
////        DataSource dataSrouce = jdbcConfig.getDataSrouce();
//        //jdbc方式获取
////        auth.jdbcAuthentication()
////                .dataSource(dataSource)
////                .withDefaultSchema()
////                .withUser(user.username("user").password(password).roles("vpi1"))
////                .withUser(user.username("admin").password(password).roles("vip2", "vip2", "vip3"));
//
//        super.configure(auth);
//    }
//}
