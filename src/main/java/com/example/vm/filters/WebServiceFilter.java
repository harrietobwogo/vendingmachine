package com.example.vm.filters;

import com.example.vm.filters.qualifiers.Authenticate;

import javax.servlet.ServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Created by Harriet on 9/25/2019.
 */
@Authenticate
//@Provider
public class WebServiceFilter implements ContainerRequestFilter {
//    @Context
//    ResourceInfo resourceInfo;
//    @Context
//    ServletRequest servletRequest;
    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        System.out.println("Web service filter");
//        System.out.println(resourceInfo.getResourceMethod().getName());
//        throw new NotAuthorizedException("you are not authenticated");
//        //containerRequestContext.abortWith();

    }
}
