package com.hsebank.importer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class CsvImporter implements Importer {

    @Override
    public List<Map<String, Object>> importAsListOfMaps(Path path) throws Exception {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        if (lines.isEmpty()) return Collections.emptyList();
        String header = lines.get(0);
        String[] cols = header.split(",");
        Map<String,Integer> idx = new HashMap<>();
        for (int i = 0; i < cols.length; i++) {
            String col = cols[i].trim().toLowerCase();
            if (!col.isEmpty() && col.charAt(0) == '\uFEFF') col = col.substring(1);
            idx.put(col, i);
        }
        List<Map<String,Object>> res = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line == null) continue;
            if (line.trim().isEmpty()) continue;
            String[] parts = line.split(",", -1);
            Map<String,Object> m = new HashMap<>();
            if (idx.containsKey("id")) m.put("id", safeGet(parts, idx.get("id")));
            if (idx.containsKey("name")) m.put("name", safeGet(parts, idx.get("name")));
            if (idx.containsKey("balance")) m.put("balance", safeGet(parts, idx.get("balance")));
            if (idx.containsKey("type")) m.put("type", safeGet(parts, idx.get("type")));
            if (idx.containsKey("bankaccountid")) m.put("accountId", safeGet(parts, idx.get("bankaccountid")));
            else if (idx.containsKey("accountid")) m.put("accountId", safeGet(parts, idx.get("accountid")));
            if (idx.containsKey("categoryid")) m.put("categoryId", safeGet(parts, idx.get("categoryid")));
            if (idx.containsKey("amount")) m.put("amount", safeGet(parts, idx.get("amount")));
            if (idx.containsKey("date")) m.put("date", safeGet(parts, idx.get("date")));
            if (idx.containsKey("description")) m.put("description", safeGet(parts, idx.get("description")));
            if (!m.isEmpty()) res.add(m);
        }
        System.out.println("Imported CSV maps: " + res);
        return res;
    }

    private static String safeGet(String[] arr, Integer i) {
        if (i == null) return null;
        if (i >= arr.length) return "";
        return arr[i].trim();
    }
}
