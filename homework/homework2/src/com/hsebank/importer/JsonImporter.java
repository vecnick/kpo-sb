package com.hsebank.importer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JsonImporter implements Importer {

    @Override
    public List<Map<String, Object>> importAsListOfMaps(Path path) throws Exception {
        String raw = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        return parseArrayOfObjects(raw);
    }

    private static List<Map<String,Object>> parseArrayOfObjects(String raw) {
        List<Map<String,Object>> res = new ArrayList<>();
        if (raw == null) return res;
        String s = raw.trim();
        if (!s.startsWith("[")) return res;
        s = s.substring(1, s.length() - 1).trim();
        List<String> objs = splitTopLevelObjects(s);
        for (String o : objs) {
            res.add(parseObject(o));
        }
        return res;
    }

    private static List<String> splitTopLevelObjects(String s) {
        List<String> res = new ArrayList<>();
        int depth = 0;
        int start = -1;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '{') {
                if (depth == 0) start = i;
                depth++;
            } else if (ch == '}') {
                depth--;
                if (depth == 0 && start >= 0) {
                    res.add(s.substring(start, i + 1));
                    start = -1;
                }
            }
        }
        return res;
    }

    private static Map<String,Object> parseObject(String o) {
        Map<String,Object> map = new HashMap<>();
        Pattern p = Pattern.compile("\"([^\"]+)\"\\s*:\\s*(\"([^\"]*)\"|[^,}\\n]+)");
        Matcher m = p.matcher(o);
        while (m.find()) {
            String key = m.group(1);
            String val;
            if (m.group(3) != null) val = m.group(3);
            else val = m.group(2).trim();
            map.put(key, val);
        }
        return map;
    }
}
