package com.apu.payment.utils;

public class ServiceUtil {
    public static String HOST = "http://PAYSLIP-SERVICE";
    public static String CONTEXT_PATH = "/service-api";
    public static String GENERATE_PAYSLIP_WHILE_JOINING = HOST+CONTEXT_PATH+"/api/payslip/join";
    public static String GET_EMP_CURRENT_SALARY = HOST+CONTEXT_PATH+"/api/employee/salary";
    public static String SAVE_EMPLOYEE_TAX = HOST+CONTEXT_PATH+"/api/employee/tax";
    public static String SAVE_PROVIDENT_FUND = HOST+CONTEXT_PATH+"/api/employee/tax";
}
