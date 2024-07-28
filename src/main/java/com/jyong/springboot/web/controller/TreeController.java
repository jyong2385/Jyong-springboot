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
import java.util.List;

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
    public Result<List<Tree>> getAllTree() {
        Result<List<Tree>> result = new Result<>();
        result.setSuccess(true);
        List<Tree> data = treeService.selectAll();
        LOGGER.info(JSONUtil.toJsonStr(data));
        result.setData(data);
        return result;
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
