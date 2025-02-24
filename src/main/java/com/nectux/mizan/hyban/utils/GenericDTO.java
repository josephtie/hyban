package com.nectux.mizan.hyban.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class GenericDTO<T> {
	
	@JsonProperty("row") private T row;

	@JsonProperty("rows") private List<T> rows;

	@JsonProperty("total") private long total;
	
	@JsonProperty("result") private Object result;
	
	@JsonProperty("status") private boolean status;
	
	@JsonProperty("message") private String message;

	@JsonProperty("errors") private List<Erreur> errors;

	public GenericDTO() {
		// TODO Auto-generated constructor stub
		super();
	}

	public T getRow() {
		return row;
	}

	public void setRow(T row) {
		this.row = row;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Erreur> getErrors() {
		return errors;
	}

	public void setErrors(List<Erreur> errors) {
		this.errors = errors;
	}
}
