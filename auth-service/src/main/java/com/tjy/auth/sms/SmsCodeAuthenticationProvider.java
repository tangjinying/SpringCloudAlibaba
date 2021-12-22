package com.tjy.auth.sms;

import com.tjy.auth.SecurityUser;
import com.tjy.dao.PermissionMapper;
import com.tjy.dao.RoleMapper;
import com.tjy.entity.Permission;
import com.tjy.entity.Role;
import com.tjy.entity.User;
import com.tjy.service.IUserService;
import com.tjy.util.SpringContextHolder;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author tjy
 * @Date 2021/12/13 10:49
 * @Version 1.0
 */
@Log4j2
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private IUserService userService;

    private RoleMapper roleMapper;

    private PermissionMapper permissionMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        SmsCodeAuthenticationToken smsCodeAuthenticationToken = (SmsCodeAuthenticationToken) authentication;
        userService = SpringContextHolder.getBean(IUserService.class);
		roleMapper = SpringContextHolder.getBean(RoleMapper.class);
		permissionMapper = SpringContextHolder.getBean(PermissionMapper.class);

        String mobile = (String) smsCodeAuthenticationToken.getPrincipal();

        //校验手机号验证码
        checkSmsCode(mobile);

        User user = userService.getUserByMobile(mobile);
        if(null == user){
            throw new BadCredentialsException("Invalid mobile!");
        }
		//获取该用户所有的角色
		List<Role> roles = roleMapper.listRolesByUserId(user.getId());
		user.setRoles(roles.stream().map(Role::getRoleCode).collect(Collectors.toList()));
		List<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
		//获取这些角色用户的权限
		List<String> permissionList = new ArrayList<>();
		for (Integer roleId : roleIds) {
			List<Permission> permissions = permissionMapper.listPermissionsByRoles(roleId);
			permissions.forEach(permission -> permissionList.add("["+ permission.getMethod() + "]" + permission.getUrl()));
//			permissions.forEach(permission -> permissionList.add(permission.getUrl()));
		}
		user.setPermissions(permissionList);


        //授权通过
        UserDetails userDetails = buildUserDetails(user);
        return new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
    }

    /**
     * 构建用户认证信息
     * @param user 用户对象
     * @return UserDetails
     */
    private UserDetails buildUserDetails(User user) {
		Set<String> authSet = new HashSet<>();
		if(!CollectionUtils.isEmpty(user.getRoles())){
			user.getRoles().forEach(role -> authSet.add("ROLE_"+role));
			authSet.addAll(user.getPermissions());
		}
		List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(authSet.toArray(new String[0]));
		return new SecurityUser(user.getId(),user.getMobile(),user.getUsername(),user.getPassword(),authorityList);
    }



    /**
     * 校验手机号与验证码的绑定关系是否正确
     *  todo 需要根据业务逻辑自行处理
     * @author javadaily
     * @date 2020/7/23 17:31
     * @param mobile 手机号码
     */
    private void checkSmsCode(String mobile) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取验证码
        String smsCode = request.getParameter("smsCode");
        if(StringUtils.isEmpty(smsCode) || !"666666".equals(smsCode)){
            throw new BadCredentialsException("Incorrect sms code,please check !");
        }
        //todo  手机号与验证码是否匹配
    }

    /**
     * ProviderManager 选择具体Provider时根据此方法判断
     * 判断 authentication 是不是 SmsCodeAuthenticationToken 的子类或子接口
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
