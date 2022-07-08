package com.imooc.form;

import lombok.Data;

/**
 * @author yuhe
 * @date 2021/11/26 21:50
 */

@Data
public class CategoryForm {

    private Integer categoryId;

    //类目名字
    private String categoryName;

    //类目编号
    private Integer categoryType;
}
