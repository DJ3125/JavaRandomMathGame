package com.example.randommathgame_8_27_23;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


//To Do
// Add more padding to choice boxes and their labels to make it look better
//Look at checklist
//Do text wrap on the question label
//Make all the images the same size


public class HelloController { //This function is used for event handling
    //Everything is public and static just in case I need to call these things in a different class
    @FXML VBox InitializationVBox, QuestionsVBox;
    @FXML Button ConfirmChoicesButton, SubmitAnswerButton, WelcomeButton, QuittersButton;
    @FXML TextField UserAnswerTextBox;
    @FXML Label DisplayQuestionLabel, StatisticsLabel, NotificationLabel;
    @FXML Label LabelA1, LabelA2, LabelA3, LabelS1, LabelS2, LabelS3, LabelM1, LabelM2, LabelM3, LabelD1, LabelD2, LabelD3, LabelE1, LabelE2, LabelE3, LabelR1, LabelR2, LabelR3; //All for the image labels
    @FXML ChoiceBox OperationChoiceBox, DifficultyChoiceBox;
    @FXML ImageView ImageA1, ImageA2, ImageA3, ImageS1, ImageS2, ImageS3, ImageM1, ImageM2, ImageM3, ImageD1, ImageD2, ImageD3, ImageE1, ImageE2, ImageE3, ImageR1, ImageR2, ImageR3; // All for the images
    @FXML TabPane MyTabPane;
    @FXML Tab ClassicModeTab, AwardsTab;
    @FXML public void handleWelcome(){ //Occurs when clicking the welcome button. I need it to initialize everything
        //Initialization
        OperationChoiceBox.getItems().addAll(Game.Operations);
        DifficultyChoiceBox.getItems().addAll(Game.Difficulties);
        MyTabPane.setDisable(false);
        MyTabPane.setVisible(true);
        Label[][] ImageLabels = {{LabelA1, LabelA2, LabelA3}, {LabelS1, LabelS2, LabelS3}, {LabelM1, LabelM2, LabelM3}, {LabelD1, LabelD2, LabelD3}, {LabelE1, LabelE2, LabelE3}, {LabelR1, LabelR2, LabelR3}};
        ImageView[][] Images = {{ImageA1, ImageA2, ImageA3}, {ImageS1, ImageS2, ImageS3}, {ImageM1, ImageM2, ImageM3}, {ImageD1, ImageD2, ImageD3}, {ImageE1, ImageE2, ImageE3}, {ImageR1, ImageR2, ImageR3}};
        for(byte i = 0; i<ImageLabels.length; i++){//Arrays allow me to easily loop through and apply a certain computation and set all of them easily
            for(byte j = 0; j < ImageLabels[i].length; j++){
                ImageLabels[i][j].setText("Earn This Badge for completing " + (j+1)*5 + " "+ Game.Operations[i] + " problems");//Looping through also helps simplify the code
                ImageLabels[i][j].setWrapText(true);
                ImageLabels[i][j].setMinHeight(100);
                ImageLabels[i][j].setAlignment(Pos.TOP_CENTER);
                try{
                    FileInputStream image = new FileInputStream("src/main/resources/OtherFiles/QuestionMark.png");
                    Images[i][j].setImage(new Image(image));//I can put a separate array in here because there's always 1 label for every image. If there isn't than I'm doing something wrong
                    Images[i][j].setFitHeight(150);
                }catch(FileNotFoundException e){
                    e.printStackTrace(); //Prints the error without stopping the program
                }
            }
        }
        //When hitting welcome , the selection area appears
        WelcomeButton.setVisible(false);
        WelcomeButton.setDisable(true);
        InitializationVBox.setVisible(true);
        InitializationVBox.setDisable(false);
    }
    @FXML public void handleConfirmation(){ //Occurs when you tell the program the difficulty and operation you want to do
        Game.Running = true;
        for(byte i = 0; i<Game.Operations.length; i++){ // Gets the operation the user selected and turns it into a #. Easier for recursion and asking questions
            if(Game.Operations[i].equals(OperationChoiceBox.getValue())){
                Game.OperationSelected = (byte)(i + 1);
                break; //Saves time and memory, sorta. It's a tiny array to start with
            }
        }
        for(byte i = 0; i<Game.Difficulties.length; i++){ //Does the same thing above but with difficulties. It's sort of like my version of indexOf in java
            if(Game.Difficulties[i].equals(DifficultyChoiceBox.getValue())){
                Game.DifficultySelected = (byte)(i + 1);
                break;
            }
        }
        QuestionsVBox.setVisible(true);
        QuestionsVBox.setDisable(false);
        DisplayQuestionLabel.setText(Game.AskQuestion(Game.OperationSelected)); //Asks questions, hides and disables some VBoxes
        NotificationLabel.setVisible(true);
        NotificationLabel.setDisable(false);
        Tab[] TotalTabs = {ClassicModeTab, AwardsTab};
        for(Tab i: TotalTabs){
            if(!i.isSelected()){
                i.setDisable(true);//Disables all other tabs that aren't selected. If the tab selected is disabled, everything on that tab is disabled
            }
        }
        QuittersButton.setVisible(true);
        QuittersButton.setDisable(false);
        StatisticsLabel.setText("Questions Answered: 0\nQuestions Correct: 0\nQuestions Incorrect: 0\nAccuracy: 0%");

        StatisticsLabel.setVisible(true);
        InitializationVBox.setVisible(false);
        InitializationVBox.setDisable(true);
    }
    @FXML public void handleMouseMove(){
//        For the choice boxes
        ConfirmChoicesButton.setDisable((OperationChoiceBox.getValue() == null)||(DifficultyChoiceBox.getValue() == null)); //If any of the choice boxes don't have a value, then the button isn't activated, otherwise an error would be thrown

    }
    @FXML public void handleCheckUserResponse(){
        if(SubmitAnswerButton.getText().equals("Submit")){
            UserAnswerTextBox.setVisible(false);
            UserAnswerTextBox.setDisable(true);
            try{
                if(Integer.parseInt(UserAnswerTextBox.getText()) == Game.Answer){ //Determining if the user got the question wrong or right
                    DisplayQuestionLabel.setStyle("-fx-text-fill: green");
                    Game.QuestionsCorrect += 1;
                    if(Game.QuestionsCorrect >= 15){
                        DisplayQuestionLabel.setText("Correct! Click continue to go back to the main menu.");
                    }else{
                        DisplayQuestionLabel.setText("Correct!");
                        if(Game.QuestionsCorrect % 5 == 0){
                            Game.DifficultySelected += 1;
                        }
                    }
                    if(Game.QuestionsCorrect % 5 == 0){
                        try{
                            Label[][] ImageLabels = {{LabelA1, LabelA2, LabelA3}, {LabelS1, LabelS2, LabelS3}, {LabelM1, LabelM2, LabelM3}, {LabelD1, LabelD2, LabelD3}, {LabelE1, LabelE2, LabelE3}, {LabelR1, LabelR2, LabelR3}};
                            ImageView[][] Images = {{ImageA1, ImageA2, ImageA3}, {ImageS1, ImageS2, ImageS3}, {ImageM1, ImageM2, ImageM3}, {ImageD1, ImageD2, ImageD3}, {ImageE1, ImageE2, ImageE3}, {ImageR1, ImageR2, ImageR3}};
                            FileInputStream image = new FileInputStream("src/main/resources/OtherFiles/"+Game.Operations[Game.OperationSelected - 1]+"Pics/" + Game.Operations[Game.OperationSelected - 1].charAt(0) + ((byte)Math.floor((float)(Game.QuestionsCorrect/5))) + ".png");
                            Images[Game.OperationSelected-1][(byte)Math.floor((float)(Game.QuestionsCorrect/5)) - 1].setImage(new Image(image));//I can put a separate array in here because there's always 1 label for every image. If there isn't than I'm doing something wrong
                            ImageLabels[Game.OperationSelected-1][(byte)Math.floor((float)(Game.QuestionsCorrect/5)) - 1].setText("Congrats! You got this badge for answering " + Game.QuestionsCorrect + " " +Game.Operations[Game.OperationSelected - 1] + " questions correctly!");
                        }catch(Exception FileNotFoundException){
                            System.out.println("File Not Found Exception");
                        }
                    }
                }else{
                    DisplayQuestionLabel.setText("Incorrect, the answer is: " + Game.Answer);
                    DisplayQuestionLabel.setStyle("-fx-text-fill: red");
                    Game.QuestionsIncorrect += 1;
                }
            }catch(Exception NumberFormatException){ //Just in case the user types in a non-numerical value
                DisplayQuestionLabel.setText("Incorrect, the answer is: " + Game.Answer);
                DisplayQuestionLabel.setStyle("-fx-text-fill: red");
                Game.QuestionsIncorrect += 1;
            }
            Game.QuestionsAnswered += 1;
            StatisticsLabel.setVisible(true);
            double accuracy = Math.round(((float)Game.QuestionsCorrect)/((float)Game.QuestionsAnswered) * 1000)/10.0;
            StatisticsLabel.setText("Questions Answered: " + Game.QuestionsAnswered + "\nQuestions Correct: " + Game.QuestionsCorrect + "\nQuestions Incorrect: " + Game.QuestionsIncorrect + "\nAccuracy: " + accuracy + "%");
            SubmitAnswerButton.setText("Continue");
            QuittersButton.setVisible(false);
            QuittersButton.setDisable(true);
            //Remember to subtract a certain amount from difficulty selected or just reset it if the user completes or quits respectively
        }else if(SubmitAnswerButton.getText().equals("Continue")){
            DisplayQuestionLabel.setStyle("-fx-text-fill: black");
            UserAnswerTextBox.setText("");
            SubmitAnswerButton.setText("Submit");
            if(Game.QuestionsCorrect >= 15){ //Resets all and returns to main menu after 15 questions answered correctly
                ResetGame();//Resets game when certain amount of questions are answered
            }else{ //Continues asking questions otherwise
                DisplayQuestionLabel.setText(Game.AskQuestion(Game.OperationSelected));
                QuittersButton.setVisible(true);
                QuittersButton.setDisable(false);
            }
            UserAnswerTextBox.setVisible(true);
            UserAnswerTextBox.setDisable(false);
        }
    }
    @FXML public void handleAskQuit(){
        //Work on quitting button
        ResetGame();//Resets game if the user quits
//        Stage QuitWindow = new Stage();
    }

    public void ResetGame(){//resets game, preparing for the next time the user wants to play a game
        Game.Running = false;
        DifficultyChoiceBox.setValue(null);
        OperationChoiceBox.setValue(null);
        Game.QuestionsCorrect = Game.QuestionsIncorrect = Game.QuestionsAnswered = Game.OperationSelected = Game.DifficultySelected = 0;
        InitializationVBox.setVisible(true);
        InitializationVBox.setDisable(false);
        QuestionsVBox.setVisible(false);
        QuestionsVBox.setDisable(true);
        StatisticsLabel.setVisible(false);
        QuittersButton.setVisible(false);
        QuittersButton.setDisable(true);
        NotificationLabel.setDisable(true);
        NotificationLabel.setVisible(false);

        Tab[] TotalTabs = {ClassicModeTab, AwardsTab};
        for(Tab i: TotalTabs){
            i.setDisable(false);
        }
    }
}