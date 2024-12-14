package com.example.randommathgame_8_27_23;

public class Game {//This function is used for actually doing stuff, like computations and such
    public static final String[] Operations = {"Addition", "Subtraction", "Multiplication", "Division", "Exponents", "Random"}; //List of all possible operations
    public static final String[] Difficulties = {"Easy", "Medium", "Hard", "Insane"}; //List of all possible difficulties
    public static boolean Running = false;
    public static int Answer = 0;
    public static byte QuestionsAnswered = 0, QuestionsCorrect = 0, QuestionsIncorrect = 0; //It's only a byte, but nobody is going to answer more than 128 questions, right? Right?!
    public static byte DifficultySelected, OperationSelected; // 1= addition, 2= subtraction, 3 = multiplication, 4 = division, etc. Same for difficulties, except in order of least difficult to most
    public static String AskQuestion(byte operation){//Parameter is used just in case for recursion, which happens when the user picks random as the operation
        if(operation < 4){ //Only includes addition, subtraction, and addition
            int Number1 = (int)(Math.round(Math.random()*10 * Math.pow(3.5, DifficultySelected - 1))*Math.cos(Math.PI * Math.round(Math.random()))); //Calculating numbers based off of difficulty
            int Number2 = (int)(Math.round(Math.random()*10 * Math.pow(3.5, DifficultySelected - 1))*Math.cos(Math.PI * Math.round(Math.random())));
            String[] OperationsArray = {"+", "-", "*"};
            if(operation == 3){
                Number1 = (int) (Number1/Math.pow(1.5, DifficultySelected - 1));
                Number2 = (int) (Number2/Math.pow(1.5, DifficultySelected - 1)); //Making the numbers smaller if it's multiplication, so it's still manageable
            }
            Answer = (operation == 1) ? (Number1 + Number2) : ((operation == 2) ? (Number1 - Number2) : (Number1*Number2)); //Shorthand if elseif else. Used to set the answer
            return ("What is " + Number1 + " " + OperationsArray[operation - 1] + " " + Number2); //Prints out on a label when returned
        }else if(operation == 4) {//Multiplication
            int Number1 = (int) (Math.round(Math.random() * 10 * Math.pow(1.5, DifficultySelected)) * Math.cos(Math.PI * Math.round(Math.random())));
            Number1 = (Number1 == 0) ? Number1 + 1 : Number1;
            int Number2 = Number1 * ((int) (Math.round(Math.random() * Math.pow(1.5, DifficultySelected)) * Math.cos(Math.PI * Math.round(Math.random())))); // Just creating the numbers for the division, so it divides nicely
            Answer = Number2 / Number1;
            return ("What is " + Number2 + " / " + Number1); //Prints out on a label when returned
        }else if(operation == 5){//Exponents
            byte Number1 = (byte) Math.floor(Math.random() * 21);
            byte Number2 = (byte) Math.floor(Math.random() * (3.5*DifficultySelected + 1));
            Answer = (int) Math.pow(Number1, Number2);
            return ("What is " + Number1 + " ^ " + Number2);
        }else{ //This is if the user selected random
            return AskQuestion(((byte)Math.floor(Math.random() * (Operations.length - 1 ) + 1)));//Subtracts one from length because we don't want it to select random again
        }
    }
}