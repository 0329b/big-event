package com.big_event.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {
    private Integer code; //状态码 0-成功 -1-失败
    private String mesg;  //提示信息
    private T data;  //响应数据

    //返回数据方法
    public static <E> Result<E> success(E data){
        return new Result<>(0,"操作成功",data);
    }
    //返回操作成功响应结果
    public static Result success(){
        return new Result(0,"操作成功",null);
    }
    public static Result error(String mesg){
        return new Result(1,mesg,null);
    }
}
