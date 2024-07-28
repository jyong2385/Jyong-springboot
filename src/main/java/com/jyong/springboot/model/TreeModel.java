package com.jyong.springboot.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jyong
 * @date 2024/7/28 13:33
 * @description
 */
public class TreeModel extends Tree{

    private static final long serialVersionUID = 7136506478595631516L;
    /**
     * 子节点
     */
    private List<TreeModel> subNode = new ArrayList<>();

    public List<TreeModel> getSubNode() {
        return subNode;
    }

    public void setSubNode(List<TreeModel> subNode) {
        this.subNode = subNode;
    }
}
