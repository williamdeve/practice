package org.springframework.samples.mvc.basic.account;
public class TestImpl implements TestInterface {
	private int i=0;
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	private TestImpl(){
		this.i=2;
	}
	//@PostConstruct
	public int init() {
		// TODO Auto-generated method stub
		return this.getI();
	}

}
