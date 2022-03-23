package com.increff.assure.util;

import com.increff.assure.pojo.InvoicePojo;
import com.increff.assure.pojo.OrderItemPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.commons.Data.InvoiceData;
import com.increff.commons.Data.InvoiceProductData;
import com.increff.commons.Data.InvoiceResponse;
import com.increff.commons.Util.ConvertUtil;
import com.increff.commons.Util.XmlUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.stream.StreamSource;
import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.increff.commons.Constants.ConstantNames.*;
import static com.increff.commons.Util.ConvertUtil.convert;
import static com.increff.commons.Util.RestTemplateUtil.postRequest;

public class InvoiceUtil {

    public static InvoiceData convertToInvoiceData(List<OrderItemPojo> orderItemPojos,
                                                   Map<Long, ProductPojo> globalSkuToProduct) throws Exception {
        List<InvoiceProductData> invoiceProductDataList = new ArrayList<>();

        Double total = 0D;
        for(OrderItemPojo oip: orderItemPojos){
            ProductPojo p = globalSkuToProduct.get(oip.getGlobalSkuId());
            InvoiceProductData ipd = ConvertUtil.convert(p, InvoiceProductData.class);
            ipd.setQty(oip.getOrderedQty());
            ipd.setSellingPricePerUnit(oip.getSellingPricePerUnit());
            invoiceProductDataList.add(ipd);
            total+=oip.getSellingPricePerUnit()*oip.getOrderedQty();
        }

        InvoiceData invoiceData = new InvoiceData();
        invoiceData.setOrderId(orderItemPojos.get(0).getOrderId());
        invoiceData.setGeneratedDateTime(XmlUtil.getDateTime());
        invoiceData.setInvoiceProductDataList(invoiceProductDataList);
        invoiceData.setTotal(total);
        return invoiceData;
    }

    public static InvoicePojo convertToInvoicePojo(Long orderId, String invoiceUrl){
        InvoicePojo invoicePojo = new InvoicePojo();
        invoicePojo.setOrderId(orderId);
        invoicePojo.setInvoiceUrl(invoiceUrl);
        return invoicePojo;
    }

    public static InvoiceResponse convertToInvoiceResponse(InvoicePojo invoicePojo){
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        invoiceResponse.setId(invoicePojo.getId());
        invoiceResponse.setOrderId(invoicePojo.getOrderId());
        invoiceResponse.setInvoiceUrl(invoicePojo.getInvoiceUrl());
        return  invoiceResponse;
    }






//
//// return Invoice response class
////
//    public static void createPdfResponse(byte[] bytes) throws IOException {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
//        response.setContentType("application/pdf");
//        response.setContentLength(bytes.length);
//        response.getOutputStream().write(bytes);
//        response.getOutputStream().flush();
//    }
}
