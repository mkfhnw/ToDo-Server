package client.model;

import client.ClientNetworkPlugin;
import server.services.InputValidator;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class LoadTasksCallable implements Callable {

    // Fields
    private ClientNetworkPlugin clientNetworkPlugin;
    private ArrayList<String> taskIDs;
    private ArrayList<ToDo> tasks;

    // Constructor
    public LoadTasksCallable(ArrayList<String> taskIDs, ClientNetworkPlugin clientNetworkPlugin) {
        this.clientNetworkPlugin = clientNetworkPlugin;
        this.taskIDs = taskIDs;
        this.tasks = new ArrayList<>();
    }

    // Run Method
    @Override
    public ArrayList<ToDo> call() throws Exception {
        System.out.println("[LOAD-TASKS-CALLABLE] Started loading tasks from database...");

        for (String ID : this.taskIDs) {
            int intID = Integer.parseInt(ID);
            ArrayList<String> itemData = this.clientNetworkPlugin.getToDo(intID);
            ToDo item = this.parseItemFromMessageString(itemData);
            this.tasks.add(item);
        }
        int length = this.tasks.size();
        System.out.println("[LOAD-TASKS-CALLABLE] Scraped " + length + " tasks from the database.");
        return this.tasks;
    }

    // Method to parse Instance of Model out of message string
    public ToDo parseItemFromMessageString(ArrayList<String> itemData) {
        ToDo returnItem = null;

        // Grab fixed components & set ambiguous components to null
        String title = itemData.get(1);
        String priority = itemData.get(2);
        String description = null;
        String dueDate = null;
        String category = null;

        // Parse dynamic parts
        InputValidator inputValidator = new InputValidator();

        // Loop through data parts and figure out ambiguous parameters
        if (itemData.size() >= 3) {
            int dataPartsLength = itemData.size();
            for (String ambiguousParameter : itemData.subList(3, dataPartsLength)) {
                String parameterType = inputValidator.getParameterType(ambiguousParameter);
                switch (parameterType) {

                    case "Category" -> {
                        category = ambiguousParameter;
                    }
                    case "Description" -> {
                        description = ambiguousParameter;
                    }
                    case "DueDate" -> {
                        dueDate = ambiguousParameter;
                    }
                    case "Undefined" -> {
                        return null;
                    }
                }
            }
        }

        // Create new Item

        // 3 missing parameters
        if(description == null && dueDate == null && category == null) {
            return new ToDo(title, priority);
        }

        // Missing 2 parameters
        // Missing dueDate and category
        if(description != null && dueDate == null && category == null) {
            return new ToDo(title, priority, description);
        }

        // Missing description and category
        if(description == null && dueDate != null && category == null) {
            return new ToDo(title, priority, dueDate);
        }

        // Missing description and dueDate
        if(description == null && dueDate == null && category != null) {
            return new ToDo(title, priority, category);
        }

        // Missing 1 parameter
        // Missing category
        if(description != null && dueDate != null && category == null) {
            return new ToDo(title, priority, description, dueDate);
        }

        // Missing dueDate
        if(description != null && dueDate == null && category != null) {
            return new ToDo(title, priority, description, category);
        }

        // Missing description
        if(description == null && dueDate != null && category != null) {
            return new ToDo(title, priority, dueDate, category);
        }

        // No missing parameters
        returnItem = new ToDo(title, priority, description, dueDate, category);
        return returnItem;
    }

}
