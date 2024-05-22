package com.big_event.pojo;

import com.big_event.anno.State;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private Integer id;   //文章的id
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String title;  //文章的标题
    @NotEmpty
    private String content;  //文章内容
    @NotEmpty
    @URL
    private String coverImg;  //封面图片
    @State
    private String state;  //发布状态
    @NotNull
    private Integer categoryId; //文章分类id
    @JsonIgnore
    private Integer createUser; //创建人id
    private LocalDateTime createTime; //创建事件
    private LocalDateTime updateTime; //更新时间
}
