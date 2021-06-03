package com.linuy.pvr.common.base.entity;

import com.linuy.pvr.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LongTeng
 * @date 2021/03/31
 **/
@Data
public class DataEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String remarks;   // 备注
    protected User createBy;    // 创建者
    protected Date createDate;  // 创建时期
    protected User updateBy;    // 更新者
    protected Date updateDate;  // 更新时间
    protected String delFlag;   // 删除标志(0:正常; 1:删除; 2:审核)

    public void preInsert() {
        this.updateDate = new Date();
        this.createDate = this.updateDate;
    }
}
