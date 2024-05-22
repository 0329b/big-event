package com.big_event.controller;

import com.big_event.pojo.Article;
import com.big_event.pojo.PageBean;
import com.big_event.pojo.Result;

import com.big_event.service.ArticleService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    //发布文章全部内容
    @PostMapping
    public Result add(@RequestBody @Validated Article article) {
        articleService.add(article);
        return Result.success();
    }
    //查询所以数据
    @GetMapping
    public Result<PageBean<Article>> list(Integer pageNum,Integer pageSize,@RequestParam(required = false) Integer categoryId,@RequestParam(required = false) String state){
        PageBean<Article> pa= articleService.list(pageNum,pageSize,categoryId,state );
        return Result.success(pa);
    }
    //更新数据
    @PutMapping
    public Result update(@RequestBody Article article){
        articleService.update(article);
        return Result.success();
    }
    //获取文章详情
    @GetMapping("/detail")
    public Result<Article> detail(Integer id){
        Article article= articleService.datail(id);
        return Result.success(article);
    }
    //删除文章
    @DeleteMapping
    public Result delete(Integer id){
        articleService.delete(id);
        return Result.success();
    }
}
