package com.soareslipe.backend.models.records;

import java.math.BigDecimal;

public record TransactionCNAB(
		Integer tipo,
		String data,
		BigDecimal valor,
		String cpf,
		String cartao,
		String hora,
		String donoLoja,
		String nomeLoja
		) {
	
}
