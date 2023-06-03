package com.blog.base;

import jakarta.validation.groups.Default;
import lombok.Data;

/**
 * 分组校验
 *
 * @author IKUN
 * @since 2023-05-31 21:25:43
 */
@Data
public abstract class ValidationDto {

    public interface Delete extends Default {
    }


    public interface DeleteBatchByIds extends Default {
    }


    public interface DeleteById extends Default {
    }


    public interface DeleteByMap extends Default {
    }


    public interface Insert extends Default {
    }


    public interface SelectBatchByIds extends Default {
    }


    public interface SelectById extends Default {
    }


    public interface SelectByMap extends Default {
    }


    public interface SelectCount extends Default {
    }


    public interface SelectList extends Default {
    }


    public interface SelectMaps extends Default {
    }


    public interface SelectMapsPage extends Default {
    }


    public interface SelectObjs extends Default {
    }


    public interface SelectOne extends Default {
    }


    public interface SelectPage extends Default {
    }


    public interface Update extends Default {
    }


    public interface UpdateById extends Default {
    }


}
