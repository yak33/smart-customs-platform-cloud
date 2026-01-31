package io.github.yak33.api.system.feign;

import io.github.yak33.common.core.domain.R;
import io.github.yak33.common.core.domain.dto.DictDataDTO;
import io.github.yak33.common.core.domain.dto.DictTypeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 字典服务 Feign 客户端
 *
 * @author ZHANGCHAO
 * @date 2026/01/28
 */
@FeignClient(name = "smart-customs-system", contextId = "dictFeignClient")
public interface DictFeignClient {

    /**
     * 根据字典类型和字典值获取字典标签
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @param separator 分隔符
     * @return 字典标签
     */
    @GetMapping("/system/dict/type/label")
    R<String> getDictLabel(@RequestParam("dictType") String dictType,
                           @RequestParam("dictValue") String dictValue,
                           @RequestParam("separator") String separator);

    /**
     * 根据字典类型和字典标签获取字典值
     *
     * @param dictType  字典类型
     * @param dictLabel 字典标签
     * @param separator 分隔符
     * @return 字典值
     */
    @GetMapping("/system/dict/type/value")
    R<String> getDictValue(@RequestParam("dictType") String dictType,
                           @RequestParam("dictLabel") String dictLabel,
                           @RequestParam("separator") String separator);

    /**
     * 获取字典下所有的字典值与标签
     *
     * @param dictType 字典类型
     * @return dictValue为key，dictLabel为值组成的Map
     */
    @GetMapping("/system/dict/type/map/{dictType}")
    R<Map<String, String>> getAllDictByDictType(@PathVariable("dictType") String dictType);

    /**
     * 根据字典类型查询详细信息
     *
     * @param dictType 字典类型
     * @return 字典类型详细信息
     */
    @GetMapping("/system/dict/type/info/{dictType}")
    R<DictTypeDTO> getDictType(@PathVariable("dictType") String dictType);

    /**
     * 根据字典类型查询字典数据列表
     *
     * @param dictType 字典类型
     * @return 字典数据列表
     */
    @GetMapping("/system/dict/data/type/{dictType}")
    R<List<DictDataDTO>> getDictData(@PathVariable("dictType") String dictType);
}
