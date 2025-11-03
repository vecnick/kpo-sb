package com.hsebank.importer;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface Importer {
    List<Map<String, Object>> importAsListOfMaps(Path path) throws Exception;
}
