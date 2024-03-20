package com.nourishnet;

public class Constants {
    

    // VARIABLES 
    public static int numberOfRecipesReturnedDiets = 4; 
    public static int numberOfRecipesReturnedUser = 4;
    public static int scaledImageWidth = 148; //Default 148
    public static int scaledImageHeight = 148; //Default 148

    // FILE PATHS
    public static String userDir = System.getProperty("user.dir");
    public static String tempImagePath = userDir + "/Data/tempImages";
    public static String usersPath = userDir + "/Data/Users";
    public static String content = userDir + "/Data/content";
    public static String recipeImagePath = userDir + "/Data/content/recipeImages";
    public static String dietImagePath = userDir + "/Data/content/dietImages";
    public static String iconsPath = userDir + "/Data/content/icons";

    // Icons for app


    // Icons for recipes
    public static String freeFromIcon = userDir + "/Data/content/icons/freeFrom.png"; 
    public static String glutenFreeIcon = userDir + "/Data/content/icons/glutenFree.png";
    public static String healthyIcon = userDir + "/Data/content/icons/healthy.png";
    public static String cookingLevelIcon = userDir + "/Data/content/icons/level.png";
    public static String servingSizeIcon = userDir + "/Data/content/icons/serves.png";
    public static String timeIcon = userDir + "/Data/content/icons/time.png";
    public static String veganIcon = userDir + "/Data/content/icons/vegan.png";
    public static String vegetarianIcon = userDir + "/Data/content/icons/vegetarian.png";
    
}
