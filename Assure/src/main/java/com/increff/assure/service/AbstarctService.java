package com.increff.assure.service;

import com.increff.commons.Exception.ApiException;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class AbstarctService {



    public void isNotNull(Object obj, String memo) throws ApiException {
        if(Objects.nonNull(obj)){
            throw new ApiException(memo);
        }
    }

    public void isNull(Object obj, String memo) throws ApiException {
        if(Objects.isNull(obj)){
            throw new ApiException(memo);
        }
    }

}
