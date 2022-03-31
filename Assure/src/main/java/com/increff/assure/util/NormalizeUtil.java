package com.increff.assure.util;

import com.increff.assure.pojo.*;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class NormalizeUtil {

    public static void normalize(PartyPojo partyPojo){
        partyPojo.setPartyName(partyPojo.getPartyName().trim().toLowerCase());
    }

    public static void normalize(ProductForm form){
        form.setProductName(form.getProductName().trim().toLowerCase());
        form.setDescription(form.getDescription().trim().toLowerCase());
        form.setClientSkuId(form.getClientSkuId().trim().toLowerCase());
    }

    public static void normalize(UpdateProductForm form){
        form.setProductName(form.getProductName().trim().toLowerCase());
        form.setDescription(form.getDescription().trim().toLowerCase());
    }

    public static void normalize(List<ProductForm> formList){
        for(ProductForm form: formList){
            normalize(form);
        }
    }

    public static void normalize(BinSkuForm form){
        form.setClientSkuId(form.getClientSkuId().trim().toLowerCase());
    }

    public static void normalizeUploadBinSkuForm(List<BinSkuForm> formList){
        for(BinSkuForm form: formList){
            normalize(form);
        }
    }

    public static void normalize(OrderItemWithClientSkuId form){
        form.setClientSkuId(form.getClientSkuId().trim().toLowerCase());
    }

    public static void normalizeOrderItemWithClientSkuIdForm(List<OrderItemWithClientSkuId> formList){
        for(OrderItemWithClientSkuId form: formList){
            normalize(form);
        }
    }

    public static void normalize(ChannelListingForm form){
        form.setClientSkuId(form.getClientSkuId().trim().toLowerCase());
    }

    public static void normalizeChannelListingForm(List<ChannelListingForm> formList){
        for(ChannelListingForm form: formList){
            normalize(form);
        }
    }

    public static List<String> normalizeClientSkus(List<String> clientSkuIds){
        return clientSkuIds.stream().map(x->x.trim().toLowerCase()).collect(Collectors.toList());
    }

    public static void normalize(ChannelPojo channelPojo){
        channelPojo.setChannelName(channelPojo.getChannelName().trim().toLowerCase());
    }

    public static void normalize(ChannelListingPojo channelListingPojo){

    }

    public static void normalize(OrderPojo orderPojo){

    }




}
