package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.demo.itemprocessors.PessoaItemProcessor;
import com.example.demo.listeners.JobCompletionNotificationListener;
import com.example.demo.model.Pessoa;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    
    
    // tag::readerwriterprocessor[]
    @Bean
    public FlatFileItemReader<Pessoa> reader() {
        return new FlatFileItemReaderBuilder<Pessoa>()
            .name("pessoaItemReader")
            .resource(new ClassPathResource("sample-data.csv"))
            .delimited()
            .names(new String[]{"nome", "sobreNome"})
            .fieldSetMapper(new BeanWrapperFieldSetMapper<Pessoa>() {{
                setTargetType(Pessoa.class);
            }})
            .build();
    }
    
    
    @Bean
    public PessoaItemProcessor processor() {
        return new PessoaItemProcessor();
    }
    
    
    
    @Bean
    public JdbcBatchItemWriter<Pessoa> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Pessoa>()
            .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
            .sql("INSERT INTO pessoa (nome, sobre_nome) VALUES (:nome, :sobreNome)")
            .dataSource(dataSource)
            .build();
    }
    // end::readerwriterprocessor[]
    
    
    // tag::jobstep[]
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importarUsuarioJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }
    
    @Bean
    public Step step1(JdbcBatchItemWriter<Pessoa> writer) {
        return stepBuilderFactory.get("Passo1")
            .<Pessoa, Pessoa> chunk(10)
            .reader(reader())
            .processor(processor())
            .writer(writer)
            .build();
    }
    // end::jobstep[]

}
