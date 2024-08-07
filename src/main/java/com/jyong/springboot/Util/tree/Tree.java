package com.jyong.springboot.Util.tree;


import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author Jyong
 * @date 2024/7/28 14:34
 * @description
 */
public class Tree<T> extends LinkedHashMap<String, Object> implements Node<T> {

    private static final long serialVersionUID = 1018721782572611513L;
    private final TreeNodeConfig treeNodeConfig;
    private Tree<T> parent;

    public Tree() {
        this(null);
    }

    /**
     * 构造
     *
     * @param treeNodeConfig TreeNode配置
     */
    public Tree(TreeNodeConfig treeNodeConfig) {
        super();
        this.treeNodeConfig = Optional.ofNullable(treeNodeConfig).orElse(TreeNodeConfig.DEFAULT_CONFIG);;
    }

    /**
     * 获取父节点
     *
     * @return 父节点
     * @since 5.2.4
     */
    public Tree<T> getParent() {
        return parent;
    }

    /**
     * 获取ID对应的节点，如果有多个ID相同的节点，只返回第一个。<br>
     * 此方法只查找此节点及子节点，采用广度优先遍历。
     *
     * @param id ID
     * @return 节点
     * @since 5.2.4
     */
    public Tree<T> getNode(T id) {
        return TreeUtils.getNode(this, id);
    }

    /**
     * 获取所有父节点名称列表
     *
     * <p>
     * 比如有个人在研发1部，他上面有研发部，接着上面有技术中心<br>
     * 返回结果就是：[研发一部, 研发中心, 技术中心]
     *
     * @param id                 节点ID
     * @param includeCurrentNode 是否包含当前节点的名称
     * @return 所有父节点名称列表
     * @since 5.2.4
     */
    public List<CharSequence> getParentsName(T id, boolean includeCurrentNode) {
        return TreeUtils.getParentsName(getNode(id), includeCurrentNode);
    }

    /**
     * 获取所有父节点名称列表
     *
     * <p>
     * 比如有个人在研发1部，他上面有研发部，接着上面有技术中心<br>
     * 返回结果就是：[研发一部, 研发中心, 技术中心]
     *
     * @param includeCurrentNode 是否包含当前节点的名称
     * @return 所有父节点名称列表
     * @since 5.2.4
     */
    public List<CharSequence> getParentsName(boolean includeCurrentNode) {
        return TreeUtils.getParentsName(this, includeCurrentNode);
    }

    /**
     * 设置父节点
     *
     * @param parent 父节点
     * @return this
     * @since 5.2.4
     */
    public Tree<T> setParent(Tree<T> parent) {
        this.parent = parent;
        if (null != parent) {
            this.setParentId(parent.getId());
        }
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getId() {
        return (T) this.get(treeNodeConfig.getIdKey());
    }

    @Override
    public Tree<T> setId(T id) {
        this.put(treeNodeConfig.getIdKey(), id);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getParentId() {
        return (T) this.get(treeNodeConfig.getParentIdKey());
    }

    @Override
    public Tree<T> setParentId(T parentId) {
        this.put(treeNodeConfig.getParentIdKey(), parentId);
        return this;
    }

    @Override
    public CharSequence getName() {
        return (CharSequence) this.get(treeNodeConfig.getNameKey());
    }

    @Override
    public Tree<T> setName(CharSequence name) {
        this.put(treeNodeConfig.getNameKey(), name);
        return this;
    }

    @Override
    public Comparable<?> getWeight() {
        return (Comparable<?>) this.get(treeNodeConfig.getWeightKey());
    }

    @Override
    public Tree<T> setWeight(Comparable<?> weight) {
        this.put(treeNodeConfig.getWeightKey(), weight);
        return this;
    }

    @SuppressWarnings("unchecked")
    public List<Tree<T>> getChildren() {
        return (List<Tree<T>>) this.get(treeNodeConfig.getChildrenKey());
    }

    public void setChildren(List<Tree<T>> children) {
        this.put(treeNodeConfig.getChildrenKey(), children);
    }

    /**
     * 扩展属性
     *
     * @param key   键
     * @param value 扩展值
     */
    public void putExtra(String key, Object value) {
        Assert.notNull(key, "Key must be not empty !");
        this.put(key, value);
    }
}
