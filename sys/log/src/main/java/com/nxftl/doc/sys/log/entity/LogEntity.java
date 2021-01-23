package com.nxftl.doc.sys.log.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LogEntity {

    private String testEntityName;

    private String testEntityAddr;

    private Integer testEntityNum;
}
