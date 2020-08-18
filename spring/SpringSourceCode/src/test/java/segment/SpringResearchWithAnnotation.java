package segment;

import com.research.ResearchStartup;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringResearchWithAnnotation {

    @Test
    public void startupSpring(){
        AnnotationConfigApplicationContext ctx=new AnnotationConfigApplicationContext();
        ctx.register(ResearchStartup.class);
        ctx.refresh();

        for (String beanDefinitionName : ctx.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }
}
