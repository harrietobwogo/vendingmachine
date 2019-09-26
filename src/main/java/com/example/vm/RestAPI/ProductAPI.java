package com.example.vm.RestAPI;

import com.example.vm.bean.ProductBeanI;
import com.example.vm.filters.qualifiers.Authenticate;
import com.example.vm.model.Product;
import com.google.gson.Gson;


import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Harriet on 9/25/2019.
 */
@Authenticate
@Path("/products")
public class ProductAPI {
    @EJB
    private ProductBeanI productBeanI;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createProduct(String json) {
        Gson gson = new Gson();
        Product product = gson.fromJson(json, Product.class);
        return Response.ok().entity(productBeanI.add(product)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get-product/{productId}")
    public Response getProduct(@PathParam("productId") int productId) {
        Product product = new Product();
        product.setId(productId);
        product = productBeanI.read(product);
        return Response.ok().entity(product).build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/list-products")
    public Response readProductList() throws SQLException {
        List<Product> products = productBeanI.getProductList();
        return Response.ok().entity(products).build();

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update-product")
    public Response updateProduct(String json) {
        Gson gson=new Gson();
        Product product = gson.fromJson(json,Product.class);
        product = productBeanI.edit(product);
        return Response.ok().entity(product).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/delete")
    public Response deleteProduct(String json) {
        Gson gson = new Gson();
        Product product = gson.fromJson(json, Product.class);
        return Response.ok().entity(productBeanI.delete(product)).build();
    }


}

