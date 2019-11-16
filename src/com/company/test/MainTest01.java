package com.company.test;

import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

/**
 * Java8特性，从匿名类转换到Lambda
 */
public class MainTest01 {

    /**
     * 原始的匿名类
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
     * Java8中的Lambda表达式
     */
    @Test
    public void test2(){
        Comparator<String> com = (x, y) -> Integer.compare(x.length(), y.length());
        TreeSet<String> ts = new TreeSet<>(com);
    }

    /**
     定义测试数据
     */
    List<Employee> emps = Arrays.asList(
            new Employee(101, "张三", 18, 9999.99),
            new Employee(102, "李四", 59, 6666.66),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );

    /**
     * 需求1：获取年龄小于30岁的人员信息
     * 传统写法
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
     * 需求2：获取工资大于5000的人员信息
     * 传统写法
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
     * 优化上面需求方式1：
     *  采用设计模式：策略设计模式
     *  此方法的缺陷是每个过滤都要建立一个类去实现接口
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
     * 需求优化2：匿名内部类
     * 即重写接口里面的过滤方法
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
     * 需求优化4：采用Java8 新特性，Lambda表达式
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
     * 需求优化5：采购Java8新特性，Stream API
     */
    @Test
    public void test8(){
        emps.stream()
            .filter(e->e.getAge()>30)
            .forEach(System.out::println);

        System.out.println("======================================");

        //获取前面的一条数据
        emps.stream().filter(e->e.getAge()>30).limit(1).forEach(System.out::println);
    }


    @Test
    public void test22(){
        String[] str = new String[]{"a","b","c","a","d"};
        Stream<String> stream = Arrays.stream(str);
        //中间操作  去重
        stream.distinct().forEach(System.out::println);

        String[] str1 = new String[]{"a","b","c","a","d"};
        Stream<String> stream1 = Arrays.stream(str);
        //中间操作 换成大写
        stream1.map((e) -> e.toUpperCase()).forEach(System.out::println);
    }




}
