package com.jyong.springboot.service;

import com.jyong.springboot.enums.TreeTypeEnum;
import com.jyong.springboot.model.Tree;
import com.jyong.springboot.model.TreeModel;

import java.util.List;

/**
 * @author Jyong
 * @date 2024/7/28 12:11
 * @description
 */
public interface TreeService {

    /**
     * 查询所有标签
     *
     * @return
     */
    List<Tree> selectAll();

    /**
     * 创建tree节点
     */
    void createTreeNode(Tree tree);

    /**
     * 创建tree节点
     */
    void createTreeNode(String name, long parentId, TreeTypeEnum type);

    void delete(long id);

    List<cn.hutool.core.lang.tree.Tree<Long>> selectTree();

    List<com.jyong.springboot.Util.tree.Tree<Long>> selectTreeModel();
}
