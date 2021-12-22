package com.company.algorithms;

import com.company.Main;
import com.company.UserMathLibrary;
import com.company.dataStructure.Request;
import com.company.dataStructure.RequestQueue;

import java.util.ArrayList;

public class FCFSScheduling {
    private RequestQueue requestQueue;
    private int armSpeed;
    private int armLocation = Main.locationOfDiskArm;

    private int seekDistance = 0; // seek distance
    private double totalTime = 0; // 경과 시간
    private double responseTimeSum = 0; // 각 request마다의 response time들의 총합
    private double responseTimeAverage; // 응답시간의 평균
    private double responseTimeVariance; // 응답시간의 표준편차
    private ArrayList<Double> responseTimeArrayList = new ArrayList<>(); // response time들의 배열

    /**
     * Constructor
     * @param armSpeed that means speed of disk arm
     * @param requestQueue that means Queue of request
     * */
    public FCFSScheduling(RequestQueue requestQueue, int armSpeed) {
        this.requestQueue = requestQueue;
        this.armSpeed = armSpeed;

        // 작업 시작
        requestQueue.getRequestArrayList().stream().forEach(request -> {
            seekDistance += Math.abs(request.getLocation() - armLocation);
            totalTime += (double)Math.abs(request.getLocation() - armLocation) / armSpeed; // 경과 시간을 반영
            request.setResponseTime(totalTime); // response time 설정
            responseTimeArrayList.add(totalTime);
            responseTimeSum += totalTime; // response time의 합에 반영
            armLocation = request.getLocation(); // arm의 위치 반영
        });

        // 응답시간의 표준편차 구하기
        responseTimeAverage = responseTimeSum / (double)requestQueue.getSize();
        responseTimeVariance = UserMathLibrary.getStandardVariance(responseTimeArrayList, responseTimeAverage);

        UserMathLibrary.prettyPrint(requestQueue);

        System.out.println("Seek distance : " + seekDistance);
        System.out.println("표준편차 : " + responseTimeVariance);
    }
}
