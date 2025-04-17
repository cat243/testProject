package com.yupi.project.model.dto.user;

import com.yupi.project.common.PageRequest;
import com.yupi.project.constant.UserConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户查询请求
 *
 * @author yupi
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserQueryRequest extends PageRequest implements Serializable {
    /**
     * id
     */
    private int id;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 账号
     */
    private String username;


    /**
     * 用户角色: user, admin
     */
    private String role = UserConstant.DEFAULT_ROLE;

    /**
     * 用户状态 1正常  0禁止
     */
    private int status = 1;


    private static final long serialVersionUID = 1L;
}