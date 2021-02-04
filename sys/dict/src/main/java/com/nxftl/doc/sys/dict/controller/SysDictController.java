package com.nxftl.doc.sys.dict.controller;


import com.nxftl.doc.common.util.annotation.RequiredToken;
import com.nxftl.doc.common.util.annotation.ValidAny;
import com.nxftl.doc.common.util.api.ApiResult;
import com.nxftl.doc.sys.dict.service.ISysDictService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

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
    private ISysDictService iSysDictService;


    @PostMapping("/addDict")
    @ApiOperation(value = "添加字典项")
    @RequiredToken
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictName",value = "字典项名称",required = true,dataType = "String"),
            @ApiImplicitParam(name = "dictCode",value = "字典项英文值",required = true,dataType = "String"),
            @ApiImplicitParam(name = "pCode",value = "字典父级Code",required = false,dataType = "String")
    })
    public ApiResult addDict(@ValidAny(exist = true,existError = "字典项名称不能为空") String dictName, @ValidAny(exist = true,existError = "字典项英文值不能为空") String dictCode, String pCode)  {
        return iSysDictService.addDictService(dictName,dictCode,pCode);
    }


    @GetMapping("/getDictByDictCode/{dictCode}")
    @ApiOperation(value = "根据字典英文值(可以是pCode,可以是dictCode,得到的数据一定是唯一的)获取字典项详细信息")
    @ApiImplicitParam(
            name = "dictCode",
            value = "字典英文值",
            dataType = "String",
            required = true,
            paramType ="PathVariable")
    public ApiResult getDictByDictCode(@PathVariable("dictCode") @ValidAny(exist = true,existError = "字典项英文值不能为空")String dictCode)  {
        return iSysDictService.findDictByDictCodeService(dictCode);
    }


    @GetMapping("/getDictByDictName/{dictName}")
    @ApiOperation(value = "根据字典名称获取字典项")
    @ApiImplicitParam(
            name = "dictName",
            value = "字典名称",
            dataType = "String",
            required = true,
            paramType = "PathVariable")
    public ApiResult getDictByDictName(@PathVariable("dictName")@ValidAny(exist = true,existError = "字典项名称不能为空") String dictName)  {
        return iSysDictService.findDictByDictNameService(dictName);
    }


    @GetMapping("/getDictAnyByPCode/{pCode}")
    @ApiOperation("根据父级code获取该父级code下所有子级字典项")
    @ApiImplicitParam(
            name = "pCode",
            value = "父级字典英文值",
            dataType = "String",
            required = true,
            paramType = "PathVariable")
    public ApiResult getDictAnyByPCode(@PathVariable("pCode")@ValidAny(exist = true,existError = "pCode不能为空") String pCode)  {
        return iSysDictService.findDictAnyByPCodeService(pCode);
    }
}
