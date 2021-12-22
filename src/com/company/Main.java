package com.company;

import com.company.dataStructure.RequestQueue;

public class Main {
    public static final int ARM_SPEED = 10;
    public static final int QUEUE_SIZE = 30;

    public static int locationOfDiskArm;
    public static RequestQueue requestQueue = new RequestQueue(QUEUE_SIZE); // 요청큐

    public static void main(String[] args) {
        new SchedulingModule(ARM_SPEED);
    }
}
