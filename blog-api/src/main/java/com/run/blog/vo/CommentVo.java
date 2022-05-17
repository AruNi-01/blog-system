package com.run.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */
@Data
public class CommentVo {

    // 因为分布式id 的Long过长前端解析精度损失,会将值改变,需要进行Json序列化，转成String
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private UserVo author;

    private String content;

    private List<CommentVo> children;

    private String createDate;

    private Integer level;

    private UserVo toUser;

}
