package com.soareslipe.backend.models;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_transaction")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	Integer tipo;
	Date data;
	BigDecimal valor;
	String cpf;
	String cartao;
	Time hora;
	String donoLoja;
	String nomeLoja;
	
	public Transaction() {}
	
	public Transaction(Long id, Integer tipo, Date data, BigDecimal valor, String cpf, String cartao, Time hora,
			String donoLoja, String nomeLoja) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.data = data;
		this.valor = valor;
		this.cpf = cpf;
		this.cartao = cartao;
		this.hora = hora;
		this.donoLoja = donoLoja;
		this.nomeLoja = nomeLoja;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public Date getData() {
		return data;
	}
	public void setData(String data) throws ParseException {
		var dateFormat = new SimpleDateFormat("yyyyMMdd");
		var date = dateFormat.parse(data);
		this.data = new Date(date.getTime());
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor.divide(BigDecimal.valueOf(100));
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCartao() {
		return cartao;
	}
	public void setCartao(String cartao) {
		this.cartao = cartao;
	}
	public Time getHora() {
		return hora;
	}
	public void setHora(String hora) throws ParseException {
		var dateFormat = new SimpleDateFormat("HHmmss");
		var time = dateFormat.parse(hora);
		this.hora = new Time(time.getTime());
	}
	public String getDonoLoja() {
		return donoLoja;
	}
	public void setDonoLoja(String donoLoja) {
		this.donoLoja = donoLoja;
	}
	public String getNomeLoja() {
		return nomeLoja;
	}
	public void setNomeLoja(String nomeLoja) {
		this.nomeLoja = nomeLoja;
	}
	
	
	

}
