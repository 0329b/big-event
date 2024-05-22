package com.big_event.service;


import com.big_event.pojo.Article;
import com.big_event.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    void update(Article article);

    Article datail(Integer id);

    void delete(Integer id);
}
