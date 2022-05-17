package com.run.blog.vo.params;

import lombok.Data;

@Data
public class ArticleBodyParam {

    private String content;

    // 将Markdown的content转为Html的content，给浏览器渲染
    private String contentHtml;

}
