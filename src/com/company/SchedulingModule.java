package com.company;

import com.company.algorithms.CLOOKScheduling;
import com.company.algorithms.FCFSScheduling;
import com.company.algorithms.SSTFScheduling;
import com.company.dataStructure.Request;
import com.company.dataStructure.RequestQueue;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SchedulingModule {
    private int speed;

    private Scanner sc = new Scanner(System.in);
    private StringTokenizer st;

    private RequestQueue requestQueue = Main.requestQueue; // Main의 request Queue 변수를 가져옴

    // 생성자
    public SchedulingModule(int speed) {
        this.speed = speed;

        // 현재의 disk arm 위치 입력받기.
        System.out.print("현재 Disk arm의 위치를 입력하시오 : ");
        Main.locationOfDiskArm = Integer.parseInt(sc.next());

        System.out.print("Request들의 위치를 입력하시오(,로 구분) : ");
        st = new StringTokenizer(sc.next(), ",");

        // 요청들을 request Queue에 삽입
        while(st.hasMoreTokens()) {
            requestQueue.enQueue(new Request(Integer.parseInt(st.nextToken())));
        }

        System.out.print("진행할 스케쥴링을 선택하십시오(FCFS : 1, SSTF : 2, CLOOK : 3) : ");
        switch (Integer.parseInt(sc.next())) {
            case 1 : new FCFSScheduling(requestQueue, speed); break;
            case 2 : new SSTFScheduling(requestQueue, speed); break;
            case 3 : new CLOOKScheduling(requestQueue, speed); break;
        }
    }
}
