package com.research.spel.language;

import com.research.spel.Inventor;
import com.research.spel.PlaceOfBirth;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.SimpleEvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.ExpressionContext;
import org.thymeleaf.standard.expression.StandardExpressionParser;

import java.lang.reflect.Method;
import java.util.*;


public class ExpressionPlayground {
    public static void main(String[] args) {
        ExpressionParser expressionParser = new SpelExpressionParser();
        EvaluationContext context = SimpleEvaluationContext.forReadOnlyDataBinding().build();
        Inventor inventor = new Inventor("Nikola Testla", "Serbian");
        inventor.setInventions(new String[]{"Wireless Communication", "induction motor"});
        inventor.setPlaceOfBirth(new PlaceOfBirth("Shanghai", "China"));
//      属性名称可以大小小不敏感
        String city = (String) expressionParser.parseExpression("placeOfBirth.city").getValue(context, inventor);
        System.out.println(city);
        int year = (Integer) expressionParser.parseExpression("Birthdate.Year+1900").getValue(context, inventor);
        System.out.println(year);

        String inductionmotor = expressionParser.parseExpression("inventions[1]").getValue(inventor, String.class);
        System.out.println(inductionmotor);

        List numbers = (List) expressionParser.parseExpression("{1,2,3,4,5}").getValue();
        System.out.println(numbers);

        List listofList = (List) expressionParser.parseExpression("{{'a','b'},{'x','y'}}").getValue();
        System.out.println(listofList);

        Map inventorInfo = (Map) expressionParser.parseExpression("{name:'Michael',nationality:'China'}").getValue(context);
        System.out.println(inventorInfo);

        Map mapOfMaps = (Map) expressionParser.parseExpression("{name:{first:'Nikola',last:'Tesla'},dob:{day:10,month:'July',year:1856}}").getValue(context);
        System.out.println(mapOfMaps);

        int[] numbers1 = (int[]) expressionParser.parseExpression("new int[4]").getValue(context);
        //不能将基础数据类型转换成包装类型的list
        System.out.println(Arrays.asList(numbers1));
        int[] numbers2 = (int[]) expressionParser.parseExpression("new int[4]{1,2,3,4}").getValue(context);
        System.out.println(numbers2.length);

        String bc = expressionParser.parseExpression("'abc'.substring(1,3)").getValue(String.class);
        System.out.println(bc);

//     创建Clss对象
        Class dateClass = expressionParser.parseExpression("T(java.util.Date)").getValue(Class.class);
        System.out.println(dateClass);
        Class stringClass = expressionParser.parseExpression("T(java.lang.String)").getValue(Class.class);
        System.out.println(stringClass);
//      创建对象实例
        Inventor inventor1 = expressionParser.parseExpression("new com.research.spel.Inventor('Michael','China')").getValue(Inventor.class);
        System.out.println(inventor1.getName() + "-" + inventor1.getNationality());
//       给对象变量赋值
        Inventor tesla = new Inventor("Nikola Tesla", "Serbian");
        EvaluationContext writeContext = SimpleEvaluationContext.forReadWriteDataBinding().build();
        writeContext.setVariable("newName", "Mike Tesla");
        expressionParser.parseExpression("Name = #newName").getValue(writeContext, tesla);
        System.out.println(tesla.getName());
//      #this
        List<Integer> primes = new ArrayList<Integer>();
        primes.addAll(Arrays.asList(1, 2, 3, 4, 5));
        context.setVariable("primes", primes);

        List<Integer> primesGreaterThanThree = (List<Integer>) expressionParser.parseExpression("#primes.?[#this>3]").getValue(context);
        System.out.println(primesGreaterThanThree);

//        Method
        try {
            Method isEmptyMethod = StringUtils.class.getDeclaredMethod("isEmpty", Object.class);
            context.setVariable("emptyMethod", isEmptyMethod);
            boolean resultIsempty = (Boolean) expressionParser.parseExpression("#emptyMethod('Michael')").getValue(context);
            System.out.println(resultIsempty);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
//        Bean Reference
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        standardEvaluationContext.setBeanResolver(new BeanResolver() {
            class One {
            }

            class Two {
            }

            class Three {
            }

            @Override
            public Object resolve(EvaluationContext evaluationContext, String s) throws AccessException {
                switch (s.toLowerCase()) {
                    case "one":
                        return new One();
                    case "two":
                        return new Two();
                    case "three":
                        return new Three();
                    default:
                        return null;
                }
            }
        });
        Object bean = expressionParser.parseExpression("@one").getValue(standardEvaluationContext);
        System.out.println(bean);
//      Ternary Operation(If-Then-Else)
        String falseString = expressionParser.parseExpression("false?'trueExp':'falseExp'").getValue(String.class);
        System.out.println(falseString);
//      Elvis Operator
        String nameElvisResult = expressionParser.parseExpression("name?:'Unknown'").getValue(new Inventor(), String.class);
        System.out.println(nameElvisResult);
//      Safe Navigation Operator
        Inventor inventor2 = new Inventor("Jack", "China");
        inventor2.setPlaceOfBirth(new PlaceOfBirth("Bamberg"));
        String cityValue = expressionParser.parseExpression("placeOfBirth?.City").getValue(context, inventor2, String.class);
        System.out.println(cityValue);
        inventor2.setPlaceOfBirth(null);
        cityValue = expressionParser.parseExpression("placeOfBirth?.City").getValue(context, inventor2, String.class);
        System.out.println(cityValue);
//      Collection Seleciton
        List<PlaceOfBirth> list = new ArrayList<>();
        list.add(new PlaceOfBirth("Shanghai", "China"));
        list.add(new PlaceOfBirth("Hubei", "China"));
        list.add(new PlaceOfBirth("New York", "America"));
        standardEvaluationContext.setVariable("placeOfBirths", list);
        List<Inventor> canadaList = (List<Inventor>) expressionParser.parseExpression("#placeOfBirths.?[country=='America']").getValue(standardEvaluationContext);
        System.out.println(canadaList.size());
        Map<String, Integer> myMap = new HashMap();
        myMap.put("michael", 23);
        myMap.put("jack", 19);
        standardEvaluationContext.setVariable("myMap", myMap);
//        map
        Map newMap = expressionParser.parseExpression("#myMap.?[value>20]").getValue(standardEvaluationContext, Map.class);
        System.out.println(newMap);// 输出：{michael=23}
//      Collection Projection
        List countryList = (List) expressionParser.parseExpression("#placeOfBirths.![country]").getValue(standardEvaluationContext);
        System.out.println(countryList);// 只获取country字段值：[China, China, America]
//      Expression templating(使用#{})
        String randomPhrase=expressionParser.parseExpression("random number is #{T(java.lang.Math).random()}",
                new TemplateParserContext()).getValue(String.class);
        System.out.println(randomPhrase);

//        literalExpressions();
    }


    private static void literalExpressions() {
        ExpressionParser expressionParser = new SpelExpressionParser();
        String helloWorld = (String) expressionParser.parseExpression("'Hello World'").getValue();
        System.out.println(helloWorld);
        double avogadrosNumber = (double) expressionParser.parseExpression("6.02215E3").getValue();
        System.out.println(avogadrosNumber);
        int maxValue = (Integer) expressionParser.parseExpression("0x7FFFFFFF").getValue();
        System.out.println(maxValue);
        boolean trueValue = (Boolean) expressionParser.parseExpression("true").getValue();
        System.out.println(trueValue);
        Object nullValue = expressionParser.parseExpression("null").getValue();
        System.out.println(nullValue);
    }

}
