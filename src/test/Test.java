package test;

import memory.hardware.PCB;

import java.util.LinkedList;
import java.util.Queue;

public class Test {
        private Queue<Integer> queue = new LinkedList<>();
        public Test(){
            queue.add(1);
        }

    public Queue<Integer> getQueue() {
        return queue;
    }

    public static void main(String[] args) {
            Test test = new Test();

            System.out.println(test.getQueue().poll());
            System.out.println(test.getQueue().poll());
    }
}

