package com.demo.configs;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.demo.service.realm.ShiroUserRealm;

/**
 * Shiro配置类
 * @author Administrator
 *
 */
@Configuration
public class ShiroConfig {
	/**
	 * ShiroFilterFactoryBean设置安全管理器
	 * @param securityManager
	 * @return
	 */
    @Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(
			@Qualifier("securityManager")DefaultWebSecurityManager securityManager){
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		//设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		
		/**
		 * Shiro内置过滤器，可以实现权限相关的拦截器
		 *	常用的过滤器：
		 * 	  anon: 无需认证（登录）可以访问
		 *	  authc: 必须认证才可以访问
		 * 	  user: 如果使用rememberMe的功能可以直接访问
		 *	  perms： 该资源必须得到资源权限才可以访问
		 *	  role: 该资源必须得到角色权限才可以访问
		 */
		Map<String,String> filterMap = new LinkedHashMap<String,String>();
		filterMap.put("/bower_components/**", "anon");
		filterMap.put("/dist/**", "anon");
		filterMap.put("/plugins/**", "anon");
		filterMap.put("/doPaperUI", "anon");
		filterMap.put("/paper/findAllById", "anon");
		filterMap.put("/doLogin", "anon");
		filterMap.put("/doLogout", "logout");
		filterMap.put("/**", "authc");
		
		//修改调整的登录页面
		shiroFilterFactoryBean.setLoginUrl("/doLoginUI");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
		return shiroFilterFactoryBean;
	}
	/**
	 * 创建DefaultWebSecurityManager
	 * @param userRealm
	 * @return
	 */
	@Bean(name="securityManager")
	public DefaultWebSecurityManager getDefaultWebSecurityManager(
			@Qualifier("shiroUserRealm")ShiroUserRealm shiroUserRealm){
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(shiroUserRealm);//关联realm
		return securityManager;
	}
	/**
	 * 创建Realm
	 * @return
	 */
	@Bean(name="shiroUserRealm")
	public ShiroUserRealm getRealm(){
		return new ShiroUserRealm();
	}
	
	/**
	 * 配置授权属性
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(
			@Qualifier("securityManager")DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorization = new AuthorizationAttributeSourceAdvisor();
		authorization.setSecurityManager(securityManager);
		return authorization;
	}
}