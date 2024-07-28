package com.jyong.springboot.Util.tree;

import java.io.Serializable;

/**
 * @author Jyong
 * @date 2024/7/28 14:35
 * @description
 */
public interface Node<T> extends Serializable, Comparable<Node<T>> {
    /**
     * 获取ID
     *
     * @return ID
     */
    T getId();

    /**
     * 设置ID
     *
     * @param id ID
     * @return this
     */
    Node<T> setId(T id);

    /**
     * 获取父节点ID
     *
     * @return 父节点ID
     */
    T getParentId();

    /**
     * 设置父节点ID
     *
     * @param parentId 父节点ID
     * @return this
     */
    Node<T> setParentId(T parentId);

    /**
     * 获取节点标签名称
     *
     * @return 节点标签名称
     */
    CharSequence getName();

    /**
     * 设置节点标签名称
     *
     * @param name 节点标签名称
     * @return this
     */
    Node<T> setName(CharSequence name);

    /**
     * 获取权重
     *
     * @return 权重
     */
    Comparable<?> getWeight();

    /**
     * 设置权重
     *
     * @param weight 权重
     * @return this
     */
    Node<T> setWeight(Comparable<?> weight);

    @SuppressWarnings({"unchecked", "rawtypes", "NullableProblems"})
    @Override
    default int compareTo(Node node) {
        final Comparable weight = this.getWeight();
        if (null != weight) {
            final Comparable weightOther = node.getWeight();
            return weight.compareTo(weightOther);
        }
        return 0;
    }
}
