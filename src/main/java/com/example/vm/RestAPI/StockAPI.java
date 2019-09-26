package com.example.vm.RestAPI;

import com.example.vm.bean.StockBeanI;

import com.example.vm.model.Stock;
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
public class StockAPI {
    @EJB
    private StockBeanI stockBeanI;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStock(String json) {
        Gson gson = new Gson();
        Stock stock = gson.fromJson(json, Stock.class);
        return Response.ok().entity(stockBeanI.add(stock)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/get-stock/{stockId}")
    public Response getStock(@PathParam("stockId") long productId) {
        Stock stock = new Stock();
        stock.setId(productId);
        stock = stockBeanI.read(stock);
        return Response.ok().entity(stock).build();

    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStock(String json) throws SQLException {
        List<Stock> listStock = stockBeanI.stockList();
        return Response.ok().entity(listStock).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/update-stock")
    public Response updateStock(String json) {
        Gson gson = new Gson();
        Stock stock = gson.fromJson(json, Stock.class);
        stock = stockBeanI.edit(stock);
        return Response.ok().entity(stock).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/delete-stock")
    public Response deleteStock(String json) {
        Gson gson = new Gson();
        Stock stock = gson.fromJson(json, Stock.class);
        return Response.ok().entity(stockBeanI.delete(stock)).build();
    }
}
