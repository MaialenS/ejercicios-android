package com.maialen.calculadora;

import android.util.Log;

public class Calculos {
	
	private String numero1;
	private String numero2;
	private String operando;
	
	private Boolean punto;
	
	public Calculos(){
		this.numero1="0";
		this.numero2="0";
		this.punto=false;
		this.operando="+";
	}
	
	public String addNumero(String num){
		//comprobar para que solo se pueda introducir un punto
		if(num.contains(".")  && this.punto){
			num="";
		}else if(num.contains(".")){
			this.punto=true;
		}
		
		this.numero1+=num;
		return this.numero1;
		
	}

	
	public String operador(String op){
		String res="";
		
		double num1= Double.parseDouble(this.numero1);
		double num2= Double.parseDouble(this.numero2);
		double numCal;
		//comprobar si se ha pulsado =
		numCal=operar(num1, num2, this.operando);
		if(!op.equals("=")){
			this.operando=op;
		}
		this.numero2=String.valueOf(numCal);
		this.numero1="0";
		return this.numero2;
	}
	
	private double operar(double num1, double num2, String op){
		Log.d("cal", "numero1 "+num1);
		Log.d("cal", "numero2 "+num2);
		Log.d("cal", "op "+op);
		Log.d("cal", "desde casa ");
		double res=0.0;
		if(op.equals("+")){
			res=num1+num2;
		}else if(op.equals("-")){
			res=num1-num2;
		}else if(op.equals("*")){
			res=num1*num2;
		}else if(op.equals("/")){
			res=num1/num2;
		}
		return res;
	}
	
}
