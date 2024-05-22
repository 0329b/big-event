package com.big_event.service.impl;

import com.big_event.mapper.ArticleMapper;
import com.big_event.pojo.Article;
import com.big_event.pojo.PageBean;
import com.big_event.service.ArticleService;
import com.big_event.utils.ThreadLocalUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    //发布文章
    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateUser(id);
        articleMapper.add(article);
    }

    //获取所有数据
    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //创建pageBean对象
        PageBean<Article> pa = new PageBean<>();
        //开启分页
        PageHelper.startPage(pageNum, pageSize);
        //调用mapper
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> list = articleMapper.list(categoryId, state, userId);
        Page<Article> articlePage = (Page<Article>) list;
        //把数据填充到PageBean
        pa.setTotal(articlePage.getTotal());
        pa.setItems(articlePage.getResult());
        return pa;

    }

    @Override
    public void update(Article article) {
        articleMapper.update(article);
    }

    //获取文章详情
    @Override
    public Article datail(Integer id) {

        return articleMapper.detail(id);
    }

    //删除文章
    @Override
    public void delete(Integer id) {
      articleMapper.delete(id);
    }
}
