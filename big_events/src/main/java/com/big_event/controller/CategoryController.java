package com.big_event.controller;

import com.big_event.pojo.Category;
import com.big_event.pojo.Result;
import com.big_event.service.CategoryService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    //新曾文章分类
    @PostMapping
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){
        categoryService.add(category);
        return Result.success();
    }
    //获取文章列表
    @GetMapping
    public Result<List<Category>> list(){
       List<Category> cs= categoryService.list();
       return Result.success(cs);
    }

    //获取文章详情
    @GetMapping("/detail")
    public Result<Category> detail(Integer id){
       Category category= categoryService.findById(id);
       return Result.success(category);
    }
    //更新文章分类
    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }
    //删除文章分类
    @DeleteMapping
    public Result delete(Integer id){
        categoryService.delete(id);
        return Result.success();
    }
}
