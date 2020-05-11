package com.enjoy.config;

public final class DataSourceHolder {
    public static final String MASTER_DB = "MASTER";
    public static final String SLAVE_DB = "SALAVE";
    public static ThreadLocal<String> holder=new ThreadLocal<String>();

    public static String getId(){
        if (holder.get()==null)
            return MASTER_DB;
        else
            return holder.get();
    }

    public static void setMasterId(){
        System.out.println("切换Master数据库");
        holder.set(MASTER_DB);
    }

    public static void setSlaveId(){
        System.out.println("切换Slave数据库");
        holder.set(SLAVE_DB);
    }


}
