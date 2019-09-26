package com.example.vm.RestAPI;

import com.example.vm.bean.SaleBeanI;
import com.example.vm.model.Sale;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Harriet on 9/26/2019.
 */
@Path("/sales")
public class SaleAPI {
    @EJB
    private SaleBeanI saleBeanI;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get-sale/{saleId}")
    public Response getSale(@PathParam("saleId") long productId) {
        Sale sale = new Sale();
        sale.setId(productId);
        sale = saleBeanI.read(sale);
        return Response.ok().entity(sale).build();

    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response readSales(String json) throws SQLException {
        List<Sale> saleList = saleBeanI.saleList();
        return Response.ok().entity(saleList).build();

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update-sale")
    public Response updateSale(String json) {
        Gson gson = new Gson();
        Sale sale = gson.fromJson(json, Sale.class);
        sale = saleBeanI.edit(sale);
        return Response.ok().entity(sale).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/delete-sale")
    public Response deleteSale(String json) {
        Gson gson = new Gson();
        Sale sale = gson.fromJson(json, Sale.class);
        return Response.ok().entity(saleBeanI.delete(sale)).build();
    }
}
