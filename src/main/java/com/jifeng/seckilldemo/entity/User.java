package com.jifeng.seckilldemo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author jifeng
 * @since 2022-03-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;

    private String nickname;

    /**
     * MD5(MD5(pass明文+固定salt)+salt)
     */
    private String password;

    private String salt;

    /**
     * Logo
     */
    private String head;

    private LocalDateTime registerDate;

    private LocalDateTime lastLoginDate;

    private Integer loginCount;


}
