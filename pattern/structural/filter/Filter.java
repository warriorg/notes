package me.warriorg.design.structural.filter;

public interface Filter {

    void doFilter(Request request, FilterChain filterChain);
}
