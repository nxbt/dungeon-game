package com.dungeon.game.utilities;

import java.lang.reflect.Method;
import java.util.Arrays;

import com.dungeon.game.entity.Entity;

public class MethodArray {
	
	public Method[] methods;
	public MethodArray(int numMethods){
	
		methods = Arrays.copyOfRange(this.getClass().getMethods(), 0, numMethods);
		String[] methodNames = new String[numMethods];
		for(int i = 0; i < numMethods; i++)methodNames[i] = methods[i].getName();
		Arrays.sort(methodNames);
		Method[] newMethods = new Method[numMethods];
		for(int i = 0; i < numMethods; i++){
			for(int k = 0; k < numMethods; k++){
				if(methodNames[i].equals(methods[k].getName())){
					newMethods[i] = methods[k];
				}
			}
		}
		methods = newMethods;
	}
	
	
	//example of how to use MethodArray <----
//	MethodArray methodArray = new MethodArray(3){
//		public void method1(){
//			System.out.println(player.name);
//		}
//		
//		public void method2(){
//			System.out.println("test2");
//		}
//		
//		public void method3(){
//			System.out.println("test3");
//		}
//		
//	};
//	
//	for(int i = 0; i < 10; i++){
//		try {
//			methodArray.methods[0].invoke(methodArray, null);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	

}
