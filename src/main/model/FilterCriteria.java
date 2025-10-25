package main.model;

import java.util.HashMap;
import java.util.Map;

public class FilterCriteria {
    private final Map<String, Object> filters = new HashMap<>();

    public void addFilter(String column, Object value) {
        filters.put(column, value);
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public boolean isEmpty() {
        return filters.isEmpty();
    }
}