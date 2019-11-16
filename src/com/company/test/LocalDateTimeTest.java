package com.company.test;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * 1.LocalDate、LocalTime、LocalDateTime
 * 2.Duration : 用于计算两个“时间”间隔
 *   Period : 用于计算两个“日期”间隔
 * 3.TemporalAdjuster : 时间校正器
 * 4.DateTimeFormatter : 解析和格式化日期或时间
 */
public class LocalDateTimeTest {

    /**
     * LocalDate、LocalTime、LocalDateTime
     */
    @Test
    public void test1(){
        //获取当前时间
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        //年份
        System.out.println(ldt.getYear());
        //月份
        System.out.println(ldt.getMonthValue());
        //日
        System.out.println(ldt.getDayOfMonth());
        //小时
        System.out.println(ldt.getHour());
        //分钟
        System.out.println(ldt.getMinute());
        //秒
        System.out.println(ldt.getSecond());
        //自定义年月日时分秒
        LocalDateTime ld2 = LocalDateTime.of(2020, 5, 20, 13, 14, 00);
        System.out.println(ld2);
        //加20年
        LocalDateTime ldt3 = ld2.plusYears(20);
        System.out.println(ldt3);
        //减两个月
        LocalDateTime ldt4 = ld2.minusMonths(2);
        System.out.println(ldt4);
    }

    /**
     * Duration : 用于计算两个“时间”间隔
     * Period : 用于计算两个“日期”间隔
     */
    @Test
    public void test2(){
        Instant ins1 = Instant.now();

        System.out.println("--------------------");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        Instant ins2 = Instant.now();
        //时间差
        System.out.println("所耗费时间为：" + Duration.between(ins1, ins2));

        LocalDate ld1 = LocalDate.now();
        LocalDate ld2 = LocalDate.of(2016, 1, 1);

        Period pe = Period.between(ld2, ld1);
        System.out.println(pe.getYears());//时间差多少年
        System.out.println(pe.getMonths());//时间差多少月
        System.out.println(pe.getDays());//时间差多少日
    }

    /**
     * TemporalAdjuster : 时间校正器
     */
    @Test
    public void test3(){
        //当期那时间
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        //定位在10日
        LocalDateTime ldt2 = ldt.withDayOfMonth(10);
        System.out.println(ldt2);
        //调整日期到周六
        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt3);
        //自定义：下一个工作日
        LocalDateTime ldt5 = ldt.with((l) -> {
            LocalDateTime ldt4 = (LocalDateTime) l;

            DayOfWeek dow = ldt4.getDayOfWeek();

            if(dow.equals(DayOfWeek.FRIDAY)){
                return ldt4.plusDays(3);
            }else if(dow.equals(DayOfWeek.SATURDAY)){
                return ldt4.plusDays(2);
            }else{
                return ldt4.plusDays(1);
            }
        });
        System.out.println(ldt5);
    }

    /**
     * DateTimeFormatter : 解析和格式化日期或时间
     */
    @Test
    public void test5(){
        //时间格式化
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss E");
        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(dtf);
        System.out.println(strDate);
        LocalDateTime newLdt = ldt.parse(strDate, dtf);
        System.out.println(newLdt);
    }
}
