package com.jyong.springboot.web.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.rocketmq.shade.com.alibaba.fastjson.JSON;
import com.jyong.springboot.enums.TreeTypeEnum;
import com.jyong.springboot.model.Tree;
import com.jyong.springboot.model.TreeModel;
import com.jyong.springboot.service.TreeService;
import com.jyong.springboot.web.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jyong
 * @date 2024/7/28 12:39
 * @description
 */
@Controller("/tree")
public class TreeController {

    private static  final Logger LOGGER = LoggerFactory.getLogger(TreeController.class);

    @Resource
    private TreeService treeService;

    @RequestMapping(value = "/getAllTree.json", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TreeModel>> getAllTree() {
        Result<List<TreeModel>> result = new Result<>();
        result.setSuccess(true);
        List<Tree> data = treeService.selectAll();
        LOGGER.info(JSONUtil.toJsonStr(data));
        result.setData(buildTree(data));
        return result;
    }
    /**
     * 从一个包含Tree<Long>的列表中构建TreeModel的树形结构。
     *
     * @param flatList 输入的扁平化树模型列表
     * @return 构建后的TreeModel根节点列表
     */
    public static List<TreeModel> buildTree(List<Tree> flatList) {
        Map<Long, TreeModel> map = new HashMap<>();
        List<TreeModel> rootNodes = new ArrayList<>();

        // 将所有的节点转换为TreeModel并放入一个Map中，以ID作为键
        for (Tree node : flatList) {
            TreeModel model = toTreeModel(node);
            map.put(model.getId(), model);
        }

        for (TreeModel model : map.values()) {
            TreeModel parentNode = map.get(model.getParentId());
            if (parentNode != null) {
                // 如果找到了父节点，则将当前节点添加到父节点的子节点列表中
                parentNode.getSubNode().add(model);
            } else {
                // 如果没有找到父节点，则认为它是根节点
                rootNodes.add(model);
            }

        }
        return rootNodes;
    }

    /**
     * 将Tree<Long>转换为TreeModel
     *
     * @param tree 输入的Tree<Long>
     * @return 转换后的TreeModel
     */
    private static TreeModel toTreeModel(Tree tree) {
        TreeModel model = new TreeModel();
        model.setId(tree.getId());
        model.setName(tree.getName());
        model.setParentId(tree.getParentId());
        model.setUpdateTime(tree.getUpdateTime());
        return model;
    }

    @RequestMapping(value = "/getTree.json", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<cn.hutool.core.lang.tree.Tree<Long>>> getTree() {
        Result<List<cn.hutool.core.lang.tree.Tree<Long>>> result = new Result<>();
        result.setSuccess(true);
        List<cn.hutool.core.lang.tree.Tree<Long>> data = treeService.selectTree();
        LOGGER.info(JSONUtil.toJsonStr(data));
        result.setData(data);
        return result;
    }
    @RequestMapping(value = "/getTreeModel.json", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<TreeModel>> getTreeModel() {
        Result<List<TreeModel>> result = new Result<>();
        result.setSuccess(true);
        List<com.jyong.springboot.Util.tree.Tree<Long>> data = treeService.selectTreeModel();
//        LOGGER.info(JSONUtil.toJsonStr(data));

        result.setData(copyTree(data));
        return result;
    }

    public List<TreeModel> copyTree(List<com.jyong.springboot.Util.tree.Tree<Long>> tree) {
        List<TreeModel> copiedSubNode = new ArrayList<>();
        if(tree == null || tree.isEmpty()){
            return copiedSubNode;
        }
        for (com.jyong.springboot.Util.tree.Tree<Long> node : tree) {
            TreeModel  target = JSON.parseObject(JSON.toJSONString(node), TreeModel.class);
            // 递归复制每个子节点
            List<TreeModel> children = copyTree(node.getChildren());
            target.setSubNode(children); // 将子节点列表赋值给当前节点

            copiedSubNode.add(target);
        }
        return copiedSubNode;
    }


    @RequestMapping(value = "/createCategoryNode.json",method = RequestMethod.POST)
    public void createNode(@RequestParam String name, @RequestParam(defaultValue = "0") long parentId) {
        treeService.createTreeNode(name,parentId, TreeTypeEnum.CATEGORY);
    }

    @RequestMapping(value = "/del.json",method = RequestMethod.GET)
    public void del(@RequestParam("id") long id){
        treeService.delete(id);
    }
}
