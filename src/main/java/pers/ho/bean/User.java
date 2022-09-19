package pers.ho.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@TableName("t_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;        //用户ID

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9_\\u4e00-\\u9fa5]{6,20}", message = "用户名必须由6-20位汉字、字母、数字或_混合组成")
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String username;    //用户名

    @Pattern(regexp = "\\w{6,20}", message = "登录密码必须由6-20位字母、数字下划线组成")
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String loginPass;   //登录密码

    //@Pattern校验的字段必须不为空，但手机号可以为空，故表达式需改为：(原表达式)|
    @Pattern(regexp = "((13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8})|", message = "手机号格式不对")
    private String phone;       //手机号

    @Email
    private String email;       //电子邮箱

    @Pattern(regexp = "[A-Za-z0-9]{6,10}", message = "交易密码必须由6-10位字母或数字组成")
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String txPass;      //交易密码

    @TableField("`desc`")
    private String desc;        //简介

}