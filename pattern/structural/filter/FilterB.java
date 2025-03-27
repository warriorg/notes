package me.warriorg.design.structural.filter;

public class FilterB implements Filter {

    @Override
    public void doFilter(Request request, FilterChain filterChain) {
        System.out.println("FilterB is processing the request");
        filterChain.doFilter(request);
    }
}
