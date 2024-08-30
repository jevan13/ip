package Yapper;import java.io.File;import java.io.FileWriter;import java.io.IOException;import java.time.LocalDateTime;import java.util.ArrayList;import java.util.Scanner;public class Storage {    public File file;    public Storage(File file) {        this.file = file;    }    public void writeHistory(ArrayList<Task> listOfTasks) {        try {            FileWriter filewriter = new FileWriter(file);            for (Task task : listOfTasks) {                filewriter.write(task.toFile() + "\n");            }            filewriter.close();        } catch (IOException e) {            System.out.println("Unable to write history");        }    }    public TaskList loadHistory() {        ArrayList<Task> listOfTasks = new ArrayList<>();        try {            if (this.file.exists()) {                Scanner sc = new Scanner(this.file);                while(sc.hasNextLine()) {                    String nextline = sc.nextLine();                    this.loadTask(listOfTasks, nextline);                }            }        } catch (IOException var4) {            System.out.println("Unable to load history");        } catch (YapperException e) {            System.out.println(e.getMessage());        }        return new TaskList(listOfTasks, this);    }    public void loadTask(ArrayList<Task> listOfTasks, String input) throws YapperException {        String typeOfTask = input.substring(0, 1);        String[] taskParameter = input.substring(2).split("--");        String fileDoneSymbol;        String taskName;        if (typeOfTask.equals("T")) {            if (taskParameter.length != 2) {                throw new YapperException("Unable to read file: Invalid ToDo Parameters");            }            fileDoneSymbol = taskParameter[0];            taskName = taskParameter[1];            boolean done = fileDoneSymbol.equals("D");            ToDo toDo = new ToDo(taskName);            toDo.setDone(done);            listOfTasks.add(toDo);        } else {            String fromString;            if (typeOfTask.equals("D")) {                if (taskParameter.length != 3) {                    throw new YapperException("Unable to read file: Invalid Deadline Parameters");                }                fileDoneSymbol = taskParameter[0];                taskName = taskParameter[1];                fromString = taskParameter[2];                boolean done = fileDoneSymbol.equals("D");                LocalDateTime byDateTime = LocalDateTime.parse(fromString);                Deadline deadline = new Deadline(taskName, byDateTime);                deadline.setDone(done);                listOfTasks.add(deadline);            } else {                if (!typeOfTask.equals("E")) {                    throw new YapperException("Unable to read file: Invalid Task type");                }                if (taskParameter.length != 4) {                    throw new YapperException("Unable to read file: Invalid Event Parameters");                }                fileDoneSymbol = taskParameter[0];                taskName = taskParameter[1];                fromString = taskParameter[2];                String toString = taskParameter[3];                boolean done = fileDoneSymbol.equals("D");                LocalDateTime fromDateTime = LocalDateTime.parse(fromString);                LocalDateTime toDateTime = LocalDateTime.parse(toString);                Event event = new Event(taskName, fromDateTime, toDateTime);                event.setDone(done);                listOfTasks.add(event);            }        }    }}