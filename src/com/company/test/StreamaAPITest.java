package com.company.test;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * һ��Stream API �Ĳ������裺
 *
 * 1. ���� Stream
 *
 * 2. �м����
 *
 * 3. ��ֹ����(�ն˲���)
 */
public class StreamaAPITest {

    List<Employee> emps = Arrays.asList(
            new Employee(102, "����", 59, 6666.66, Employee.Status.BUSY),
            new Employee(101, "����", 18, 9999.99, Employee.Status.FREE),
            new Employee(103, "����", 28, 3333.33, Employee.Status.VOCATION),
            new Employee(104, "����", 8, 7777.77, Employee.Status.BUSY),
            new Employee(104, "����", 8, 7777.77, Employee.Status.BUSY),
            new Employee(104, "����", 8, 7777.77, Employee.Status.BUSY),
            new Employee(105, "����", 38, 5555.55, Employee.Status.BUSY)
    );

    /**
     * collect��������ת��Ϊ������ʽ������һ�� Collector�ӿڵ�ʵ�֣����ڸ�Stream��Ԫ�������ܵķ���
     */
    @Test
    public void test6(){
        //toList ���������ַ�ת��list
        emps.stream().map(Employee::getName).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("====================================================");
        //toSet ���������ַ�ת��Set
        emps.stream().map(Employee::getName).collect(Collectors.toSet()).forEach(System.out::println);
        System.out.println("====================================================");
        //joining ��ȡ�����ã��ָ�
        String join = emps.stream().map(Employee::getName).collect(Collectors.joining(","));
        System.out.println(join);
        System.out.println("====================================================");
        //maxBy ��ȡ��������
        Optional<Double> collect = emps.stream().map(Employee::getSalary).collect(Collectors.maxBy(Double::compareTo));
        System.out.println(collect.get());
        System.out.println("====================================================");
        //maxBy ��������Ա����Ϣ
        Optional<Employee> collect1 = emps.stream().collect(Collectors.maxBy((x, y) -> Double.compare(x.getSalary(), y.getSalary())));
        System.out.println(collect1.get());
        System.out.println("====================================================");
        //summingDouble ��ȡ�����ܺ�
        Double sum = emps.stream().collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);
        System.out.println("====================================================");
        //averagingDouble ��ȡƽ������
        Double collect2 = emps.stream().collect(Collectors.averagingDouble(Employee::getAge));
        System.out.println(collect2);
        System.out.println("====================================================");
        //groupingBy ����״̬����
        Map<Employee.Status, List<Employee>> collect3 = emps.stream().collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(collect3);
        System.out.println("====================================================");
        //groupingBy �༶���飬��״̬���� Ȼ���������
        Map<Employee.Status, Map<String, List<Employee>>> map = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
                    if(e.getAge() >= 60) {
                        return "����";
                    }else if(e.getAge() >= 35) {
                        return "����";
                    }else {
                        return "����";
                    }
                })));
        System.out.println(map);
        System.out.println("====================================================");
    }

    /**
     * reduce(T identity, BinaryOperator) / reduce(BinaryOperator) �������Խ�����Ԫ�ط�������������õ�һ��ֵ��
     */
    @Test
    public void test5(){
        //���й����ܺ�
        Optional<Double> reduce = emps.stream().map(Employee::getSalary).reduce(Double::sum);
        System.out.println(reduce.get());
        System.out.println("====================================================");
        //���ֳ������Ĵ���
        Optional<Integer> reduce1 = emps.stream().map(Employee::getName).flatMap(StreamaAPITest::filterCharacter)
                .map((ch) -> {
                    if (ch.equals('��')) {
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
     * 		allMatch��������Ƿ�ƥ������Ԫ��
     * 		anyMatch��������Ƿ�����ƥ��һ��Ԫ��
     * 		noneMatch��������Ƿ�û��ƥ���Ԫ��
     * 		findFirst�������ص�һ��Ԫ��
     * 		findAny�������ص�ǰ���е�����Ԫ��
     * 		count������������Ԫ�ص��ܸ���
     * 		max���������������ֵ
     * 		min��������������Сֵ
     *
     */
    @Test
    public void test4(){
        //allMatch �Ƿ�������Ա��״̬����æµ
        boolean b = emps.stream().allMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b);
        System.out.println("====================================================");
        //anyMatch �Ƿ����æµ����Ա
        boolean b1 = emps.stream().anyMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b1);
        System.out.println("====================================================");
        //noneMatch û��æµ����Ա
        boolean b2 = emps.stream().noneMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b2);
        System.out.println("====================================================");
        //findFirst ��ȡ��һ����Ա��Ϣ
        Optional<Employee> first = emps.stream().filter(e -> e.getAge() < 30).findFirst();
        System.out.println(first.get());
        System.out.println("====================================================");
        //findAny ��������һ��Ԫ��,Ĭ��Ϊ˳������˰��յ�һ�����
        Optional<Employee> any = emps.stream().filter(e -> e.getSalary() >5000).findAny();
        System.out.println(any.get());
        System.out.println("====================================================");
        //������
        Optional<Employee> any1 = emps.parallelStream().filter(e -> e.getSalary() >5000).findAny();
        System.out.println(any1.get());
        System.out.println("====================================================");
        //count ����
        long count = emps.stream().count();
        System.out.println(count);
        System.out.println("====================================================");
        //max ��������Ա����Ϣ
        Optional<Employee> max = emps.stream().max((x,y) -> Integer.compare(x.getAge(),y.getAge()));
        System.out.println(max.get());
        System.out.println("====================================================");
        //min ���������Ƕ���
        Optional<Double> min = emps.stream().map(Employee::getSalary).min(Double::compareTo);
        System.out.println(min.get());
        System.out.println("====================================================");
    }

    /**
     * sorted()������Ȼ����
     * sorted(Comparator com)������������
     */
    @Test
    public void test3(){
        //����������
        emps.stream().map(Employee::getAge).sorted().forEach(System.out::println);
        System.out.println("====================================================");
        //����������󰴹�������
        emps.stream().sorted((x,y) -> {
            if(x.getAge()==y.getAge()){
                return Double.compare(x.getSalary(),y.getSalary());
            }else{
                return Integer.compare(x.getAge(),y.getAge());
            }
        }).forEach(System.out::println);

    }

    /**
     * map�������� Lambda �� ��Ԫ��ת����������ʽ����ȡ��Ϣ��
     *      ����һ��������Ϊ�������ú����ᱻӦ�õ�ÿ��Ԫ���ϣ�������ӳ���һ���µ�Ԫ�ء�
     * flatMap��������һ��������Ϊ�����������е�ÿ��ֵ��������һ������Ȼ������������ӳ�һ����
     */
    @Test
    public void test2(){
        List<String> strList = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        strList.stream().map(String::toUpperCase).forEach(System.out::println);

        System.out.println("===================================================");
        strList.stream().flatMap(StreamaAPITest::filterCharacter).forEach(System.out::print);
    }


   /**
    *ɸѡ����Ƭ
    *filter�������� Lambda �� �������ų�ĳЩԪ�ء�
    *limit�����ض�����ʹ��Ԫ�ز���������������
    *skip(n) ���� ����Ԫ�أ�����һ���ӵ���ǰ n ��Ԫ�ص�����������Ԫ�ز��� n �����򷵻�һ���������� limit(n) ����
    *distinct����ɸѡ��ͨ����������Ԫ�ص� hashCode() �� equals() ȥ���ظ�Ԫ��
	**/
    @Test
    public void test1(){
        //filter:����age����30����Ա��Ϣ
        emps.stream().filter((e) -> e.getAge()>30).forEach(System.out::println);
        System.out.println("======================================================");
        //distinct ȥ��
        emps.stream().distinct().forEach(System.out::println);
        System.out.println("======================================================");
        //limit ��ȡǰ����
        emps.stream().filter(e -> e.getSalary()>4500).limit(1).forEach(System.out::println);
        System.out.println("======================================================");
        //skip ����ǰ��������
        emps.stream().filter(e -> e.getSalary()>5000).skip(2).forEach(System.out::println);
    }

}
