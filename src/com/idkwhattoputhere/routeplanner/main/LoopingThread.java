package com.idkwhattoputhere.routeplanner.main;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 07/05/2017
 * Description: Represents a thread that will loop until told to stop.
 */
public abstract class LoopingThread implements Runnable {

    private Thread loopThread;

    public LoopingThread(String name) {
        // prepare the loop thread (a potentially expensive operation
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

    @Override
    public void run() {
        // perform pre-loop operations
        prepare();

        // the looping section; runs as fast as possible
        while (!Thread.interrupted()) {
            update();
        }

        // perform post-loop operations
        dispose();
    }

    // not required to implement but provided nonetheless
    protected void prepare() {
    }

    protected void dispose() {
    }

    // required to implement
    protected abstract void update();
}
