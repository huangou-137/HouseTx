package pers.ho.bean;

import lombok.*;

import java.io.Serializable;

/**
 * @author 黄欧
 * @Date 2021-03-09 20:36
 * 房子户型（如：?厅?室）
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class HouseType implements Serializable {
    private static final long serialVersionUID = 1L;

    private Byte id;        //户型编号

    private String desc;    //描述

    @Override
    public String toString() {
        return desc;
    }
}
