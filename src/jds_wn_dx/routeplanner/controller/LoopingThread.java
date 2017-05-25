package jds_wn_dx.routeplanner.controller;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 07/05/2017
 * Description: Represents a thread that will execute a loop until told to stop.
 */
public class LoopingThread implements Runnable {

    private final Loop loop;
    private Thread loopThread;

    public LoopingThread(Loop loop) {
        this(loop, "unnamed-loop");
    }

    public LoopingThread(Loop loop, String name) {
        this.loop = loop;

        // init the loop thread (a potentially expensive operation
        // that should occur before the application window is opened)
        loopThread = new Thread(this, name);
    }

    public void start() {
        // start the loop thread
        loopThread.start();
    }

    public void stop() {
        try {
            loopThread.interrupt();
            loopThread.join();
        } catch (InterruptedException e) {
            // should never happen
        }
    }

    public void run() {
        // perform pre-loop operations
        loop.init();

        // the looping section; runs as fast as possible
        while (!Thread.interrupted()) {
            loop.update();
        }

        // perform post-loop operations
        loop.dispose();
    }
}
