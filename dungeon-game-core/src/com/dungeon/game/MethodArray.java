package com.dungeon.game;

import java.lang.reflect.Method;
import java.util.Arrays;

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
	
	

}
