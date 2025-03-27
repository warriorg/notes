package me.warriorg.design.structural.filter;

public class FilterChainExample {
    public static void main(String[] args) {
        // Create a filter chain and add filters dynamically.
        FilterChainImpl filterChain = new FilterChainImpl();

        // Add filters to the chain.
        filterChain.addFilter(new FilterA());
        filterChain.addFilter(new FilterB());

        // Create a request and process it through the filter chain.
        Request request = new Request("Sample Data");
        filterChain.doFilter(request);
    }

}
