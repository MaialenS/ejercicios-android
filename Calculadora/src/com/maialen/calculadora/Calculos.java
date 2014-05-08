package com.maialen.calculadora;

import android.util.Log;

public class Calculos {
	
	static final String STATE_NUM1 = "estadoNum1";
	static final String STATE_NUM2 = "estadoNum2";
	static final String STATE_OPERANDO = "estadoOperando";
	static final String STATE_PUNTO = "estadoPunto";
	
	public String numero1;
	public String numero2;
	public String operando;
	public Boolean punto;
	
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
		Log.d("cal", "operar-cal");
		this.punto=false;
		
		double num1= Double.parseDouble(this.numero1);
		double num2= Double.parseDouble(this.numero2);
		double numCal;
		//comprobar si se ha pulsado =
		numCal=operar(num1, num2, this.operando);
		if(!op.equals("=")){
			this.operando=op;
		}else{
			this.operando="+";
		}
		this.numero2=String.valueOf(numCal);
		this.numero1="0";
		return this.numero2;
	}
	
	public String borrar(String op){
		Log.d("cal", "borrar-cal");
		this.punto=false;
		if(op.equals("delete")){
			this.numero2="0";
			this.operando="+";
		}
		
		this.numero1="0";
		
		return this.numero1;
	}
	
	
	private double operar(double num1, double num2, String op){

		double res=0.0;
		if(op.equals("+")){
			res=num2+num1;
		}else if(op.equals("-")){
			res=num2-num1;
		}else if(op.equals("*")){
			res=num2*num1;
		}else if(op.equals("/")){
			res=num2/num1;
		}
		return res;
	}
	
}
