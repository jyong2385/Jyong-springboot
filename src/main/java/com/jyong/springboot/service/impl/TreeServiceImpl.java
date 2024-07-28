package com.jyong.springboot.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.JarClassLoader;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.jyong.springboot.Util.tree.TreeUtils;
import com.jyong.springboot.enums.TreeTypeEnum;
import com.jyong.springboot.dao.mapper.TreeMapper;
import com.jyong.springboot.model.Tree;
import com.jyong.springboot.model.TreeExample;
import com.jyong.springboot.model.TreeModel;
import com.jyong.springboot.service.TreeService;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jyong
 * @date 2024/7/28 12:12
 * @description
 */
@Service
public class TreeServiceImpl implements TreeService {

    @Resource
    private TreeMapper treeMapper;

    @Override
    public List<Tree> selectAll() {
        TreeExample treeExample = new TreeExample();
        treeExample.createCriteria();
        return treeMapper.selectByExample(treeExample);
    }

    @Override
    public void createTreeNode(Tree tree) {
        int insert = treeMapper.insert(tree);
        if(insert <=0){
            throw new RuntimeException("tree node 创建失败！！！,name="+tree.getName());
        }
    }

    @Override
    public void createTreeNode(String name, long parentId,TreeTypeEnum type) {

        //不能有重复的
        TreeExample treeExample = new TreeExample();
        treeExample.createCriteria().andNameEqualTo(name)
                .andParentIdEqualTo(parentId)
                        .andTypeEqualTo(type.code);
        List<Tree> trees = treeMapper.selectByExample(treeExample);
        if(!CollectionUtils.isEmpty(trees)){
            throw new RuntimeException("类目已存在！！！，name:"+name);
        }

        Tree tree = new Tree();
        tree.setName(name);
        tree.setParentId(parentId);
        tree.setCreateTime(new Date());
        tree.setUpdateTime(new Date());
        tree.setType(type.code);
        tree.setPath(getPath(parentId,name));
        int insert = treeMapper.insert(tree);
        if(insert <=0){
            throw new RuntimeException("tree node 创建失败！！！,name="+tree.getName());
        }
    }

    @Override
    public void delete(long id) {
        treeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<cn.hutool.core.lang.tree.Tree<Long>> selectTree() {
        //查询所有
        TreeExample treeExample = new TreeExample();
        List<Tree> trees = treeMapper.selectByExample(treeExample);
        List<TreeNode<Long>> list = new ArrayList<>();
        List<TreeNode<Long>> collect = trees.stream().map(e -> {
            TreeNode<Long> treeNode = new TreeNode();
            treeNode.setId(e.getId());
            treeNode.setName(e.getName());
            treeNode.setParentId(e.getParentId());
            treeNode.setWeight(e.getUpdateTime());
            Map<String, Object> extra = new HashMap<>();
            extra.put("type", e.getType());
            extra.put("createTime", e.getCreateTime());
            extra.put("updateTime", e.getUpdateTime());
            extra.put("path", e.getPath());
            treeNode.setExtra(extra);
            return treeNode;
        }).collect(Collectors.toList());
        List<cn.hutool.core.lang.tree.Tree<Long>> build = TreeUtil.build(collect, 0L);
        return build;
    }

    @Override
    public List<com.jyong.springboot.Util.tree.Tree<Long>> selectTreeModel() {
        TreeExample treeExample = new TreeExample();
        List<Tree> trees = treeMapper.selectByExample(treeExample);
        List<com.jyong.springboot.Util.tree.TreeNode<Long>> treeNodeList = trees.stream().map(e -> {
            com.jyong.springboot.Util.tree.TreeNode<Long> treeNode = new com.jyong.springboot.Util.tree.TreeNode();
            treeNode.setId(e.getId());
            treeNode.setName(e.getName());
            treeNode.setParentId(e.getParentId());
            treeNode.setWeight(e.getUpdateTime());
            Map<String, Object> extra = new HashMap<>();
            extra.put("type", e.getType());
            extra.put("createTime", e.getCreateTime());
            extra.put("updateTime", e.getUpdateTime());
            extra.put("path", e.getPath());
            treeNode.setExtra(extra);
            return treeNode;
        }).collect(Collectors.toList());

        return TreeUtils.build(treeNodeList, 0L);
    }


    private String getPath(long parentId,String name) {
        if(parentId ==0){
            return name;
        }
        //找到父类所有节点拼接
        Tree tree = treeMapper.selectByPrimaryKey(parentId);
        String subTreeName = tree.getName();
        Long subParentId = tree.getParentId();
        name = subTreeName+"-"+name;
        return getPath(subParentId,name);
    }
}
