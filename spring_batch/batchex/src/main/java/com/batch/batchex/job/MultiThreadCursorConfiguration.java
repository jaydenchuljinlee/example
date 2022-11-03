package com.batch.batchex.job;

import com.batch.batchex.entity.Pay;
import com.batch.batchex.entity.Pay2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.support.SynchronizedItemStreamReader;
import org.springframework.batch.item.support.builder.SynchronizedItemStreamReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MultiThreadCursorConfiguration {
    public static final String JOB_NAME = "multiThreadCursorBatch";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    private int chunkSize;

    @Value("${chunkSize:1}")
    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    private int poolSize;

    @Value("${poolSize:2}")
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    @Bean(name = JOB_NAME + "taskPool")
    public TaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize);
        executor.setThreadNamePrefix("multi-thread-");
        executor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
        executor.initialize();

        return executor;
    }

    @Bean(name = JOB_NAME)
    public Job job() throws Exception {
        return jobBuilderFactory.get(JOB_NAME)
                .preventRestart()
                .start(step())
                .build();
    }

    @Bean(name = JOB_NAME + "_step")
    public Step step() throws Exception {
        return stepBuilderFactory.get(JOB_NAME + "_step")
                .<Pay, Pay2>chunk(chunkSize)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(executor())
                .throttleLimit(poolSize)
                .build();
    }

    @Bean(name = JOB_NAME + "_reader")
    public SynchronizedItemStreamReader<Pay> reader() throws Exception {
        String sql = "SELECT id, amount, tx_name, tx_date_time FROM pay";

        JdbcCursorItemReader<Pay> itemReader = new JdbcCursorItemReaderBuilder<Pay>()
                .name(JOB_NAME + "_reader")
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Pay.class))
                .sql(sql)
                .build();

        return new SynchronizedItemStreamReaderBuilder<Pay>()
                .delegate(itemReader)
                .build();
    }

    private ItemProcessor<Pay, Pay2> processor() {
        return pay -> {
            log.info("Pay = {}", pay);
            Thread.sleep(1000);
            return Pay2.from(pay);
        };
    }

    @Bean(name = JOB_NAME + "_writer")
    @StepScope
    public JdbcBatchItemWriter<Pay2> writer() {
        return new JdbcBatchItemWriterBuilder<Pay2>()
                .dataSource(dataSource)
                .sql("insert into pay2(amount, tx_name, tx_date_time) values (:amount, :txName, :txDateTime)")
                .beanMapped()
                .build();
    }
}
