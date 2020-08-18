package com.spring;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * Hello world!
 *
 */
public class App <T extends CharSequence>
{
    public static void main( String[] args )
    {
        TypeVariable<Class<App>>[] typeVariables=App.class.getTypeParameters();

        for (TypeVariable<Class<App>> typeVariable : typeVariables) {
            System.out.println( typeVariable.getName());
            System.out.println(typeVariable.getTypeName());
            Type[] bounds=typeVariable.getBounds();
            for (Type bound : bounds) {
                System.out.println(bound);
            }

            System.out.println(typeVariable.getGenericDeclaration());

        }

        System.out.println( "Hello World!" );
    }
}
