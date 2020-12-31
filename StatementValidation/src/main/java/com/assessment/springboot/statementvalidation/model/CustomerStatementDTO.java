package com.assessment.springboot.statementvalidation.model;

import java.io.Serializable;

public class CustomerStatementDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6736769016693387684L;

private Integer transactionReference;
	
	private String accountNumber;
	
	private Double startBalance;
	
	private Double mutation;
	
	private String description;
	
	private Double endBalance;

	public Integer getTransactionReference() {
		return transactionReference;
	}

	public void setTransactionReference(Integer transactionReference) {
		this.transactionReference = transactionReference;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Double getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(Double startBalance) {
		this.startBalance = startBalance;
	}

	public Double getMutation() {
		return mutation;
	}

	public void setMutation(Double mutation) {
		this.mutation = mutation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(Double endBalance) {
		this.endBalance = endBalance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((endBalance == null) ? 0 : endBalance.hashCode());
		result = prime * result + ((mutation == null) ? 0 : mutation.hashCode());
		result = prime * result + ((startBalance == null) ? 0 : startBalance.hashCode());
		result = prime * result + ((transactionReference == null) ? 0 : transactionReference.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomerStatementDTO other = (CustomerStatementDTO) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (endBalance == null) {
			if (other.endBalance != null)
				return false;
		} else if (!endBalance.equals(other.endBalance))
			return false;
		if (mutation == null) {
			if (other.mutation != null)
				return false;
		} else if (!mutation.equals(other.mutation))
			return false;
		if (startBalance == null) {
			if (other.startBalance != null)
				return false;
		} else if (!startBalance.equals(other.startBalance))
			return false;
		if (transactionReference == null) {
			if (other.transactionReference != null)
				return false;
		} else if (!transactionReference.equals(other.transactionReference))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerStatementDTO [transactionReference=" + transactionReference + ", accountNumber=" + accountNumber
				+ ", startBalance=" + startBalance + ", mutation=" + mutation + ", description=" + description
				+ ", endBalance=" + endBalance + "]";
	}

	

	
	
}