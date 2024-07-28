package com.jyong.springboot.Util.tree;



import com.alibaba.nacos.common.utils.MapUtil;

import java.util.Map;

/**
 * @author Jyong
 * @date 2024/7/28 14:53
 * @description
 */
public class DefaultNodeParser<T> implements NodeParser<TreeNode<T>, T> {

    @Override
    public void parse(TreeNode<T> treeNode, Tree<T> tree) {
        tree.setId(treeNode.getId());
        tree.setParentId(treeNode.getParentId());
        tree.setWeight(treeNode.getWeight());
        tree.setName(treeNode.getName());

        //扩展字段
        final Map<String, Object> extra = treeNode.getExtra();
        if(MapUtil.isNotEmpty(extra)){
            extra.forEach(tree::putExtra);
        }
    }
}