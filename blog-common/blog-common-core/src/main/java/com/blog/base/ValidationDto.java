package com.blog.base;

import lombok.Data;

/**
 * 分组校验
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
public abstract class ValidationDto {

    private interface Delete {
    }


    private interface DeleteBatchByIds {
    }


    private interface DeleteById {
    }


    private interface DeleteByMap {
    }


    private interface Insert {
    }


    private interface SelectBatchByIds {
    }


    private interface SelectById {
    }


    private interface SelectByMap {
    }


    private interface SelectCount {
    }


    private interface SelectList {
    }


    private interface SelectMaps {
    }


    private interface SelectMapsPage {
    }


    private interface SelectObjs {
    }


    private interface SelectOne {
    }


    private interface SelectPage {
    }


    private interface Update {
    }


    private interface UpdateById {
    }


}
