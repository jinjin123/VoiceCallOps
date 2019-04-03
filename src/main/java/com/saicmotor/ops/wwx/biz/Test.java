package com.saicmotor.ops.wwx.biz;

/**
 * Created  by  Jimmy  on 2018/12/18
 *
 */

class Person {
	public static void prt(String s) {
		System.out.println(s);
	}

	Person() {
		prt("父类·无参数构造方法： \"+\"A Person.");
	}
	
	Person(String name){
		prt("父类·含一个参数构造方法： \"+\"A Person." + name);
	}
}

public class Test extends Person {


	Test(){
		super();
		prt("子类·无参数构造方法： \"+\"A chinese boy");
	}
	
	Test(String name){
		super(name);
		prt("子类·含一个参数构造方法： \"+\"A chinese boy"  + name);
	}
	
	Test(String name,int age){
		 this(name);
		 prt("子类·含一个参数构造方法： \"+\"A chinese boy"  + age);
	}
	
	public static void main(String[] args) {
		Test t = new Test();
		t = new Test("a");
		t = new Test("a",18);
	}
}