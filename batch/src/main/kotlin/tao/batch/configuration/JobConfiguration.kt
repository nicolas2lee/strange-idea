package tao.batch.configuration

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JobConfiguration(val jobBuilderFactory: JobBuilderFactory,
                       val stepBuilderFactory: StepBuilderFactory) {

    @Bean
    fun itemReader(): ListItemReader<String> {
        return ListItemReader((0..1000).map { i -> i.toString() })
    }

    @Bean
    fun itemWriter(): ItemWriter<String> {
        return ItemWriter { items -> items.forEach { it -> println(">> $it") }
        }
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory.get("step1")
                .chunk<String, String>(100)
                .reader(itemReader())
                .writer(itemWriter())
//                .listener(chunkListener() as ChunkListener)
                .build()
    }

    @Bean
    fun job(): Job {
        return jobBuilderFactory.get("job")
                .start(step1())
                .build()
    }
}