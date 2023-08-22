package com.example.computer_science_ia.Controllers;

import com.example.computer_science_ia.*;
import com.example.computer_science_ia.Handling.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class MainMenuController {
    @FXML
    private Label labelOne;
    @FXML
    private Label labelTwo;
    @FXML
    private Label labelThree;
    @FXML
    private Label labelFour;
    @FXML
    private Label labelFive;
    @FXML
    private Label labelSix;
    @FXML
    private Label welcomeMessage;
    @FXML
    private TextField searchBar;
    @FXML
    private ListView<String> searchResults;
    @FXML
    private Label noFileFoundLabel;
    @FXML
    private Label noUserInputLabel;
    @FXML
    private ListView<String> notesListView;
    @FXML
    private ListView<String> taskListView;
    @FXML
    private ListView<String> fileListView;
    @FXML
    private Button closeButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button fileListViewBackButton;
    @FXML
    private ContextMenu fileListViewContextMenu;
    @FXML
    private MenuItem deleteMenuItem;
    @FXML
    private NotesList currentNotesList;
    @FXML
    private TaskList currentTaskList;
    @FXML
    private ContextMenu sortContextMenu;
    @FXML
    private Button sortButton;
    private Stage stage;
    public static FilePathHandling currentFilePath;
    private LabelHandling labelHandling;

    @FXML
    public void initialize(){
        // Ensures labels aren't null when the stage field is initialised
        Platform.runLater(() -> {
            ArrayList<Label> subjectLabels = new ArrayList<>(Arrays.asList(labelOne, labelTwo, labelThree, labelFour, labelFive,labelSix));
            ArrayList<Label> otherLabels = new ArrayList<>(Arrays.asList(noUserInputLabel, noFileFoundLabel));

            LabelHandling subjectLabelHandling = new LabelHandling(subjectLabels); // Label handling for subject labels
            labelHandling = new LabelHandling(otherLabels); // Label handling for other labels

            ArrayList<String> subjects = UserSession.getSubjects(); // Creates arraylist of the user's subjects

            subjectLabelHandling.setSubjectLabels(subjectLabels, subjects); // Sets the labels to the user's subjects
            LabelHandling.setWelcomeMessage(welcomeMessage, UserSession.getLoggedInUsername()); // Sets the welcome message based on the user's username

            noFileFoundLabel.setVisible(false);
            noUserInputLabel.setVisible(false);
            stage = (Stage) labelOne.getScene().getWindow();

            currentFilePath = new FilePathHandling();

            populateNoteListView();
            populateTaskListView();

            Scene scene = noUserInputLabel.getScene();
            scene.getWindow().setOnCloseRequest(windowEvent -> {
                saveNotesListContent();
                saveTaskListContent();
            });
        });
    }

    public void onTaskListViewClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            String taskTitle = taskListView.getSelectionModel().getSelectedItem();
            Task selectedTask = currentTaskList.getTaskByTitle(taskTitle);
            if (selectedTask != null) {
                showTaskDetails(selectedTask);
            }
        }
    }

    private void showTaskDetails(Task task) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Task Details");
        alert.setHeaderText("Title: " + task.getTitle() + "\nDue Date: " + task.getDueDate() + "\nStatus: " + task.getStatus().toString() + "\nPriority: " + task.getPriority().toString());
        alert.setContentText("Description:\n" + task.getDescription());
        alert.showAndWait();
    }

    public void loadRenameTaskScreen() {
        String selectedTaskTitle = taskListView.getSelectionModel().getSelectedItem();
        Task task = currentTaskList.getTaskByTitle(selectedTaskTitle);
        if (task!= null) {
            ScreenHandling.loadFXMLScreen("renameTaskScreen.fxml", "Rename Task: " + selectedTaskTitle, 359, 234, false, this);
        }
    }

    public void loadSetTaskDescriptionScreen() {
        String selectedTaskTitle = taskListView.getSelectionModel().getSelectedItem();
        Task task = currentTaskList.getTaskByTitle(selectedTaskTitle);
        if (task!= null) {
            ScreenHandling.loadFXMLScreen("setTaskDescriptionScreen.fxml", "Set Task Description: " + selectedTaskTitle, 359, 234, false, this);
        }
    }

    public void loadSetTaskDueDateScreen(){
        String selectedTaskTitle = taskListView.getSelectionModel().getSelectedItem();
        Task task = currentTaskList.getTaskByTitle(selectedTaskTitle);
        if (task!= null) {
            ScreenHandling.loadFXMLScreen("setTaskDueDateScreen.fxml", "Set Task Due Date: " + selectedTaskTitle, 359, 234, false, this);
        }
    }

    public void loadSetTaskStatusScreen(){
        String selectedTaskTitle = taskListView.getSelectionModel().getSelectedItem();
        Task task = currentTaskList.getTaskByTitle(selectedTaskTitle);
        if (task!= null) {
            ScreenHandling.loadFXMLScreen("setTaskStatusScreen.fxml", "Set Task Status: " + selectedTaskTitle, 359, 234, false, this);
        }
    }

    public void loadSetTaskPriorityScreen(){
        String selectedTaskTitle = taskListView.getSelectionModel().getSelectedItem();
        Task task = currentTaskList.getTaskByTitle(selectedTaskTitle);
        if (task!= null) {
            ScreenHandling.loadFXMLScreen("setTaskPriorityScreen.fxml", "Set Task Priority: " + selectedTaskTitle, 359, 234, false, this);
        }
    }

    public void setTaskTitle(String selectedTaskTitle, String newTitle) {
        Task selectedTask = currentTaskList.getTaskByTitle(selectedTaskTitle);
        if (selectedTask!= null) {
            selectedTask.setTitle(newTitle);
            updateTaskListView(selectedTaskTitle, newTitle);
        }
    }

    public void setTaskDescription(String selectedTaskTitle, String newDescription) {
        Task selectedTask = currentTaskList.getTaskByTitle(selectedTaskTitle);
        if (selectedTask!= null) {
            selectedTask.setDescription(newDescription);
        }
    }

    public void setTaskDueDate(String selectedTaskTitle, LocalDate newDueDate) {
        Task selectedTask = currentTaskList.getTaskByTitle(selectedTaskTitle);
        if (selectedTask!= null) {
            selectedTask.setDueDate(newDueDate);
        }
    }

    public void setTaskStatus(String selectedTaskTitle, Task.TaskStatus newStatus) {
        Task selectedTask = currentTaskList.getTaskByTitle(selectedTaskTitle);
        if (selectedTask!= null) {
            selectedTask.setStatus(newStatus);
        }
    }

    public void setTaskPriority(String selectedTaskTitle, Task.TaskPriority newPriority) {
        Task selectedTask = currentTaskList.getTaskByTitle(selectedTaskTitle);
        if (selectedTask!= null) {
            selectedTask.setPriority(newPriority);
        }
    }


    public boolean taskExists(Task task){
        return currentTaskList.taskExists(task);
    }

    @FXML
    public void loadNewTaskScreen() {
        ScreenHandling.loadFXMLScreen("addTaskScreen.fxml", "Add New Task", 359, 234, false, this );
    }

    public void addTask(Task task){
        currentTaskList.addTask(task);
        taskListView.getItems().add(task.getTitle());
    }


    private void updateTaskListView(String taskTitle, String newTitle){
        ArrayList<String> tasks = (ArrayList<String>) taskListView.getItems();

        for (String task : tasks){
            if (task.equals(taskTitle)){
                notesListView.getItems().remove(task);
                notesListView.getItems().add(newTitle);
            }
        }
    }

    public void clearTasks(){
        for (Task task : currentTaskList.getTasks()){
            if (task.getStatus().toString().equals(Task.TaskStatus.COMPLETED.toString())) {
                currentTaskList.deleteNoteByTitle(task.getTitle());
                taskListView.getItems().remove(task.getTitle());
            }
        }
    }

    public void showContextMenu(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            sortContextMenu.show(sortButton, mouseEvent.getScreenX(), mouseEvent.getScreenY());
        }
    }
    
    public void sortTasksByDueDate(){
        currentTaskList.sortTasksBasedOnDueDate();
        taskListView.getItems().clear();
        for (Task task : currentTaskList.getTasks()){
            taskListView.getItems().add(task.getTitle());
        }
    }
    
    public void sortTasksByPriority(){
        currentTaskList.sortTasksBasedOnPriority();
        taskListView.getItems().clear();
        for (Task task : currentTaskList.getTasks()){
            taskListView.getItems().add(task.getTitle());
        }
    }

    private void populateTaskListView(){
        UserDataSource userSource = new UserDataSource();
        userSource.openConnection();

        User user = JSONHandling.deserializeJSONFile();

        assert user != null;
        if (!user.getTasks().isEmpty()) {
            currentTaskList = user.getTasks();
            for (Task task : currentTaskList.getTasks()){
                taskListView.getItems().add(task.getTitle());
            }
        }else {
            currentTaskList = new TaskList();
        }
        userSource.closeConnection();
    }

    private void saveTaskListContent(){
        UserDataSource userSource = new UserDataSource();
        userSource.openConnection();
        User user = JSONHandling.deserializeJSONFile();
        assert user!= null;
        user.setTasks(currentTaskList);
        JSONHandling.overWriteUserJSONFile(user);

        userSource.closeConnection();
    }



    @FXML
    public void subjectFolderClick(MouseEvent mouseEvent) {
        currentFilePath.setDirectoryToRoot();
        fileListView.getItems().clear();
        searchResults.setVisible(false);
        labelHandling.hideErrorMessages();

        Label clickedLabel = (Label) mouseEvent.getSource();

        String fileName = clickedLabel.getText();
        currentFilePath.changeDirectory(fileName);

        File pathFile = new File(currentFilePath.getCurrentPath());
        ArrayList<File> files = SearchHandling.listFiles(pathFile);

        assert files != null;
        if (!files.isEmpty()) {
            for (File file : files) {
                fileListView.getItems().add(file.getName());
            }
        }
        setFileListViewVisibility(true);
    }


    // Method to handle any file click in the fileListView
    @FXML
    public void onFileClick(){
        String selectedFileName = fileListView.getSelectionModel().getSelectedItem();
        File selectedFile = new File(currentFilePath.getCurrentPath(), selectedFileName);

        searchResults.setVisible(false);

        if (!selectedFileName.isEmpty()) {
            if (selectedFile.isDirectory()) {
                onFolderCLick(selectedFile);
            }else {
                FileHandling.openFile(selectedFile);
            }
        }
    }

    // Method to handle any folder click in the fileListView which is implemented in the onFileClick method
    @FXML
    private void onFolderCLick(File folderToView) {
        fileListView.getItems().clear();

        ArrayList<File> files = SearchHandling.listFiles(folderToView);

        assert files != null;
        for (File file : files) {
            fileListView.getItems().add(file.getName());
        }
        setFileListViewVisibility(true);
        currentFilePath.changeDirectory(folderToView.getName());
    }

    public void onSearchedFileClick() {
        int selectedIndex = searchResults.getSelectionModel().getSelectedIndex();
        ArrayList<File> files = (ArrayList<File>) searchResults.getUserData();

        File selectedFile = files.get(selectedIndex);
        if (selectedFile.isDirectory()){
            onSearchedFolderClick(selectedFile.getPath());
        }else{
            FileHandling.openFile(selectedFile);
        }
        searchResults.setVisible(false);
    }

    private void onSearchedFolderClick(String searchedFolderPath){
        fileListView.getItems().clear();

        File searchedFolder = new File(searchedFolderPath);
        ArrayList<File> files = SearchHandling.listFiles(searchedFolder);

        assert files!= null;
        if (!files.isEmpty()) {
            for (File file : files) {
                fileListView.getItems().add(file.getName());
            }
        }
        setFileListViewVisibility(true);
        currentFilePath.setDirectory(searchedFolderPath);
    }

    public void searchConfirmButton() {
        searchResults.setVisible(false);
        setFileListViewVisibility(false);
        labelHandling.hideErrorMessages();

        searchResults.getItems().clear();
        String userInput = searchBar.getText();

        if (userInput.isEmpty()) {
            labelHandling.showErrorMessage(noUserInputLabel);
        }else{
            File rootDirectory = new File(UserSession.getLoggedInUsername());
            ArrayList<File> results = SearchHandling.find(rootDirectory, userInput);

            if (results.isEmpty()) {
                labelHandling.showErrorMessage(noFileFoundLabel);
            }else {
                for (File file : results) {
                    searchResults.getItems().add(file.getName());
                    searchResults.setVisible(true);
                }
                searchResults.setUserData(results);
            }
        }
    }

    @FXML
    public void fileListViewBackButton(){
        fileListView.getItems().clear();
        currentFilePath.changeDirectory("..");

        File previousFolder = new File(currentFilePath.getCurrentPath());
        ArrayList<File> files = SearchHandling.listFiles(previousFolder);

        assert files != null;
        for (File file : files) {
            String filename = file.getName();
            fileListView.getItems().add(filename);
        }
    }

    private void setFileListViewVisibility(boolean visible) {
        if (visible) {
            fileListView.setVisible(true);
            closeButton.setVisible(true);
            fileListViewBackButton.setVisible(true);
        } else {
            fileListView.setVisible(false);
            closeButton.setVisible(false);
            fileListViewBackButton.setVisible(false);
        }
    }


    @FXML
    public void fileListViewCloseButton() {
        setFileListViewVisibility(false);
    }


    public void renameContextMenuItem(ActionEvent actionEvent) {
    }

    public void uploadFile(ActionEvent actionEvent) {
    }

    public void deleteFile(ActionEvent actionEvent) {
    }

    public void backButton(ActionEvent actionEvent) {
    }

    public void closeButton(ActionEvent actionEvent) {
    }

    public void saveText(ActionEvent actionEvent) {
    }

    public void addButton(ActionEvent actionEvent) {
    }

    public void toDoListClear(ActionEvent actionEvent) {
    }




    public void onNotesListViewClick(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            String noteTitle = notesListView.getSelectionModel().getSelectedItem();
            loadEditNoteScreen(noteTitle);
        }else if (mouseEvent.getClickCount() == 2) {
            String noteTitle = notesListView.getSelectionModel().getSelectedItem();
            Note selectedNote = currentNotesList.getNoteByTitle(noteTitle);
            if (selectedNote != null) {
                showNoteDetails(selectedNote);
            }
        }
    }

    private void loadEditNoteScreen(String selectedNoteTitle) {
        Note note = currentNotesList.getNoteByTitle(selectedNoteTitle);
        if (note!= null) {
            ScreenHandling.loadFXMLScreen("editNoteScreen.fxml", "Edit Note: " + selectedNoteTitle, 359, 234, false, this);
        }
    }

    private void showNoteDetails(Note note) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Note Details");
        alert.setHeaderText("Subject: " + note.getSubject() + "\nTitle: " + note.getTitle());
        alert.setContentText("Content:\n" + note.getContent());
        alert.showAndWait();
    }

    public void loadAddNoteScreen(){
        ScreenHandling.loadFXMLScreen("addNoteScreen.fxml", "Add Note", 359, 234, false, this);
    }

    public void addNote(String subject, String title, String content) {
        currentNotesList.addNote(subject, title, content);
        notesListView.getItems().add(title);
    }

    public void setNoteDetails(String selectedNoteTitle, String newSubject, String newTitle, String newContent) {
        Note selectedNote = currentNotesList.getNoteByTitle(selectedNoteTitle);
        if (selectedNote!= null) {
            selectedNote.setSubject(newSubject);
            selectedNote.setTitle(newTitle);
            selectedNote.setContent(newContent);
            updateNoteListView(selectedNoteTitle, newTitle);
        }
    }

    private void updateNoteListView(String noteTitle, String newTitle){
        ArrayList<String> notes = (ArrayList<String>) notesListView.getItems();

        for (String note : notes){
            if (note.equals(noteTitle)){
                notesListView.getItems().remove(note);
                notesListView.getItems().add(newTitle);
            }
        }
    }

    public void deleteNote(){
        String selectedNoteTitle = notesListView.getSelectionModel().getSelectedItem();
        if (selectedNoteTitle!= null) {
            currentNotesList.deleteNoteByTitle(selectedNoteTitle);
            notesListView.getItems().remove(selectedNoteTitle);
        }
    }

    // Populate the ListView with note titles
    private void populateNoteListView() {
        UserDataSource userSource = new UserDataSource();
        userSource.openConnection();

        User user = JSONHandling.deserializeJSONFile();

        assert user != null;
        if (!user.getNotes().isEmpty()) {
            currentNotesList = user.getNotes();
            Note current = user.getNotes().getHead();
            while (current != null) {
                notesListView.getItems().add(current.getTitle());
                current = current.getNext();
            }
        }else {
            currentNotesList = new NotesList();
        }
        userSource.closeConnection();
    }

    private void saveNotesListContent(){
        UserDataSource userSource = new UserDataSource();
        userSource.openConnection();

        User user = JSONHandling.deserializeJSONFile();
        assert user!= null;
        user.setNotes(currentNotesList);
        JSONHandling.overWriteUserJSONFile(user);

        userSource.closeConnection();
    }

    public boolean noteExists(String noteTitle){
        return currentNotesList.getNoteByTitle(noteTitle) != null;
    }

    @FXML
    public void handleSearchBarEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            searchButton.fire();
        }
    }
}
