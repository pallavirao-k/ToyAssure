package com.increff.commons.Form;

import com.increff.commons.Constants.Invoice.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelForm {

    private String channelName ;
    private InvoiceType  invoiceType;
}
