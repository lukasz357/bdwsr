package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {

    private static MyThread         coordinator;
    private boolean                 verbose  = true;

    private List<MyThread>          myThreadsRunnableList;
    private List<Thread>            threadsList;

    private static final Controller instance = new Controller();
    private boolean                 electionStarted;

    public static Controller getInstance() {
        return instance;
    }

    private Controller() {

    }

    public void startThreads(int numberOfThreads, int priorityMax, int coordinatorNumber) {

        myThreadsRunnableList = new ArrayList<MyThread>(numberOfThreads);
        threadsList = new ArrayList<Thread>(numberOfThreads);

        Random r = new Random();
        for (int i = 0; i < numberOfThreads; i++) {
            myThreadsRunnableList.add(new MyThread(r.nextInt(priorityMax), i));
        }

        getMyThreadByNumber(coordinatorNumber).setCoordinator(true);
        Controller.coordinator = getMyThreadByNumber(coordinatorNumber);

        for (int i = 0; i < numberOfThreads; i++) {
            threadsList.add(new Thread(myThreadsRunnableList.get(i)));
            threadsList.get(i).start();
        }

    }

    public void coordinatorIsDead() {
        MainFrame.getInstance().getTextArea().append("KOORDYNATOR JEST MARTWY!\n");
        coordinator.kill();
        verbose = false;
        threadsList.remove(coordinator);
        myThreadsRunnableList.remove(coordinator);
        System.out.println("WIELKOSC LISTY WATKOW: " + threadsList.size() + " : " + myThreadsRunnableList.size());
    }

    public void stopThreads() {
        for (int i = 0; i < threadsList.size(); i++) {
            threadsList.get(i).interrupt();
        }
    }

    public Thread getThreadByNumber(int number) {
        return threadsList.get(number);
    }

    public MyThread getMyThreadByNumber(int number) {
        return myThreadsRunnableList.get(number);
    }

    public MyThread getCoordinator() {
        System.out.println("ZWRACAM KOORDYNATORA: " + coordinator.getNumber());
        return coordinator;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public void setCoordinator(MyThread coordinator) {
        System.out.println("NOWY KOORDYNATOR: " + coordinator.getNumber());
        Controller.coordinator = coordinator;
    }

    public boolean isElectionStarted() {
        return electionStarted;
    }

    public void setElectionStarted(boolean electionStarted) {
        this.electionStarted = electionStarted;
    }

    public List<MyThread> getMyThreadsRunnableList() {
        return myThreadsRunnableList;
    }

    public void setMyThreadsRunnableList(List<MyThread> myThreadsRunnableList) {
        this.myThreadsRunnableList = myThreadsRunnableList;
    }

    // public void pauseThreads() {
    // for (int i = 0; i < threadsList.size(); i++) {
    // System.out.println(threadsList);
    // try {
    // threadsList.get(i).wait();
    // } catch (InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    // }
}
