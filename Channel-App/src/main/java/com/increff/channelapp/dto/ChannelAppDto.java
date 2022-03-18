package com.increff.channelapp.dto;
//
//import com.increff.channelapp.spring.RestTemplateUrls;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.OrderWithChannelSkuIdForm;
import com.increff.commons.Form.OrderWithClientSkuIdForm;
import com.increff.commons.Form.PartyForm;
import com.increff.commons.Util.RestTemplateUtil.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import static com.increff.commons.Util.RestTemplateUtil.postRequest;

@Service
public class ChannelAppDto {


    public void addOrder(OrderWithClientSkuIdForm form) throws ApiException {
            postRequest("http://localhost:9090/Assure/api/orders/upload", form);

    }



}
