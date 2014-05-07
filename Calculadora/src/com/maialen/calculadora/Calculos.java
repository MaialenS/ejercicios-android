package com.maialen.calculadora;

public class Calculos {
	
	private String numero1;
	private String numero2;
	private Boolean primerNumero;
	private Boolean punto;
	
	public Calculos(){
		this.numero1="";
		this.numero2="";
		this.primerNumero=true;
		this.punto=false;
	}
	
	public String addNumero(String num){
		//comprobar para que solo se pueda introducir un punto
		if(num.contains(".")  && this.punto){
			num="";
		}else if(num.contains(".")){
			this.punto=true;
		}
		
		if(this.primerNumero){
			this.numero1+=num;
			return this.numero1;
		}else{
			this.numero2+=num;
			return this.numero2;
		}
	}

	
	
	
}
