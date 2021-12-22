package com.tjy.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tjy.dao.PermissionMapper;
import com.tjy.dao.RoleMapper;
import com.tjy.dao.UserMapper;
import com.tjy.entity.Permission;
import com.tjy.entity.Role;
import com.tjy.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/15 18:16
 * @Version 1.0
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //获取本地用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,userName);
        User user = userMapper.selectOne(wrapper);

        if(user != null){
        	//获取该用户所有的角色
			List<Role> roles = roleMapper.listRolesByUserId(user.getId());
			user.setRoles(roles.stream().map(Role::getRoleCode).collect(Collectors.toList()));
			List<Integer> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
			//获取这些角色用户的权限
			List<String> permissionList = new ArrayList<>();
			for (Integer roleId : roleIds) {
				List<Permission> permissions = permissionMapper.listPermissionsByRoles(roleId);
//				permissions.forEach(permission -> permissionList.add("["+ permission.getMethod() + "]" + permission.getUrl()));
				permissions.forEach(permission -> permissionList.add(permission.getPermission()));
			}
			user.setPermissions(permissionList);

			return buildUserDetails(user);
        }else{
            throw new UsernameNotFoundException("用户["+userName+"]不存在");
        }
    }

	/**
	 * 构建oAuth2用户，将角色和权限赋值给用户，角色使用ROLE_作为前缀
	 * @param user 用户
	 * @return
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
}
