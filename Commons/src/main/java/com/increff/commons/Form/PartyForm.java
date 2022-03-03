package com.increff.commons.Form;


import com.increff.commons.Constants.Party;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PartyForm {


    @NotNull
    private String partyName;

    @NotNull
    private Party.PartyType partyType;

}
