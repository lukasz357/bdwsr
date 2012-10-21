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

    public void startThreads(int numberOfThreads, int priorityMax, int coordinatorNumber, int delay) {

        myThreadsRunnableList = new ArrayList<MyThread>(numberOfThreads);
        threadsList = new ArrayList<Thread>(numberOfThreads);

        Random r = new Random();
        for (int i = 0; i < numberOfThreads; i++) {
            myThreadsRunnableList.add(new MyThread(r.nextInt(priorityMax), i, delay));
        }

        getMyThreadByNumber(coordinatorNumber).setCoordinator(true);
        Controller.coordinator = getMyThreadByNumber(coordinatorNumber);

        for (int i = 0; i < numberOfThreads; i++) {
            threadsList.add(new Thread(myThreadsRunnableList.get(i)));
            threadsList.get(i).start();
        }

    }

    public void coordinatorIsDead() {
        System.out.println("\t\tRUNNABLE LIST SIZE: "+myThreadsRunnableList.size());
        MainFrame.getInstance().getTextArea().append("KOORDYNATOR JEST MARTWY!\n");
        verbose = false;
        threadsList.get(coordinator.getNumber()).interrupt();
        coordinator.kill();
        removeThreadWithNumber(coordinator.getNumber());
        System.out.println("\t\tRUNNABLE LIST SIZE: "+myThreadsRunnableList.size());
    }

    public void killThreadWithNumber(int number) {
        System.out.println("\tRUNNABLE LIST SIZE: "+myThreadsRunnableList.size());
        threadsList.get(number).interrupt();
        myThreadsRunnableList.get(number).kill();
        removeThreadWithNumber(number);
        System.out.println("\tRUNNABLE LIST SIZE: "+myThreadsRunnableList.size());
    }

    public MyThread getMaxPriorityThread() {
        MyThread maxPriorityThread = null;

        for (MyThread mt : getMyThreadsRunnableList()) {
            if (maxPriorityThread == null)
                maxPriorityThread = mt;
            if (mt.getPriority() > maxPriorityThread.getPriority())
                maxPriorityThread = mt;
        }
        return maxPriorityThread;
    }

    private void removeThreadWithNumber(int number) {
        MyThread toRemove = null;
        for (MyThread r : myThreadsRunnableList) {
            if (r.getNumber() == number) {
                toRemove = r;
                break;
            }
        }
        myThreadsRunnableList.remove(toRemove);
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
        return coordinator;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public void setCoordinator(MyThread coordinator) {
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
}
