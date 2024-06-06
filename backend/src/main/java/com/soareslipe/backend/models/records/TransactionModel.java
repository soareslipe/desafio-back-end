package com.soareslipe.backend.models.records;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public record TransactionModel(
		Long id,
		Integer tipo,
		Date data,
		BigDecimal valor,
		String cpf,
		String cartao,
		Time hora,
		String donoLoja,
		String nomeLoja
		) {
	
}