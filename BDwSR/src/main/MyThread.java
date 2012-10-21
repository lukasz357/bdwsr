package main;

import java.util.Random;

public class MyThread implements Runnable {

    private int        priority      = 0;
    private int        DELAY         = 3000;
    private int        numberr        = 0;
    private boolean    isCoordinator = false;
    private boolean    isAlive       = true;
    private Controller controller;

    public MyThread(int priority, int number, int delay) {
        this.priority = priority;
        this.numberr = number;
        this.DELAY = delay;
    }

    public void run() {
        controller = Controller.getInstance();
        try {
            while (true && isAlive) {
                if (controller.isVerbose()) {
                    MainFrame
                            .getInstance()
                            .getTextArea()
                            .append("Wątek " + numberr + " o priorytecie: " + priority + " zgłasza się!" + (isCoordinator() ? " JESTEM KOORDYNATOREM!" : "")
                                    + "\n");
                }

                if (!isCoordinator()) {
                    String message = controller.getCoordinator().getMessageFromCoordinator();
                    if (message == null) {
                        startElection();
                    } else {
                        MainFrame.getInstance().getTextArea().append("Wątek " + numberr + " otrzymał informację od koordynatora: \"" + message + "\".\n");
                    }
                }
                Thread.sleep(DELAY);
            }
        } catch (Exception exception) {
            System.out.println("Wątek: " + numberr + " został zabity.");
        }
    }

    private synchronized void startElection() {
        if (!controller.isElectionStarted()) {
            if (controller.getMyThreadsRunnableList().size() <= 1) {
                MainFrame.getInstance().getTextArea().append("!!! Brak żywych wątków! Kończę działanie programu.\n");
                controller.stopThreads();
                return;
            }
            controller.setElectionStarted(true);
            MainFrame
                    .getInstance()
                    .getTextArea()
                    .append("=== Wątek " + numberr + " o priorytecie " + getPriority() + " rozpoczyna proces elekcji spośród "
                            + controller.getMyThreadsRunnableList().size() + " wątków!\n");

            Random random = new Random();


            //WYBIERZ WATEK O NAJWYZSZYM PRIORYTECIE
            MyThread maxPriorityThread = controller.getMaxPriorityThread();

            // ZABIJ LOSOWY WĄTEK
            int number = random.nextInt(controller.getMyThreadsRunnableList().size());
            while (number == this.getNumber() || !controller.getMyThreadsRunnableList().get(number).isAlive()) {
                System.out.println("NUMBER: "+number);
                number = random.nextInt(controller.getMyThreadsRunnableList().size());
                System.out.println("NUMBER: "+number);
            }
            controller.killThreadWithNumber(number);
            MainFrame.getInstance().getTextArea().append("OOOPS! Wątek nr: " + number + " padł w trakcje elekcji.\n");
            
            
            if (maxPriorityThread.getNumber() == number) {
                MainFrame.getInstance().getTextArea().append(">>> Nowy koordynator padł w trakcie algorytmu elekcji. Rozpoczynam jeszcze raz.\n");
                controller.setElectionStarted(false);
                startElection();
            } else {
                MainFrame
                        .getInstance()
                        .getTextArea()
                        .append("> Nowym koordynatorem został wątek nr: " + maxPriorityThread.getNumber() + " z priorytetem: "
                                + maxPriorityThread.getPriority() + "\n");
            }
            
            maxPriorityThread.setCoordinator(true);
            maxPriorityThread.setAlive(true);
            System.out.println("KOORDYNATOR NUMER: "+maxPriorityThread.getNumber());
            controller.setCoordinator(maxPriorityThread);
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
                return "Wiadomość zwrotna od koordynatora.";
            } else
                return "Nie jestem koordynatorem, nie wiem czemu mnie ktoś odpytuje\n";
        } else
            return null;
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

    public int getNumber() {
        return numberr;
    }

    public int getDELAY() {
        return DELAY;
    }

    public void setDELAY(int dELAY) {
        DELAY = dELAY;
    }

}
