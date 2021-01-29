package com.nxftl.doc.sys.dict.controller;


import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.sys.dict.entity.SysDict;
import com.nxftl.doc.sys.dict.service.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author darkltl
 * @since 2021-01-29
 */
@RestController
@RequestMapping("/dict")
@Api(value = "字典接口",tags = {"字典公共接口"})
public class SysDictController {

    @Resource
    private ISysDictService dictService;


    @PostMapping("/addDict")
    @ApiOperation(value = "添加字典项")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictName",value = "字典项名称",required = true,dataType = "String"),
            @ApiImplicitParam(name = "dictCode",value = "字典项英文值",required = true,dataType = "String"),
            @ApiImplicitParam(name = "pCode",value = "字典父级Code",required = false,dataType = "String")
    })
    public ApiResult addDict(String dictName,String dictCode,String pCode) throws Exception {
        return dictService.addDictService(dictName,dictCode,pCode);
    }

}
