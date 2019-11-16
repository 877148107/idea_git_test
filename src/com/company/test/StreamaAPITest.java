package com.company.test;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * 一、Stream API 的操作步骤：
 *
 * 1. 创建 Stream
 *
 * 2. 中间操作
 *
 * 3. 终止操作(终端操作)
 */
public class StreamaAPITest {

    List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66, Employee.Status.BUSY),
            new Employee(101, "张三", 18, 9999.99, Employee.Status.FREE),
            new Employee(103, "王五", 28, 3333.33, Employee.Status.VOCATION),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.BUSY),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.BUSY),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.BUSY),
            new Employee(105, "田七", 38, 5555.55, Employee.Status.BUSY)
    );

    /**
     * collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
     */
    @Test
    public void test6(){
        //toList 将所有名字封转成list
        emps.stream().map(Employee::getName).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("====================================================");
        //toSet 将所有名字封转成Set
        emps.stream().map(Employee::getName).collect(Collectors.toSet()).forEach(System.out::println);
        System.out.println("====================================================");
        //joining 获取姓名用，分割
        String join = emps.stream().map(Employee::getName).collect(Collectors.joining(","));
        System.out.println(join);
        System.out.println("====================================================");
        //maxBy 获取工资最大的
        Optional<Double> collect = emps.stream().map(Employee::getSalary).collect(Collectors.maxBy(Double::compareTo));
        System.out.println(collect.get());
        System.out.println("====================================================");
        //maxBy 工资最大的员工信息
        Optional<Employee> collect1 = emps.stream().collect(Collectors.maxBy((x, y) -> Double.compare(x.getSalary(), y.getSalary())));
        System.out.println(collect1.get());
        System.out.println("====================================================");
        //summingDouble 获取工资总和
        Double sum = emps.stream().collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);
        System.out.println("====================================================");
        //averagingDouble 获取平均年龄
        Double collect2 = emps.stream().collect(Collectors.averagingDouble(Employee::getAge));
        System.out.println(collect2);
        System.out.println("====================================================");
        //groupingBy 根据状态分组
        Map<Employee.Status, List<Employee>> collect3 = emps.stream().collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(collect3);
        System.out.println("====================================================");
        //groupingBy 多级分组，先状态分组 然后按年龄分组
        Map<Employee.Status, Map<String, List<Employee>>> map = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
                    if(e.getAge() >= 60) {
                        return "老年";
                    }else if(e.getAge() >= 35) {
                        return "中年";
                    }else {
                        return "成年";
                    }
                })));
        System.out.println(map);
        System.out.println("====================================================");
    }

    /**
     * reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
     */
    @Test
    public void test5(){
        //所有工资总和
        Optional<Double> reduce = emps.stream().map(Employee::getSalary).reduce(Double::sum);
        System.out.println(reduce.get());
        System.out.println("====================================================");
        //名字出现六的次数
        Optional<Integer> reduce1 = emps.stream().map(Employee::getName).flatMap(StreamaAPITest::filterCharacter)
                .map((ch) -> {
                    if (ch.equals('六')) {
                        return 1;
                    } else {
                        return 0;
                    }
                }).reduce(Integer::sum);
        System.out.println(reduce1.get());
        System.out.println("====================================================");
    }

    public static Stream<Character> filterCharacter(String str){
        List<Character> list = new ArrayList<>();
        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }
        return list.stream();
    }

    /**
     *
     * 		allMatch——检查是否匹配所有元素
     * 		anyMatch——检查是否至少匹配一个元素
     * 		noneMatch——检查是否没有匹配的元素
     * 		findFirst——返回第一个元素
     * 		findAny——返回当前流中的任意元素
     * 		count——返回流中元素的总个数
     * 		max——返回流中最大值
     * 		min——返回流中最小值
     *
     */
    @Test
    public void test4(){
        //allMatch 是否所有人员的状态都是忙碌
        boolean b = emps.stream().allMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b);
        System.out.println("====================================================");
        //anyMatch 是否存在忙碌的人员
        boolean b1 = emps.stream().anyMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b1);
        System.out.println("====================================================");
        //noneMatch 没有忙碌的人员
        boolean b2 = emps.stream().noneMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b2);
        System.out.println("====================================================");
        //findFirst 获取第一个人员信息
        Optional<Employee> first = emps.stream().filter(e -> e.getAge() < 30).findFirst();
        System.out.println(first.get());
        System.out.println("====================================================");
        //findAny 返回任意一个元素,默认为顺序流因此按照第一个输出
        Optional<Employee> any = emps.stream().filter(e -> e.getSalary() >5000).findAny();
        System.out.println(any.get());
        System.out.println("====================================================");
        //并行流
        Optional<Employee> any1 = emps.parallelStream().filter(e -> e.getSalary() >5000).findAny();
        System.out.println(any1.get());
        System.out.println("====================================================");
        //count 总数
        long count = emps.stream().count();
        System.out.println(count);
        System.out.println("====================================================");
        //max 年龄最大的员工信息
        Optional<Employee> max = emps.stream().max((x,y) -> Integer.compare(x.getAge(),y.getAge()));
        System.out.println(max.get());
        System.out.println("====================================================");
        //min 工资最少是多少
        Optional<Double> min = emps.stream().map(Employee::getSalary).min(Double::compareTo);
        System.out.println(min.get());
        System.out.println("====================================================");
    }

    /**
     * sorted()——自然排序
     * sorted(Comparator com)——定制排序
     */
    @Test
    public void test3(){
        //按年龄排序
        emps.stream().map(Employee::getAge).sorted().forEach(System.out::println);
        System.out.println("====================================================");
        //按年龄排序后按工资排序
        emps.stream().sorted((x,y) -> {
            if(x.getAge()==y.getAge()){
                return Double.compare(x.getSalary(),y.getSalary());
            }else{
                return Integer.compare(x.getAge(),y.getAge());
            }
        }).forEach(System.out::println);

    }

    /**
     * map——接收 Lambda ， 将元素转换成其他形式或提取信息。
     *      接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
     * flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     */
    @Test
    public void test2(){
        List<String> strList = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        strList.stream().map(String::toUpperCase).forEach(System.out::println);

        System.out.println("===================================================");
        strList.stream().flatMap(StreamaAPITest::filterCharacter).forEach(System.out::print);
    }


   /**
    *筛选与切片
    *filter——接收 Lambda ， 从流中排除某些元素。
    *limit——截断流，使其元素不超过给定数量。
    *skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
    *distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
	**/
    @Test
    public void test1(){
        //filter:过滤age大于30的人员信息
        emps.stream().filter((e) -> e.getAge()>30).forEach(System.out::println);
        System.out.println("======================================================");
        //distinct 去重
        emps.stream().distinct().forEach(System.out::println);
        System.out.println("======================================================");
        //limit 获取前两个
        emps.stream().filter(e -> e.getSalary()>4500).limit(1).forEach(System.out::println);
        System.out.println("======================================================");
        //skip 跳过前两条数据
        emps.stream().filter(e -> e.getSalary()>5000).skip(2).forEach(System.out::println);
    }

}
