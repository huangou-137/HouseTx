package pers.ho.bean;

import lombok.*;

import java.io.Serializable;

/**
 * @author 黄欧
 * @Date 2021-03-09 20:35
 * 房子类型
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class HouseKind implements Serializable {
    private static final long serialVersionUID = 1L;

    private Byte id;        //类型编号

    private String name;    //名称

    @Override
    public String toString() {
        return name;
    }
}
