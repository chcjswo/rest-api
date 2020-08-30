package com.mocadev.restapi.stream;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {

	public static void main(String[] args) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter isoLocalDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		System.out.println(now.format(isoLocalDateTime));

		List<String> names = new ArrayList<>();
		names.add("chcjswo");
		names.add("tony");
		names.add("heyjin");
		names.add("foo");

		List<String> collect = names.stream()
			.map(String::toUpperCase)
			.collect(Collectors.toList());

		collect.forEach(System.out::println);

		List<OnlineClass> springClass = new ArrayList<>();
		springClass.add(new OnlineClass(1, "spring boot", true));
		springClass.add(new OnlineClass(2, "spring data jpa", true));
		springClass.add(new OnlineClass(3, "spring mvc", false));
		springClass.add(new OnlineClass(4, "spring core", false));
		springClass.add(new OnlineClass(5, "rest api development", false));

		System.out.println("spring 으로 시작하는 수업");
		springClass.stream()
			.filter(s -> s.getTitle().startsWith("spring"))
			.forEach(s -> System.out.println(s.getTitle()));

		System.out.println("===================================");

		System.out.println("close 되지 않은 수업");
		springClass.stream()
			.filter(Predicate.not(OnlineClass::isClosed))
			.forEach(s -> System.out.println(s.getTitle()));

		System.out.println("===================================");

		System.out.println("수업 이름만 모아서 스트림 만들기");
		springClass.stream()
			.map(OnlineClass::getTitle)
			.forEach(System.out::println);

		System.out.println("===================================");

		List<OnlineClass> javaClass = new ArrayList<>();
		javaClass.add(new OnlineClass(6, "The java, Test", true));
		javaClass.add(new OnlineClass(7, "The java, Code manipulation", true));
		javaClass.add(new OnlineClass(8, "The java, 8 to 11", false));

		List<List<OnlineClass>> events = new ArrayList<>();
		events.add(springClass);
		events.add(javaClass);

		System.out.println("두 수업 목록에 들어 있는 모든 수업 아이디 출력");
		events.stream()
			.flatMap(Collection::stream)
			.forEach(s -> System.out.println(s.getTitle()));

		System.out.println("===================================");

		System.out.println("10부 1씩  증가하는 무제한 스트림 중에서 앞에 10개 빼고 최대 10개 까지만");
		Stream.iterate(10, i -> i + 1)
			.skip(10)
			.limit(10)
			.forEach(System.out::println);

		System.out.println("===================================");

		System.out.println("자바 수업 중에 Test가 들어있는 수업이 있는지 확인");
		boolean test = javaClass.stream().anyMatch(s -> s.getTitle().contains("Test"));
		System.out.println(test);

		System.out.println("===================================");

		System.out.println("스프링 수업 중에 제목에 spring이 들어간 제목만 모아서 List로 만들기");
		List<String> spring = springClass
			.stream()
			.filter(s -> s.getTitle().contains("spring"))
			.map(OnlineClass::getTitle)
			.collect(Collectors.toList());

		spring.forEach(System.out::println);
	}
}
