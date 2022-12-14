package com.apu.order.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Utils {
    //Which is better Log4j or Log4j2?
    //Log4j, Logback, and Log4j2 are good logging frameworks that are broadly used. So which one should you use?
    //I recommend using Log4j2 because it's the fastest and most advanced of the three frameworks.
    //Logback is still a good option, if performance is not your highest priority
    private static final Logger logger= LoggerFactory.getLogger(Utils.class);

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <Source, Dest> void copyProperty(Source source, Dest target) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.map(source, target);
    }

    public static Boolean isNullOrEmpty(String str){
        if(str == null || str.length()==0)return true;
        return false;
    }


    public static <U, V> List<V> toDtoList(Iterable<U> mapperObjects, Class<V> targetClass) {
        List<V> dtoObjects = new ArrayList<>();

        mapperObjects.forEach(object -> {
            dtoObjects.add(convertClass(object, targetClass));
        });

        return dtoObjects;
    }

    public static <U, V> V convertClass(U mapperObject, Class<V> targetClass) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper.map(mapperObject, targetClass);
    }
    public static List<LocalDate> getCurrentFinancialYear(){
        List<LocalDate> financialYear = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate currFinancialYearStart = null;
        LocalDate currFinancialYearEnd = null;

        if (currentDate.getMonth().getValue() <= 6) {
            currFinancialYearStart = LocalDate.of(currentDate.getYear() - 1, 7, 1);
            currFinancialYearEnd = LocalDate.of(currentDate.getYear(), 6, 30);
        } else {
            currFinancialYearStart = LocalDate.of(currentDate.getYear(), 7, 1);
            currFinancialYearEnd = LocalDate.of(currentDate.getYear() + 1, 6, 30);
        }

        financialYear.add(currFinancialYearStart);
        financialYear.add(currFinancialYearEnd);
        return financialYear;
    }
    public static Integer getRemainingMonthForTheCurrentFinancialYear(){

        LocalDate currentDate = LocalDate.now();
        Integer remainingNumberOfMonth = 0;

        if (currentDate.getMonth().getValue() <= 6) {
            remainingNumberOfMonth = 6 - currentDate.getMonth().getValue() + 1;
        } else {
            remainingNumberOfMonth = 12 - currentDate.getMonth().getValue() + 1;
        }
        return remainingNumberOfMonth;
    }

    public static String jsonAsString(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
