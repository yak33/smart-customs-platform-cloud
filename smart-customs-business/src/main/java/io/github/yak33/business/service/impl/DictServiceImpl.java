package io.github.yak33.business.service.impl;

import io.github.yak33.api.system.feign.DictFeignClient;
import io.github.yak33.common.core.domain.dto.DictDataDTO;
import io.github.yak33.common.core.domain.dto.DictTypeDTO;
import io.github.yak33.common.core.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 字典服务实现 - 通过 Feign 调用 System 服务
 *
 * @author ZHANGCHAO
 */
@RequiredArgsConstructor
@Service
public class DictServiceImpl implements DictService {

    private final DictFeignClient dictFeignClient;

    @Override
    public String getDictLabel(String dictType, String dictValue, String separator) {
        return dictFeignClient.getDictLabel(dictType, dictValue, separator).getData();
    }

    @Override
    public String getDictValue(String dictType, String dictLabel, String separator) {
        return dictFeignClient.getDictValue(dictType, dictLabel, separator).getData();
    }

    @Override
    public Map<String, String> getAllDictByDictType(String dictType) {
        return dictFeignClient.getAllDictByDictType(dictType).getData();
    }

    @Override
    public DictTypeDTO getDictType(String dictType) {
        return dictFeignClient.getDictType(dictType).getData();
    }

    @Override
    public List<DictDataDTO> getDictData(String dictType) {
        return dictFeignClient.getDictData(dictType).getData();
    }
}
