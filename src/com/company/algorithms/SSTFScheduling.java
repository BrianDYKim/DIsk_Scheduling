package com.company.algorithms;

import com.company.Main;
import com.company.UserMathLibrary;
import com.company.dataStructure.Request;
import com.company.dataStructure.RequestQueue;

import java.util.ArrayList;

public class SSTFScheduling {
    private RequestQueue requestQueue;
    private int armSpeed;
    private int armLocation = Main.locationOfDiskArm;

    private int seekDistance = 0; // seek distance
    private double totalTime = 0; // 경과 시간
    private double responseTimeSum = 0; // 각 request마다의 response time들의 총합
    private double responseTimeAverage; // 응답시간의 평균
    private double responseTimeVariance; // 응답시간의 표준편차
    private ArrayList<Double> responseTimeArrayList = new ArrayList<>(); // response time들의 배열

    private Request readRequest; // 현재 읽고있는 request

    public SSTFScheduling(RequestQueue requestQueue, int armSpeed) {
        int readingRequestIndex; // 현재 읽고있는 request의 인덱스
        ArrayList<Integer> alreadyReadIndex = new ArrayList<>(); // 이미 읽은 request의 인덱스를 저장하는 리스트
        int count = 0; // 작업 카운터

        this.requestQueue = requestQueue;
        this.armSpeed = armSpeed;

        // request의 개수만큼만 loop 반복
        while(count < requestQueue.getSize()) {
            readingRequestIndex = getClosestRequestIndex(requestQueue,alreadyReadIndex, armLocation);
            readRequest = requestQueue.peek(readingRequestIndex);
            alreadyReadIndex.add(readingRequestIndex); // 이미 읽었음 상태로 처리
            count++;

            // 작업 시작
            seekDistance += Math.abs(readRequest.getLocation() - armLocation); // Seek distance 설정
            totalTime += (double)Math.abs(readRequest.getLocation() - armLocation) / armSpeed; // 경과 시간 갱신
            requestQueue.peek(readingRequestIndex).setResponseTime(totalTime); // 응답 시간 갱신
            responseTimeArrayList.add(totalTime);
            responseTimeSum += totalTime;
            armLocation = readRequest.getLocation();
        }

        // 응답시간의 표준편차 구하기
        responseTimeAverage = responseTimeSum / (double)requestQueue.getSize();
        responseTimeVariance = UserMathLibrary.getStandardVariance(responseTimeArrayList, responseTimeAverage);

        UserMathLibrary.prettyPrint(requestQueue);

        System.out.println("Seek distance : " + seekDistance);
        System.out.println("표준편차 : " + responseTimeVariance);
    }

    // 현재의 disk arm 위치와 제일 가까운 request의 인덱스를 반환하는 메소드
    public int getClosestRequestIndex(RequestQueue requestQueue, ArrayList<Integer> alreadyReadIndex, int armLocation) {
        ArrayList<Integer> distanceList = new ArrayList<>();
        int min = 0;

        requestQueue.getRequestArrayList().stream().forEach(request -> {
            distanceList.add(Math.abs(request.getLocation() - armLocation)); // 거리를 저장
        });

        // 이미 방문한 인덱스를 min으로 두는 것을 방지하는 코드
        for(int i = 0; i < distanceList.size(); i++) {
            if(!alreadyReadIndex.contains(i)) {
                min = i;
                break;
            }
            else
                continue;
        }

        for(int i = 0; i < distanceList.size(); i++) {
            // 이전에 방문하지 않았음을 전제로함
            if(distanceList.get(min) > distanceList.get(i) && !alreadyReadIndex.contains(i))
                min = i;
        }

        return min;
    }
}
