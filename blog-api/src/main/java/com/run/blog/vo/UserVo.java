package com.run.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */
@Data
public class UserVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String nickname;

    private String avatar;

}
