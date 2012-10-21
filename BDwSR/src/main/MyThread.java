package main;

import java.util.Random;

public class MyThread implements Runnable {

    private int        priority      = 0;
    private int        number        = 0;
    private int        DELAY         = 3000;
    private boolean    isCoordinator = false;
    private boolean    isAlive       = true;
    private Controller controller;

    public MyThread(int priority, int number) {
        this.priority = priority;
        this.number = number;
    }

    public void run() {
        controller = Controller.getInstance();
        try {
            while (true && isAlive) {
                if (controller.isVerbose()) {
                    MainFrame
                            .getInstance()
                            .getTextArea()
                            .append("Wątek " + number + " o priorytecie: " + priority + " zgłasza się!" + (isCoordinator() ? " JESTEM KOORDYNATOREM!" : "")
                                    + "\n");
                }

                if (!isCoordinator()) {
                    String message = controller.getCoordinator().getMessageFromCoordinator();
                    if (message == null) {
                        startElection();
                    } else {
                        MainFrame.getInstance().getTextArea().append("Wątek " + number + " otrzymał informację od koordynatora: " + message);
                    }
                }
                Thread.sleep(DELAY);
            }
        } catch (Exception exception) {
            System.out.println("EXCEPTION: ");
            exception.printStackTrace();
        }
    }

    private void startElection() {
        if (!controller.isElectionStarted()) {
            if (controller.getMyThreadsRunnableList().size() == 1) {
                MainFrame.getInstance().getTextArea().append("Brak żywych wątków! Kończę działanie programu.\n");
                controller.stopThreads();
                return;
            }
            //TODO DODAC SYNCHRONIZED
            controller.setElectionStarted(true);
            MainFrame.getInstance().getTextArea().append("=== Wątek " + number + " o priorytecie "+ getPriority() + " rozpoczyna proces elekcji spośród "+controller.getMyThreadsRunnableList().size()+" wątków!\n");

            Random random = new Random();
            
            MyThread maxPriorityThread = null;
            int maxPriority = 0;
            
            for (MyThread mt : controller.getMyThreadsRunnableList()) {
                if (maxPriorityThread == null)
                    maxPriorityThread = mt;
                if (mt.getPriority() > maxPriorityThread.getPriority())
                    maxPriorityThread = mt;
            }
            
            //ZABIJ LOSOWY WĄTEK
            int number = random.nextInt(controller.getMyThreadsRunnableList().size());
            MainFrame.getInstance().getTextArea().append("OOOPS! Wątek nr: "+number+" padł w trakcje elekcji.\n");
            maxPriority = controller.getMyThreadsRunnableList().get(number).getPriority();
            controller.getMyThreadsRunnableList().remove(number);
            
            if (maxPriorityThread.getPriority() == maxPriority) {
                MainFrame.getInstance().getTextArea()
                .append(">>> Nowy koordynator padł w trakcie algorytmu elekcji. Rozpoczynam jeszcze raz.\n");
                controller.setElectionStarted(false);
                startElection();
            }
            else {
                MainFrame.getInstance().getTextArea()
                .append("> Nowym koordynatorem został wątek nr: " + maxPriorityThread.getNumber() + " z priorytetem: " + maxPriorityThread.getPriority()+"\n");
            }
            
            System.out.println("TU");
            maxPriorityThread.setCoordinator(true);
            maxPriorityThread.setAlive(true);
            controller.setCoordinator(maxPriorityThread);
            //controller.setVerbose(true);
            controller.setElectionStarted(false);
        }
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isCoordinator() {
        return isCoordinator;
    }

    public void setCoordinator(boolean isCoordinator) {
        this.isCoordinator = isCoordinator;
    }

    public String getMessageFromCoordinator() {
        if (isAlive) {
            if (isCoordinator()) {
                return "Wiadomość zwrotna od koordynatora.\n";
            } else
                return "Nie jestem koordynatorem, nie wiem czemu mnie ktoś odpytuje\n";
        } else
            return null;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void kill() {
        if (isCoordinator)
            isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

}
