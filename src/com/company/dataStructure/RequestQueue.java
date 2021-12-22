package com.company.dataStructure;

import java.util.ArrayList;

public class RequestQueue {
    private int size;
    private int numOfData;
    private ArrayList<Request> requestArrayList = new ArrayList<>();

    public RequestQueue(int size) {
        this.size = size;
    }

    public boolean isEmpty() {
        if(numOfData == 0)
            return true;
        else
            return false;
    }

    public boolean isFull() {
        if(requestArrayList.size() == size)
            return true;
        else
            return false;
    }

    public void enQueue(Request request) {
        if(!isFull()) {
            numOfData++;
            requestArrayList.add(request);
        }
        else {
            System.out.println("Request Queue 포화 상태.");
            return;
        }
    }

    public Request deQueue() {
        Request tempRequest = null;

        if(!isEmpty()) {
            tempRequest = requestArrayList.remove(0);
            numOfData--;
            return tempRequest;
        }
        else {
            System.out.println("Request Queue에 데이터가 존재하지 않음.");
            return tempRequest;
        }
    }

    public Request peek(int index) {
        return requestArrayList.get(index);
    }

    public int getSize() {
        return this.numOfData;
    }

    public void swap(int index1, int index2) {
        Request tempRequest = requestArrayList.get(index1);

        requestArrayList.set(index1, requestArrayList.get(index2));
        requestArrayList.set(index2, tempRequest);
    }

    public ArrayList<Request> getRequestArrayList() {
        return requestArrayList;
    }
}
