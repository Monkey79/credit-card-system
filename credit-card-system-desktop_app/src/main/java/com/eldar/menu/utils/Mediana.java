package com.eldar.menu.utils;

import java.util.ArrayList;
import java.util.List;

public class Mediana {
	
	public static void Case1_TwoDaysFiveExpenses_ShuldAlertOnce() {
		List<Integer> expenses = new ArrayList<>();
		int days = 2;

		expenses.add(10);
		expenses.add(20);
		expenses.add(30);
		expenses.add(40);
		expenses.add(50);
		
		Fraud_Detector(expenses, days);
	}
	
	public static void Case2_ThreeDaysFiveExpenses_ShuldAlertOnce() {
		List<Integer> expenses = new ArrayList<>();
		int days = 3;

		expenses.add(10);
		expenses.add(20);
		expenses.add(30);
		expenses.add(40);
		expenses.add(50);
		
		Fraud_Detector(expenses, days);
	}
	
	//--------------------------------MAIN----------------------------
	public static void main(String[] args) {
		Case1_TwoDaysFiveExpenses_ShuldAlertOnce();	
		System.out.println("*************************");
		Case2_ThreeDaysFiveExpenses_ShuldAlertOnce();
	}
	
	
	public static void Fraud_Detector(List<Integer> expenses, int days) {
		int top = expenses.size() - days;
		int median = 0;		
		int fraud = 0;
		
		for(int i=0; i<top; i++) {
			median = CalculateGetMedian(expenses, i, days+i);
			int nDys = days + i;
			if(expenses.get(nDys) >= (median*2)) {
				System.out.println("---ALERTA: Movimiento sospechoso, llamen a Moe");
				System.out.println("-->la mediana es=" + median + " y el dia " + (nDys+1) + " se ha gastado " + expenses.get(nDys) );
				fraud++;
			}
		}
		System.out.println("---------Alertas Totales--------------");
		System.out.println("Cantidad de alertas " + fraud);
		System.out.println("--------------------------------------");	
	}

	public static int CalculateGetMedian(List<Integer> values,int from, int limit) {
		List<Integer> list = values.subList(from, limit);
		int median = 0;		
		int n = list.size();

		if (n % 2 == 0) {
			median = (int) Math.floor((list.get(n / 2 - 1) + list.get(n / 2)) / 2.0);
		} else {
			median = (int) Math.floor(list.get(n / 2));
		}		
		return median;
	}
}
