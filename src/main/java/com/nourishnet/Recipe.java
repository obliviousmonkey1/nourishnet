package com.nourishnet;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Recipe {

    private String name; 
    private ArrayList<String> diet;
    private int prepTime;
    private int cookTime;
    private String level;
    private int serves;
    private Nutrition nutrition;
    private String description;
    private ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    private ArrayList<String> instructions = new ArrayList<String>();
    private ArrayList<String> tags = new ArrayList<String>();

    @JsonCreator
    public Recipe(
            @JsonProperty("name") String name,
            @JsonProperty("diet") ArrayList<String> diet,
            @JsonProperty("prepTime") int prepTime,
            @JsonProperty("cookTime") int cookTime,
            @JsonProperty("level") String level,
            @JsonProperty("serves") int serves,
            @JsonProperty("nutrition") Nutrition nutrition,
            @JsonProperty("description") String description,
            @JsonProperty("ingredients") ArrayList<Ingredient> ingredients,
            @JsonProperty("instructions") ArrayList<String> instructions,
            @JsonProperty("tags") ArrayList<String> tags
    ) {
        this.name = name;
        this.diet = diet;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.level = level;
        this.serves = serves;
        this.nutrition = nutrition;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.tags = tags;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getDiet() {
        return this.diet;
    }

    public int getPrepTime() {
        return this.prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getServes() {
        return this.serves;
    }

    public void setServes(int serves) {
        this.serves = serves;
    }

    public Nutrition getNutrition() {
        return this.nutrition;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public ArrayList<String> getInstructions() {
        return this.instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    @JsonIgnore
    public String generateImageName(){
        String output = "";
        String thing = this.name.toLowerCase();

        for(int i =0 ; i< thing.length();i++){
            if (thing.charAt(i) == ' '){
                output += '-';
            } else if (thing.charAt(i) == ','){
            } else {
                output += thing.charAt(i);
            }
        }
        
        return output;
    }

    public static class Nutrition {
        private int calories;
        private int fat;
        private int saturates;
        private int carbs;
        private int sugars;
        private int fibre;
        private int protein;
        private int salt;


        @JsonCreator
        public Nutrition(
                @JsonProperty("calories") int calories,
                @JsonProperty("fat") int fat,
                @JsonProperty("saturates") int saturates,
                @JsonProperty("carbs") int carbs,
                @JsonProperty("sugars") int sugars,
                @JsonProperty("fibre") int fibre,
                @JsonProperty("protein") int protein,
                @JsonProperty("salt") int salt
        ) {
            this.calories = calories;
            this.fat = fat;
            this.saturates = saturates;
            this.carbs = carbs;
            this.sugars = sugars;
            this.fibre = fibre;
            this.protein = protein;
            this.salt = salt;
        }

        public int getCalories() {
            return this.calories;
        }

        public int getFat() {
            return this.fat;
        }

        public int getSaturates() {
            return this.saturates;
        }

        public int getProtein() {
            return this.protein;
        }

        public int getSalt() {
            return this.salt;
        }   

        public int getCarbs() {
            return this.carbs;
        }

        public int getSugars() {
            return this.sugars;
        }

        public int getFibre() {
            return this.fibre;
        }

    }

    public static class Ingredient {
        private String name;
        private int quantity; 
        private String measurement;

     
        @JsonCreator
        public Ingredient(@JsonProperty("name") String name, @JsonProperty("quantity") int quantity, @JsonProperty("measurement") String measurement) {
            this.name = name;
            this.quantity = quantity;
            this.measurement = measurement;
        }


        public String getIngredientName(){
            return this.name;
        }

        public Integer getQuantity() {
            return this.quantity;
        }

        public String getMeasurement() {
            return this.measurement;
        }

    }
}
