package com.increff.assure.dto;
//
//import com.increff.channelapp.spring.RestTemplateUrls;
import com.increff.assure.spring.ApplicationProperties;
import com.increff.commons.Data.InvoiceData;
import com.increff.commons.Form.OrderWithChannelSkuIdForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.increff.commons.Util.XmlUtil.generatePdf;


@Service
public class ChannelAppDto {


    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private ApplicationProperties properties;

    public void placeOrder(OrderWithChannelSkuIdForm form) {
        restTemplate.postForObject(properties.getServerUri()+"orders", form, OrderWithChannelSkuIdForm.class);//TODO make an another class for url....
    }

    public String generateInvoice(InvoiceData invoiceData) throws Exception {
        return generatePdf(invoiceData);

    }



}
