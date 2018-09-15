package com.rene.helloworld.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // for configs
@EnableBatchProcessing // batch
public class JobConfiguration {

    /**
     * Automatically provided without any other configuration
     */
    @Autowired
    JobBuilderFactory jobBuilderFactory;

    /**
     * Automatically provided without any other configuration
     */
    @Autowired
    StepBuilderFactory stepBuilderFactory;

    /**
     * Step1, as to be defined in helloWorldJob().
     * The get("step1") actually doesn't get anything from somewhere else, it just sets the name of the step.
     *
     * @return
     */
    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("Hello, World!");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    /**
     * This Job is having one step, within start(). There is no need to configure a "helloWorldJob" that
     * is get(), this is only used to set the name. (for now?).
     *
     * @return
     */
    @Bean
    public Job helloWorldJob() {
        return this.jobBuilderFactory.get("helloWorldJob")
                .start(step1())
                .build();
    }
}
