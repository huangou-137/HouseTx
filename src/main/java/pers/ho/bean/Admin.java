package pers.ho.bean;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@TableName("t_admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "admin_id", type = IdType.AUTO)
    private Short id;           //管理员ID

    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9_\\u4e00-\\u9fa5]{6,20}", message = "必须由6-20位汉字、字母、数字或_混合组成")
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String adminName;   //管理员名

    @Pattern(regexp = "\\w{8,20}", message = "密码必须由8-20位字母、数字下划线组成")
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String pass;        //密码

    @Pattern(regexp = "\\d{3,4}-\\d{7,8}", message = "固话号格式不对")
    @TableField(updateStrategy = FieldStrategy.NOT_EMPTY)
    private String tel;         //联系固话号

    @Email
    private String email;       //电子邮箱

    @TableField("`desc`")
    private String desc;        //简介

}
