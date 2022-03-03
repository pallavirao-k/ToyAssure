package com.increff.assure.dto;
import com.increff.assure.pojo.ChannelPojo;
import com.increff.assure.service.ChannelService;
import com.increff.commons.Constants.Invoice.*;
import com.increff.commons.Data.ChannelData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.ChannelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

import static com.increff.assure.util.NormalizeUtil.normalize;
import static com.increff.commons.Util.ConvertUtil.convert;


@Service
public class ChannelDto extends AbstractDto {
    @Autowired
    private ChannelService service;

    public void add(ChannelForm form) throws ApiException {
        validateChannelForm(form);
        normalize(form);
        service.add(convert(form, ChannelPojo.class));
    }

    public ChannelData get(Long id) throws ApiException {
        ChannelPojo cp = service.get(id);
        return convert(cp, ChannelData.class);
    }

    public List<ChannelData> getAll(){
        List<ChannelPojo> list = service.getAll();
        return list.stream().map(x->convert(x,ChannelData.class)).collect(Collectors.toList());
    }

    public List<ChannelData> getChannelsByInvoiceType(InvoiceType invoiceType){
        List<ChannelPojo> list = service.getChannelsByInvoiceType(invoiceType);
        return list.stream().map(x->convert(x,ChannelData.class)).collect(Collectors.toList());
    }

}
