package com.research.spel;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

public class Evaluation {
    public static void main(String[] args) {


//        EvaluationContextTest();

//        EvaluationTest();
    }

    private static void EvaluationContextTest() {

//        Parser Configuration
        class Demo {
            public List<String> list;
        }

        SpelParserConfiguration config = new SpelParserConfiguration(true, true);
        ExpressionParser parser = new SpelExpressionParser(config);
        Expression exp = parser.parseExpression("list[3]");
        Demo demo = new Demo();
        Object obj = exp.getValue(demo);
        System.out.println(obj);


//        当评估表达式时，EvaluationContext接口用于解决属性，方法，字段帮助执行类型转换。Spring提供2个实现类SimpleEvaluationContext,StandaradEvaluationContext
//        SimpleEvaluationContext暴露SpEL语言特性子集和配置选项。表达式类型不需要SpEL语言语法全部支持，部分有意义的限制。配置支持级别一个或多个组合：
//          Custom PropertyAccessor only (no reflection)
//          Data binding properties for read-only access
//          Data binding properties for read and write
//        StandardEvaluationContext支持SpEL语言全部特性和配置选项。
        class Simple {
            public List<Boolean> booleansList = new ArrayList<>();
        }

        Simple simple = new Simple();
        simple.booleansList.add(true);

        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        ExpressionParser expressionParser = new SpelExpressionParser();
        expressionParser.parseExpression("booleansList[0]").setValue(context, simple, false);
        System.out.println(simple.booleansList.get(0));
    }

    private static void EvaluationTest() {

        ExpressionParser expressionParser = new SpelExpressionParser();
//        Expression exp = expressionParser.parseExpression("'Hello World'");
//        System.out.println((String)exp.getValue());// Hello World

        Expression exp = expressionParser.parseExpression("'Hello World'.concat('!')");
        System.out.println((String) exp.getValue());

        exp = expressionParser.parseExpression("'Hello World'.bytes");
        byte[] bytes = (byte[]) exp.getValue();
        System.out.println(Arrays.toString(bytes));// [72, 101, 108, 108, 111, 32, 87, 111, 114, 108, 100]
        System.out.println(new String(bytes));// Hello World
        exp = expressionParser.parseExpression("'Hello World'.bytes.length");
        System.out.println(exp.getValue());// 11

        exp = expressionParser.parseExpression("new String(\"Hello World\").toUpperCase()");
        System.out.println(exp.getValue());//HELLO WORLD

        GregorianCalendar c = new GregorianCalendar();
        c.set(1857, 7, 9);
        Inventor inventor = new Inventor("Nikola Testla", c.getTime(), "Serbian");

        exp = expressionParser.parseExpression("name");
        String name = (String) exp.getValue(inventor);
        System.out.println(name);
        name = exp.getValue(inventor, String.class);
        System.out.println(name);

        exp = expressionParser.parseExpression("name == 'Nikola Testla'");
        boolean equivalent = exp.getValue(inventor, Boolean.class);
        System.out.println(equivalent);
    }
}
