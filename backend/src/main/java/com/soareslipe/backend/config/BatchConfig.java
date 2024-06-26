package com.soareslipe.backend.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.soareslipe.backend.models.Transaction;
import com.soareslipe.backend.models.records.TransactionCNAB;

@Configuration
public class BatchConfig {

	private PlatformTransactionManager transactionManager;
	private JobRepository jobRepository;

	public BatchConfig(PlatformTransactionManager transactionManager, JobRepository jobRepository) {
		this.transactionManager = transactionManager;
		this.jobRepository = jobRepository;
	}

	@Bean
	Job job(Step step) {
		return new JobBuilder("job", jobRepository).start(step).incrementer(new RunIdIncrementer()).build();
	}

	@Bean
	Step step(ItemReader<TransactionCNAB> reader, ItemProcessor<TransactionCNAB, Transaction> processor,
			ItemWriter<Transaction> writer) {
		return new StepBuilder("step", jobRepository).<TransactionCNAB, Transaction>chunk(1000, transactionManager)
				.reader(reader).processor(processor).writer(writer).build();
	}

	@Bean
	FlatFileItemReader<TransactionCNAB> reader() {
		return new FlatFileItemReaderBuilder<TransactionCNAB>().name("reader")
				.resource(new FileSystemResource("files/CNAB.txt")).fixedLength()
				.columns(new Range(1, 1), new Range(2, 9), new Range(10, 19), new Range(20, 30), new Range(31, 42),
						new Range(43, 48), new Range(49, 62), new Range(63, 80))
				.names("tipo", "data", "valor", "cpf", "cartao", "hora", "donoLoja", "nomeLoja")
				.targetType(TransactionCNAB.class).build();
	}

	@Bean
	ItemProcessor<TransactionCNAB, Transaction> processor() {
		return item -> {
			Transaction model = new Transaction();
			model.setId(null);
			model.setCartao(item.cartao());
			model.setCpf(item.cpf());
			model.setData(item.data());
			model.setDonoLoja(item.donoLoja());
			model.setHora(item.hora());
			model.setNomeLoja(item.nomeLoja());
			model.setTipo(item.tipo());
			model.setValor(item.valor());
			return model;
		};
	}

	@Bean
	JdbcBatchItemWriter<Transaction> writer(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Transaction>().dataSource(dataSource).sql("""
				 INSERT INTO TB_TRANSACTION (
				tipo, data, valor, cpf, cartao,
				hora, dono_loja, nome_loja) VALUES (
						:tipo, :data, :valor, :cpf, :cartao,
						:hora, :donoLoja, :nomeLoja)
						""").beanMapped().build();
	}
	
	@Bean
	static BeanDefinitionRegistryPostProcessor jobRegistryBeanPostProcessorRemover() {
	  return registry -> registry.removeBeanDefinition("jobRegistryBeanPostProcessor");
	}

}
