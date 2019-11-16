package com.company.test;

import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

/**
 * Java8���ԣ���������ת����Lambda
 */
public class MainTest01 {

    /**
     * ԭʼ��������
     */
    @Test
    public void test1(){
        Comparator<String> com = new Comparator<String>(){
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        };

        TreeSet<String> ts = new TreeSet<>(com);

        TreeSet<String> ts2 = new TreeSet<>(new Comparator<String>(){
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o1.length(), o2.length());
            }
        });
    }

    /**
     * Java8�е�Lambda���ʽ
     */
    @Test
    public void test2(){
        Comparator<String> com = (x, y) -> Integer.compare(x.length(), y.length());
        TreeSet<String> ts = new TreeSet<>(com);
    }

    /**
     �����������
     */
    List<Employee> emps = Arrays.asList(
            new Employee(101, "����", 18, 9999.99),
            new Employee(102, "����", 59, 6666.66),
            new Employee(103, "����", 28, 3333.33),
            new Employee(104, "����", 8, 7777.77),
            new Employee(105, "����", 38, 5555.55)
    );

    /**
     * ����1����ȡ����С��30�����Ա��Ϣ
     * ��ͳд��
     */
    public List<Employee> filterEmployeeByAge(List<Employee> epms){
           List<Employee> list = new ArrayList<Employee>();

        for (Employee epm : epms) {
            if(epm.getAge()<30){
                list.add(epm);
            }
        }
        return list;
    }

    @Test
    public void test3(){
        List<Employee> employees = filterEmployeeByAge(emps);
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    /**
     * ����2����ȡ���ʴ���5000����Ա��Ϣ
     * ��ͳд��
     */
    public List<Employee> filterEmployeeBySalary(List<Employee> employes){
        List<Employee> list = new ArrayList<Employee>();
        for (Employee employe : employes) {
            if(employe.getSalary()>5000){
                list.add(employe);
            }
        }
        return list;
    }

    @Test
    public void test4(){
        List<Employee> employees = filterEmployeeBySalary(emps);
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    /**
     * �Ż���������ʽ1��
     *  �������ģʽ���������ģʽ
     *  �˷�����ȱ����ÿ�����˶�Ҫ����һ����ȥʵ�ֽӿ�
     */
    public List<Employee> filterEmployee(List<Employee> employees ,MyPredicate<Employee> myPredicate){
        List<Employee> list = new ArrayList<Employee>();
        for (Employee employee : employees) {
            if(myPredicate.filterInfo(employee)){
                list.add(employee);
            }
        }
        return list;
    }

    @Test
    public void test5(){
        List<Employee> employees = filterEmployee(emps, new FiltereEmployeeAge());
        for (Employee employee : employees) {
            System.out.println(employee);
        }

        System.out.println("================================================");

        List<Employee> employees1 = filterEmployee(emps,new FiltereEmployeeSalary());
        for (Employee employee : employees1) {
            System.out.println(employee);
        }
    }

    /**
     * �����Ż�2�������ڲ���
     * ����д�ӿ�����Ĺ��˷���
     */
    @Test
    public void test6(){
        List<Employee> employees = filterEmployee(emps, new MyPredicate<Employee>() {
            @Override
            public boolean filterInfo(Employee employee) {
                return employee.getAge()<20;
            }
        });
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    /**
     * �����Ż�4������Java8 �����ԣ�Lambda���ʽ
     */
    @Test
    public void test7(){
        List<Employee> employees = filterEmployee(emps, (e) -> e.getAge() > 30);
        employees.forEach(System.out::println);

        System.out.println("======================================");

        List<Employee> employees1 = filterEmployee(emps,(e)->e.getSalary()>5000);
        employees1.forEach(System.out::println);
    }

    /**
     * �����Ż�5���ɹ�Java8�����ԣ�Stream API
     */
    @Test
    public void test8(){
        emps.stream()
            .filter(e->e.getAge()>30)
            .forEach(System.out::println);

        System.out.println("======================================");

        //��ȡǰ���һ������
        emps.stream().filter(e->e.getAge()>30).limit(1).forEach(System.out::println);
    }


    @Test
    public void test22(){
        String[] str = new String[]{"a","b","c","a","d"};
        Stream<String> stream = Arrays.stream(str);
        //�м����  ȥ��
        stream.distinct().forEach(System.out::println);

        String[] str1 = new String[]{"a","b","c","a","d"};
        Stream<String> stream1 = Arrays.stream(str);
        //�м���� ���ɴ�д
        stream1.map((e) -> e.toUpperCase()).forEach(System.out::println);
    }




}
