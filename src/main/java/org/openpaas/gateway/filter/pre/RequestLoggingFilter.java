package org.openpaas.gateway.filter.pre;

import java.util.Optional;

import org.openpaas.gateway.filter.FilterType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openpaas.gateway.filter.FilterOrder.*;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

// Zuul Pre-route Filter (Logging)
public class RequestLoggingFilter extends ZuulFilter {
    private Logger logger = LoggerFactory.getLogger( getClass() );
    
    @Override
    public boolean shouldFilter() {
        return true;
    }
    
    @Override
    public int filterOrder() {
        return PreRouteOrder.RequestLoggingFilter;
    }

    @Override
    public String filterType() {
        return FilterType.PRE;
    }
    
    @Override
    public Object run() {
        Optional<RequestContext> ctx = Optional.ofNullable(RequestContext.getCurrentContext());
        
        // Legacy
        //logger.info( "Request URI : {}", ctx.get().getRequest().getRequestURI() );
        //logger.info( "Request URL : {}", ctx.get().getRequest().getRequestURL() );
        
        // After Java 8
        ctx.ifPresent(c -> {
            logger.info("Request URI : {}", c.getRequest().getRequestURI());
            logger.info("Request URL : {}", c.getRequest().getRequestURL());
        });
        
        return null;
    }
}
