package com.company.test;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * 1.LocalDate��LocalTime��LocalDateTime
 * 2.Duration : ���ڼ���������ʱ�䡱���
 *   Period : ���ڼ������������ڡ����
 * 3.TemporalAdjuster : ʱ��У����
 * 4.DateTimeFormatter : �����͸�ʽ�����ڻ�ʱ��
 */
public class LocalDateTimeTest {

    /**
     * LocalDate��LocalTime��LocalDateTime
     */
    @Test
    public void test1(){
        //��ȡ��ǰʱ��
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        //���
        System.out.println(ldt.getYear());
        //�·�
        System.out.println(ldt.getMonthValue());
        //��
        System.out.println(ldt.getDayOfMonth());
        //Сʱ
        System.out.println(ldt.getHour());
        //����
        System.out.println(ldt.getMinute());
        //��
        System.out.println(ldt.getSecond());
        //�Զ���������ʱ����
        LocalDateTime ld2 = LocalDateTime.of(2020, 5, 20, 13, 14, 00);
        System.out.println(ld2);
        //��20��
        LocalDateTime ldt3 = ld2.plusYears(20);
        System.out.println(ldt3);
        //��������
        LocalDateTime ldt4 = ld2.minusMonths(2);
        System.out.println(ldt4);
    }

    /**
     * Duration : ���ڼ���������ʱ�䡱���
     * Period : ���ڼ������������ڡ����
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
        //ʱ���
        System.out.println("���ķ�ʱ��Ϊ��" + Duration.between(ins1, ins2));

        LocalDate ld1 = LocalDate.now();
        LocalDate ld2 = LocalDate.of(2016, 1, 1);

        Period pe = Period.between(ld2, ld1);
        System.out.println(pe.getYears());//ʱ��������
        System.out.println(pe.getMonths());//ʱ��������
        System.out.println(pe.getDays());//ʱ��������
    }

    /**
     * TemporalAdjuster : ʱ��У����
     */
    @Test
    public void test3(){
        //������ʱ��
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);
        //��λ��10��
        LocalDateTime ldt2 = ldt.withDayOfMonth(10);
        System.out.println(ldt2);
        //�������ڵ�����
        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt3);
        //�Զ��壺��һ��������
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
     * DateTimeFormatter : �����͸�ʽ�����ڻ�ʱ��
     */
    @Test
    public void test5(){
        //ʱ���ʽ��
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy��MM��dd�� HH:mm:ss E");
        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(dtf);
        System.out.println(strDate);
        LocalDateTime newLdt = ldt.parse(strDate, dtf);
        System.out.println(newLdt);
    }
}
