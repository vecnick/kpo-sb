package com.hsebank.importer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class YamlImporter implements Importer {

    @Override
    public List<Map<String, Object>> importAsListOfMaps(Path path) throws Exception {
        String raw = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        return parseListOfMaps(raw);
    }

    private static List<Map<String,Object>> parseListOfMaps(String text) {
        if (text == null) return Collections.emptyList();
        List<Map<String,Object>> res = new ArrayList<>();
        String[] lines = text.split("\\r?\\n");
        Map<String,Object> current = null;
        for (String raw : lines) {
            String line = raw.replaceAll("\\t", "    ");
            if (line.trim().isEmpty()) continue;
            if (line.trim().startsWith("- ")) {
                if (current != null) res.add(current);
                current = new LinkedHashMap<>();
                String rest = line.trim().substring(2).trim();
                if (!rest.isEmpty()) {
                    int idx = rest.indexOf(':');
                    if (idx > 0) {
                        String k = rest.substring(0, idx).trim();
                        String v = rest.substring(idx + 1).trim();
                        if (v.startsWith("\"") && v.endsWith("\"") && v.length() >= 2) v = v.substring(1, v.length()-1);
                        current.put(k, v);
                    }
                }
            } else {
                if (current == null) continue;
                String trimmed = line.trim();
                int idx = trimmed.indexOf(':');
                if (idx <= 0) continue;
                String k = trimmed.substring(0, idx).trim();
                String v = trimmed.substring(idx + 1).trim();
                if (v.startsWith("\"") && v.endsWith("\"") && v.length() >= 2) v = v.substring(1, v.length()-1);
                current.put(k, v);
            }
        }
        if (current != null) res.add(current);
        return res;
    }
}
