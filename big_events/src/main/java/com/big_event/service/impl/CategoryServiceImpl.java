package com.big_event.service.impl;

import com.big_event.mapper.CategoryMapper;
import com.big_event.pojo.Category;
import com.big_event.service.CategoryService;
import com.big_event.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    //添加文章分类
    @Override
    public void add(Category category) {
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        category.setCategoryUser(userId);
        categoryMapper.add(category);
    }

    //获取文章数据
    @Override
    public List<Category> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return categoryMapper.list(userId);
    }

    //获取文章详情
    @Override
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }

    //更新文章分类
    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    //删除文章分类
    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }


}
