package com.jyong.springboot.Util.tree;

/**
 * @author Jyong
 * @date 2024/7/28 14:53
 * @description
 */
public interface NodeParser<T,E>{
    /**
     * @param object   源数据实体
     * @param treeNode 树节点实体
     */
    void parse(T object, Tree<E> treeNode);
}
