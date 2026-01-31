package io.github.yak33.system.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.lock.annotation.Lock4j;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import io.github.yak33.common.core.domain.R;
import io.github.yak33.common.excel.utils.ExcelUtil;
import io.github.yak33.common.idempotent.annotation.RepeatSubmit;
import io.github.yak33.common.log.annotation.Log;
import io.github.yak33.common.log.enums.BusinessType;
import io.github.yak33.common.mybatis.core.page.PageQuery;
import io.github.yak33.common.mybatis.core.page.TableDataInfo;
import io.github.yak33.common.web.core.BaseController;
import io.github.yak33.system.domain.bo.SysDictTypeBo;
import io.github.yak33.system.domain.vo.SysDictTypeVo;
import io.github.yak33.system.service.ISysDictTypeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.github.yak33.common.core.domain.dto.DictDataDTO;
import io.github.yak33.common.core.domain.dto.DictTypeDTO;
import io.github.yak33.common.core.service.DictService;

/**
 * 数据字典信息
 *
 * @author ZHANGCHAO
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {

    private final ISysDictTypeService dictTypeService;

    /**
     * 查询字典类型列表
     */
    @SaCheckPermission("system:dict:list")
    @GetMapping("/list")
    public TableDataInfo<SysDictTypeVo> list(SysDictTypeBo dictType, PageQuery pageQuery) {
        return dictTypeService.selectPageDictTypeList(dictType, pageQuery);
    }

    /**
     * 导出字典类型列表
     */
    @Log(title = "字典类型", businessType = BusinessType.EXPORT)
    @SaCheckPermission("system:dict:export")
    @PostMapping("/export")
    public void export(SysDictTypeBo dictType, HttpServletResponse response) {
        List<SysDictTypeVo> list = dictTypeService.selectDictTypeList(dictType);
        ExcelUtil.exportExcel(list, "字典类型", SysDictTypeVo.class, response);
    }

    /**
     * 查询字典类型详细
     *
     * @param dictId 字典ID
     */
    @SaCheckPermission("system:dict:query")
    @GetMapping(value = "/{dictId}")
    public R<SysDictTypeVo> getInfo(@PathVariable Long dictId) {
        return R.ok(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @SaCheckPermission("system:dict:add")
    @Log(title = "字典类型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping
    public R<Void> add(@Validated @RequestBody SysDictTypeBo dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return R.fail("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dictTypeService.insertDictType(dict);
        return R.ok();
    }

    /**
     * 修改字典类型
     */
    @SaCheckPermission("system:dict:edit")
    @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping
    public R<Void> edit(@Validated @RequestBody SysDictTypeBo dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return R.fail("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dictTypeService.updateDictType(dict);
        return R.ok();
    }

    /**
     * 删除字典类型
     *
     * @param dictIds 字典ID串
     */
    @SaCheckPermission("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictIds}")
    public R<Void> remove(@PathVariable Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(Arrays.asList(dictIds));
        return R.ok();
    }

    /**
     * 刷新字典缓存
     */
    @SaCheckPermission("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @Lock4j
    @DeleteMapping("/refreshCache")
    public R<Void> refreshCache() {
        dictTypeService.resetDictCache();
        return R.ok();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public R<List<SysDictTypeVo>> optionselect() {
        List<SysDictTypeVo> dictTypes = dictTypeService.selectDictTypeAll();
        return R.ok(dictTypes);
    }

    /**
     * 根据字典类型和字典值获取字典标签（Feign调用）
     */
    @GetMapping("/label")
    public R<String> getDictLabel(@RequestParam String dictType,
                                  @RequestParam String dictValue,
                                  @RequestParam String separator) {
        DictService dictService = (DictService) dictTypeService;
        return R.ok(dictService.getDictLabel(dictType, dictValue, separator));
    }

    /**
     * 根据字典类型和字典标签获取字典值（Feign调用）
     */
    @GetMapping("/value")
    public R<String> getDictValue(@RequestParam String dictType,
                                  @RequestParam String dictLabel,
                                  @RequestParam String separator) {
        DictService dictService = (DictService) dictTypeService;
        return R.ok(dictService.getDictValue(dictType, dictLabel, separator));
    }

    /**
     * 获取字典下所有的字典值与标签（Feign调用）
     */
    @GetMapping("/map/{dictType}")
    public R<Map<String, String>> getAllDictByDictType(@PathVariable String dictType) {
        DictService dictService = (DictService) dictTypeService;
        return R.ok(dictService.getAllDictByDictType(dictType));
    }

    /**
     * 根据字典类型查询详细信息（Feign调用）
     */
    @GetMapping("/info/{dictType}")
    public R<DictTypeDTO> getDictType(@PathVariable String dictType) {
        DictService dictService = (DictService) dictTypeService;
        return R.ok(dictService.getDictType(dictType));
    }

    /**
     * 根据字典类型查询字典数据列表（Feign调用）
     */
    @GetMapping("/data/type/{dictType}")
    public R<List<DictDataDTO>> getDictData(@PathVariable String dictType) {
        DictService dictService = (DictService) dictTypeService;
        return R.ok(dictService.getDictData(dictType));
    }
}
