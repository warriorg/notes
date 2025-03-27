package me.warriorg.design.structural.filter;

public class FilterA implements Filter {


    @Override
    public void doFilter(Request request, FilterChain filterChain) {
        System.out.println("FilterA is processing the request");
        // if some condition evaluates to true, then call the next
        // filter in the chain
        filterChain.doFilter(request);
        // we can also perform some post-filter logic after all the
        // downstream call
        System.out.println("FilterA is done processing the request");
    }
}
