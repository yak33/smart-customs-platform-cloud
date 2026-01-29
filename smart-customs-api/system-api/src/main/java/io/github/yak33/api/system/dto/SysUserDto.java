package io.github.yak33.api.system.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 系统用户 DTO
 *
 * @author ZHANGCHAO
 * @date 2026/01/28
 */
@Data
public class SysUserDto implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /** 用户ID */
    private Long userId;
    
    /** 租户ID */
    private Long tenantId;
    
    /** 部门ID */
    private Long deptId;
    
    /** 用户账号 */
    private String userName;
    
    /** 用户昵称 */
    private String nickName;
    
    /** 用户类型 */
    private String userType;
    
    /** 用户邮箱 */
    private String email;
    
    /** 手机号码 */
    private String phonenumber;
    
    /** 用户性别 */
    private String sex;
    
    /** 头像地址 */
    private String avatar;
    
    /** 帐号状态（0正常 1停用） */
    private String status;
    
    /** 角色ID列表 */
    private List<Long> roleIds;
    
    /** 角色权限列表 */
    private Set<String> rolePermissions;
    
    /** 菜单权限列表 */
    private Set<String> menuPermissions;
}
