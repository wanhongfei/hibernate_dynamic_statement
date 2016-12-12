package com.jd.vf.hibernate.dystatement.reader;

import com.jd.vf.hibernate.dystatement.entity.Mapper;
import com.jd.vf.hibernate.dystatement.extractor.Dom4jXmlExtractor;
import com.jd.vf.hibernate.dystatement.util.StringUtil;
import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongfei.whf on 2016/11/23.
 */
@Data
@Slf4j
public class MapperReader {

    public static final String MAPPER_FILE_SUFFIX = ".mapper.xml";
    private Map<String /*namespace*/, Mapper> mappers = new HashMap<>();
    private Dom4jXmlExtractor extractor = new Dom4jXmlExtractor();

    @SneakyThrows
    public MapperReader(List<String> mapperDirectory) {
        for (String path : mapperDirectory) {
            if (StringUtil.isEmpty(path)) throw new Exception("mapperDirectory has null!");
            if (path.startsWith("classpath:")) {
                path = path.replace("classpath:", "");
                path = MapperReader.class.getResource(path).getPath();
            }
            scan(path);
        }
    }

    @SneakyThrows
    public void scan(String path) {
        log.debug(path);
        File directory = new File(path);
        if (directory.isDirectory()) {
            List<File> files = new ArrayList<>();
            scanDictory(directory, files);
            readMapper(files);
        } else {
            throw new RuntimeException("mapperDirectory isn't Directory");
        }
    }

    public Mapper getMapper(@NonNull String namespace) {
        return this.getMappers().get(namespace);
    }

    private void scanDictory(File directory, List<File> mappers) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(MAPPER_FILE_SUFFIX)) {
                    mappers.add(file);
                } else if (file.isDirectory()) {
                    scanDictory(file, mappers);
                }
            }
        }
    }

    @SneakyThrows
    private void readMapper(List<File> mapperFiles) {
        if (mapperFiles == null) throw new RuntimeException("init error");
        for (File file : mapperFiles) {
            log.debug(file.getAbsolutePath());
            Mapper mapper = extractor.parserFile2Mapper(file);
            if (mappers.containsKey(mapper.getNamespace())) {
                mappers.get(mapper.getNamespace())
                        .addMapperMethods(mapper.getMapperMethods());
            } else {
                mappers.put(mapper.getNamespace(), mapper);
            }
        }
    }

}
