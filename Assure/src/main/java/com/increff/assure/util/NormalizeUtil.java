package com.increff.assure.util;

import com.increff.assure.pojo.*;
import com.increff.commons.Exception.ApiException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NormalizeUtil {

    public static void normalize(PartyPojo partyPojo){
        partyPojo.setPartyName(partyPojo.getPartyName().trim().toLowerCase());
    }

    public static void normalize(ProductPojo productPojo){
        productPojo.setProductName(productPojo.getProductName().trim().toLowerCase());
        productPojo.setDescription(productPojo.getDescription().trim().toLowerCase());
    }

    public static void normalize(List<ProductPojo> pojoList){
        for(ProductPojo productPojo: pojoList){
            normalize(productPojo);
        }
    }

    public static void normalize(ChannelPojo channelPojo){
        channelPojo.setChannelName(channelPojo.getChannelName().trim().toLowerCase());
    }

    public static void normalize(ChannelListingPojo channelListingPojo){

    }

    public static void normalize(OrderPojo orderPojo){

    }




}
