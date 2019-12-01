package com.learn.unittestdemo.util;


import com.learn.unittestdemo.models.NoteTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

import static com.learn.unittestdemo.util.DateUtil.GET_MONTH_ERROR;
import static com.learn.unittestdemo.util.DateUtil.monthNumbers;
import static com.learn.unittestdemo.util.DateUtil.months;


public class DateUtilTest {

    public static final String date = "11-2019";

    @Test
    public void testGetCurrentTimeStamp_getReturnedTimestamp(){
        Assertions.assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                Assertions.assertEquals(date,DateUtil.getCurrentTimeStamp());
                System.out.println("Timestamp generated successfully");
            }
        });
    }


    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10,11})
    public void getMonthFromNumbers_returnSuccess(int month){
        Assertions.assertEquals(months[month],DateUtil.getMonthFromNumber(monthNumbers[month]));
        System.out.println(months[month]+" : "+monthNumbers[month]);
    }


    @ParameterizedTest
    @ValueSource(ints = {0,1,2,3,4,5,6,7,8,9,10,11})
    public void testGetMonthFromNumbers_returnSuccess(int month){
        int random = new Random().nextInt(90) + 13;
        Assertions.assertEquals(DateUtil.getMonthFromNumber(String.valueOf(month * random)),GET_MONTH_ERROR);
        System.out.println(months[month]+" : " + GET_MONTH_ERROR );
    }
}
