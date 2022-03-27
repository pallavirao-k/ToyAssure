package com.increff.assure.controller;

import com.increff.assure.dto.PartyDto;
import com.increff.commons.Constants.Party.PartyType;
import com.increff.commons.Data.PartyData;
import com.increff.commons.Form.PartyForm;
import com.increff.commons.Exception.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api
@RestController()
@RequestMapping("/api/parties")
public class PartyController {

    @Autowired
    private PartyDto dto;

    @ApiOperation(value = "Adds a party")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestBody PartyForm form) throws ApiException{
        dto.add(form);
    }



    @ApiOperation(value = "Gets a single party by ID")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public PartyData get(@PathVariable Long id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "Gets list of all parties")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<PartyData> getAll() throws ApiException {
        return dto.getAll();
    }

    @ApiOperation(value = "Gets list of all parties of same type")
    @RequestMapping(path = "/type", method = RequestMethod.GET)
    public List<PartyData> getPartyByType(@RequestParam PartyType type) throws ApiException {
        return dto.getPartyByType(type);
    }


}
