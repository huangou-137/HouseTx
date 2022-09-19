package pers.ho.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装了返回结果&lt;V&gt; 及 错误提示信息
 * @author 黄欧
 * @Date 2021/10/24 22:21
 */
public class Result<V> {
    V value;
    List<String> tips;

    public Result() {}

    public Result(String tip) {
        this(null, tip);
    }

    public Result(List<String> tips) {
        this(null, tips);
    }

    public Result(V value, String tip) {
        this.value = value;
        this.tips = new ArrayList<>();
        tips.add(tip);
    }

    public Result(V value, List<String> tips) {
        this.value = value;
        this.tips = tips;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public List<String> getTips() {
        return tips;
    }
    public String getTipsStr() {
        if (tips.size() == 1) {
            return tips.get(0);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tips.size(); i++) {
            sb.append('[').append(i).append(']').append(tips.get(i)).append('.');
        }
        return sb.toString();
    }

    public void setTips(List<String> tips) {
        this.tips = tips;
    }

    public void addTip(String tip) {
        if (this.tips == null) {
            this.tips = new ArrayList<>();
        }
        this.tips.add(tip);
    }

    public void addTips(List<String> extTips) {
        if (this.tips == null) {
            this.tips = new ArrayList<>();
        }
        this.tips.addAll(extTips);
    }

}
