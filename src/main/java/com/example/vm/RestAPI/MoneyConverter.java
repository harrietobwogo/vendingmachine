package com.example.vm.RestAPI;

import com.example.vm.bean.MoneyConverterBeanI;
import com.example.vm.model.Denomination;
import com.google.gson.Gson;


import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harriet on 9/19/2019.
 */
@Path("/mc")
public class MoneyConverter {
    @EJB
    private MoneyConverterBeanI moneyConverterBeanI;

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    @Path("/get-denominations/{amount}")
    public Response getDenominationsForMoney(@PathParam("amount") BigDecimal amount) {
        Map m = moneyConverterBeanI.getDenominationsForMoney(amount);
        Gson gson = new Gson();
        return Response.status(401).entity(m).build();

    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/get-money-value")
    public Response getMoneyValue(String jsonObject) {
        Gson gson = new Gson();
        Map<String, Integer> m = gson.fromJson(jsonObject.toString(), Map.class);
        Map<Denomination, Integer> map = new HashMap<>();
        for (Map.Entry m_ : m.entrySet()) {
            System.out.println("Value is:" + m_.getValue());
            double value = (double) m_.getValue();
            map.put(Denomination.valueOf(m_.getKey().toString()), (int) value);


        }
        BigDecimal amount = moneyConverterBeanI.getMoneyValueFromDenominations(map);
        return Response.ok().entity(amount).build();
    }
}
