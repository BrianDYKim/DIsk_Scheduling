package com.company.algorithms;

import com.company.Main;
import com.company.UserMathLibrary;
import com.company.dataStructure.Direction;
import com.company.dataStructure.Request;
import com.company.dataStructure.RequestQueue;

import java.util.ArrayList;
import java.util.Scanner;

public class CLOOKScheduling {
    private RequestQueue requestQueue;
    private int armSpeed;
    private int armLocation = Main.locationOfDiskArm;
    private Direction armDirection; // 디스크 헤드가 움직일 방향
    private int leftmostLocation;
    private int rightmostLocation;

    private int seekDistance = 0; // seek distance
    private double totalTime = 0; // 경과 시간
    private double responseTimeSum = 0; // 각 request마다의 response time들의 총합
    private double responseTimeAverage; // 응답시간의 평균
    private double responseTimeVariance; // 응답시간의 표준편차
    private ArrayList<Double> responseTimeArrayList = new ArrayList<>(); // response time들의 배열

    private Request readRequest; // 현재 읽고있는 request

    private Scanner sc = new Scanner(System.in);

    public CLOOKScheduling(RequestQueue requestQueue, int armSpeed) {
        int initialArmLocation = armLocation; // 초기의 위치를 저장
        int count = 0;

        this.requestQueue = requestQueue;
        this.armSpeed = armSpeed;
        this.leftmostLocation = getLeftmostLocation();
        this.rightmostLocation = getRightmostLocation();

        // 방향 결정
        System.out.print("Disk head가 움직일 방향을 입력하시오(negative : 0, positive : 1) : ");

        if(Integer.parseInt(sc.next()) == 1)
            armDirection = Direction.POSITIVE;
        else
            armDirection = Direction.NEGATIVE;

        /** 작업 진행(방향에 따라)*/
        // 오른쪽으로 이동하는 경우
        if(armDirection == Direction.POSITIVE) {
            // 오른쪽으로 이동
            for(int i = armLocation; i <= rightmostLocation; i++) {
                seekDistance += 1; // 1만큼 이동
                totalTime += 1.0 / armSpeed;
                armLocation += 1;

                for(int j = 0; j < requestQueue.getSize(); j++) {
                    // 지나가다가 request가 걸리는 경우
                    if(requestQueue.peek(j).getLocation() == i) {
                        requestQueue.peek(j).setResponseTime(totalTime);
                        responseTimeArrayList.add(totalTime);
                        responseTimeSum += totalTime;
                        count++;
                    }
                }
            }

            // 끝으로 이동
            armLocation = leftmostLocation;
            seekDistance += rightmostLocation - leftmostLocation - 2;
            totalTime += (double)(rightmostLocation - leftmostLocation - 1) / armSpeed;

            // 다시 오른쪽으로 이동
            for(int i = armLocation; i < initialArmLocation; i++) {
                if(count == requestQueue.getSize())
                    break;

                seekDistance += 1; // 1만큼 이동
                totalTime += 1.0 / armSpeed;
                armLocation += 1;

                for(int j = 0; j < requestQueue.getSize(); j++) {
                    // 지나가다가 request가 걸리는 경우
                    if(requestQueue.peek(j).getLocation() == i) {
                        requestQueue.peek(j).setResponseTime(totalTime);
                        responseTimeArrayList.add(totalTime);
                        responseTimeSum += totalTime;
                        count++;
                    }
                }
            }
        }

        // 왼쪽으로 이동하는 경우
        else if(armDirection == Direction.NEGATIVE) {
            for(int i = armLocation; i >= leftmostLocation; i--) {
                seekDistance += 1; // 1만큼 이동
                totalTime += 1.0 / armSpeed;
                armLocation -= 1;

                for(int j = 0; j < requestQueue.getSize(); j++) {
                    // 지나가다가 request가 걸리는 경우
                    if(requestQueue.peek(j).getLocation() == i) {
                        requestQueue.peek(j).setResponseTime(totalTime);
                        responseTimeArrayList.add(totalTime);
                        responseTimeSum += totalTime;
                        count++;
                    }
                }
            }

            // 끝으로 이동
            armLocation = rightmostLocation;
            seekDistance += rightmostLocation - leftmostLocation - 2;
            totalTime += (double)(rightmostLocation - leftmostLocation - 1) / armSpeed;

            // 다시 왼쪽으로 이동
            for(int i = armLocation; i > initialArmLocation; i--) {
                if(count == requestQueue.getSize())
                    break;

                seekDistance += 1; // 1만큼 이동
                totalTime += 1.0 / armSpeed;
                armLocation += 1;

                for(int j = 0; j < requestQueue.getSize(); j++) {
                    // 지나가다가 request가 걸리는 경우
                    if(requestQueue.peek(j).getLocation() == i) {
                        requestQueue.peek(j).setResponseTime(totalTime);
                        responseTimeArrayList.add(totalTime);
                        responseTimeSum += totalTime;
                        count++;
                    }
                }
            }
        }

        // 응답시간의 표준편차 구하기
        responseTimeAverage = responseTimeSum / (double)requestQueue.getSize();
        responseTimeVariance = UserMathLibrary.getStandardVariance(responseTimeArrayList, responseTimeAverage);

        UserMathLibrary.prettyPrint(requestQueue);

        System.out.println("Seek distance : " + seekDistance);
        System.out.println("표준편차 : " + responseTimeVariance);
    }

    public int getRightmostLocation() {
        int max = 0;

        // 위치의 최댓값을 가지는 인덱스 탐색
        for(int i = 0; i < requestQueue.getSize(); i++) {
            if(requestQueue.peek(max).getLocation() < requestQueue.peek(i).getLocation())
                max = i;
        }

        return requestQueue.peek(max).getLocation();
    }

    public int getLeftmostLocation() {
        int min = 0;

        // 위치의 최솟값을 가지는 인덱스를 탐색
        for(int i = 0; i < requestQueue.getSize(); i++) {
            if(requestQueue.peek(min).getLocation() > requestQueue.peek(i).getLocation())
                min = i;
        }

        return requestQueue.peek(min).getLocation();
    }
}
