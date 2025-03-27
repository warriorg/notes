package me.warriorg.design.structural.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterChainImpl implements FilterChain {
    private List<Filter> filters = new ArrayList<>();
    private int currentFilterIndex = 0;

    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    @Override
    public void doFilter(Request request) {
        if (currentFilterIndex < filters.size()) {
            Filter currentFilter = filters.get(currentFilterIndex);
            currentFilterIndex++;
            currentFilter.doFilter(request, this);
        }
    }
}
