package com.company;

import com.company.dataStructure.Request;
import com.company.dataStructure.RequestQueue;

import java.util.ArrayList;

public class UserMathLibrary {

    // 표준편차를 구하는 메소드
    public static double getStandardVariance(ArrayList<Double> numberList, double average) {
        double variance = 0;

        for(double num : numberList) {
            variance += Math.pow(num - average, 2);
        }
        variance /= numberList.size();

        variance = Math.sqrt(variance);

        return variance;
    }

    // 98,183,37,122,14,124,65,67
    public static void prettyPrint(RequestQueue requestQueue) {
        int count = 0;

        System.out.println("-----------------------------------------");
        System.out.println("request     location    response time");

        for(Request request : requestQueue.getRequestArrayList()) {
            System.out.println("request" + count + "     " + request.getLocation() + "          " + request.getResponseTime());
            count++;
        }

        System.out.println("-----------------------------------------");
    }
}
