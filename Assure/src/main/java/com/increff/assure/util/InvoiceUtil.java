package com.increff.assure.util;

import com.increff.assure.pojo.OrderItemPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.commons.Data.InvoiceData;
import com.increff.commons.Data.InvoiceProductData;
import com.increff.commons.Util.XmlUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.increff.commons.Constants.ConstantNames.*;
import static com.increff.commons.Util.ConvertUtil.convert;

public class InvoiceUtil {

    public static InvoiceData convertToInvoiceData(List<OrderItemPojo> orderItemPojos,
                                                   Map<Long, ProductPojo> globalSkuToProduct) throws Exception {
        List<InvoiceProductData> invoiceProductDataList = new ArrayList<>();

        Double total = 0D;
        for(OrderItemPojo oip: orderItemPojos){
            ProductPojo p = globalSkuToProduct.get(oip.getGlobalSkuId());
            InvoiceProductData ipd = convert(p, InvoiceProductData.class);
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

    public static byte[] generatePdf(InvoiceData invoiceData) throws Exception {
        XmlUtil.generateXml(new File(INVOICE_XML_PATH),
                invoiceData, InvoiceData.class);
        return XmlUtil.generatePDF(invoiceData.getOrderId(), new File(INVOICE_XML_PATH),
                new StreamSource(INVOICE_XSL_PATH));
    }

    public static void getInvoice(Long orderId) throws IOException {
        String path = PDF_BASE_ADDRESS+orderId+"pdf";
        Path pdfPath = Paths.get(path);
        byte[] bytes = Files.readAllBytes(pdfPath);
        createPdfResponse(bytes);
    }


    public static void createPdfResponse(byte[] bytes) throws IOException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
        response.setContentType("application/pdf");
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
        response.getOutputStream().flush();
    }
}
