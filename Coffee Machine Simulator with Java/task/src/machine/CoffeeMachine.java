package machine;

import java.util.Scanner;

public class CoffeeMachine {
    public static void main(String[] args) {
        sixQuestion();
    }

    public static void sixQuestion(){
        Scanner scanner = new Scanner(System.in);
        Machine machine = new Machine(400,540,120,9,550);
        machine.summary();
        while(true) {
            System.out.println("Write action (buy, fill, take, clean, remaining, exit):");
            String action = scanner.nextLine();
            OPTION value = OPTION.valueOf(action.toUpperCase());
            switch (value) {
                case BUY:
                    if(machine.getMade()>10) {
                        System.out.println("I need cleaning!");
                        break;
                    }
                    buyOption(machine);
                    break;
                case FILL:
                    fillOption(machine);
                    break;
                case TAKE:
                    System.out.println("I gave you $" + machine.getMoney());
                    machine.decreaseMoney(machine.getMoney());
                    break;
                case CLEAN:
                    System.out.println("I have been cleaned!");
                    machine.setMade(0);
                    break;
                case REMAINING:
                    machine.summary();
                    break;
                case EXIT:
                    System.exit(0);
            }
        }
    }
    public static void buyOption(Machine machine){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:, back - to main menu ");
        String buychoice = scanner.nextLine();
        int[] resourceMultiple = new int[3];
        switch (buychoice){
            case "1":
                Coffee espresso = new Coffee("espresso");
                resourceMultiple = canMakeMultiple(machine, espresso);
                if(resourceMultiple[0]>=1 && resourceMultiple[1]>=1 && resourceMultiple[2]>=1 ) {
                    System.out.println("I have enough resources, making you a coffee!");
                    machine.makeEspresso();
                }
                else if(resourceMultiple[0]<0){
                    System.out.println("Sorry, not enough water!");
                }
                else if(resourceMultiple[0]<0){
                    System.out.println("Sorry, not enough milk!");
                }else{
                    System.out.println("Sorry, not enough coffee beans!");
                }
                break;
            case "2":
                Coffee latte = new Coffee("latte");
                resourceMultiple = canMakeMultiple(machine, latte);
                if(resourceMultiple[0]>=1 && resourceMultiple[1]>=1 && resourceMultiple[2]>=1 ) {
                    System.out.println("I have enough resources, making you a coffee!");
                    machine.makeLatte();
                }
                else if(resourceMultiple[0]<0){
                    System.out.println("Sorry, not enough water!");
                }
                else if(resourceMultiple[0]<0){
                    System.out.println("Sorry, not enough milk!");
                }else{
                    System.out.println("Sorry, not enough coffee beans!");
                }
                break;
            case "3":
                Coffee cappuccino = new Coffee("cappucino");
                resourceMultiple = canMakeMultiple(machine, cappuccino);
                if(resourceMultiple[0]>=1 && resourceMultiple[1]>=1 && resourceMultiple[2]>=1 ) {
                    System.out.println("I have enough resources, making you a coffee!");
                    machine.makeCappuccino();
                }
                else if(resourceMultiple[0]<0){
                    System.out.println("Sorry, not enough water!");
                }
                else if(resourceMultiple[0]<0){
                    System.out.println("Sorry, not enough milk!");
                }else{
                    System.out.println("Sorry, not enough coffee beans!");
                }
                break;
            case "back":
                break;
            default:
                break;
        }
    }
    public static void fillOption(Machine machine){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water you want to add:");
        machine.increaseWater(scanner.nextInt());
        System.out.println("Write how many ml of milk you want to add:");
        machine.increaseMilk(scanner.nextInt());
        System.out.println("Write how many grams of coffee beans you want to add:");
        machine.increaseCoffeeBean(scanner.nextInt());
        System.out.println("Write how many disposable cups you want to add:");
        machine.increaseCups(scanner.nextInt());
        machine.summary();
    }
    public enum OPTION{
        BUY,
        FILL,
        TAKE,
        CLEAN,
        REMAINING,
        EXIT
    }

    public static int[] canMakeMultiple(Machine machine, Coffee drink){
        int[] makeMultiple = new int[3];
        makeMultiple[0]= machine.getWater()/drink.getWater();
        makeMultiple[1] = machine.getMilk()/ drink.getMilk(); //need to avoid division by zero
        makeMultiple[2] = machine.getCoffeeBean()/drink.getCoffeeBean();
        return makeMultiple;
    }

    private static int findSmallest(int a, int b, int c){
        if(a <= b && a <= c){
            return a;
        }else if(b <= a && b <= c){
            return b;
        }else {
            return c;
        }
    }
}


class Coffee{
    protected int water;
    protected int milk;
    protected int coffeeBean;
    protected int price;
    String name;

    public Coffee(String choice) {
        switch (choice) {
            case "espresso":
                this.water = 250;
                this.milk = 1; //need to avoid division by 0
                this.coffeeBean = 16;
                this.price = 4;
                break;
            case "latte":
                this.water = 350;
                this.milk = 75;
                this.coffeeBean = 20;
                this.price = 7;
                break;
            case "cappuccino":
                this.water = 200;
                this.milk = 100;
                this.coffeeBean = 12;
                this.price = 6;
                break;
        }
    }
    public int getWater(){
        return water;
    }
    public int getMilk(){
        return milk;
    }
    public int getCoffeeBean(){
        return coffeeBean;
    }
}


class Machine{
    private int water;
    private int milk;
    private int coffeeBean;
    private int cups;
    private int money;
    private int made;
    public Machine(int water, int milk, int coffeeBean, int cups, int money){
        this.water = water;
        this.milk = milk;
        this.coffeeBean = coffeeBean;
        this.cups = cups;
        this.money = money;
        this.made = 0;
    }

    public void makeEspresso(){
        Coffee espresso = new Coffee("espresso");
        bundleUpdate(espresso);
    }

    public void makeLatte(){
        Coffee latte = new Coffee("latte");
        decreaseWater(latte.water);
        bundleUpdate(latte);
    }

    public void makeCappuccino(){
        Coffee cappuccino = new Coffee("cappuccino");
        bundleUpdate(cappuccino);
    }

    public void decreaseWater(int water){
        this.water -= water;
    }
    public void increaseWater(int water){
        this.water += water;
    }
    public int getWater(){
        return water;
    }
    public void decreaseMilk(int milk){
        this.milk -= milk;
    }
    public void increaseMilk(int milk){
        this.milk += milk;
    }
    public int getMilk(){
        return milk;
    }
    public void decreaseCoffeeBean(int coffeeBean){
        this.coffeeBean -= coffeeBean;
    }
    public void increaseCoffeeBean(int coffeeBean){
        this.coffeeBean += coffeeBean;
    }
    public int getCoffeeBean(){
        return coffeeBean;
    }
    public void decreaseCups(){
        this.cups--;
    }
    public void increaseCups(int cups){
        this.cups += cups;
    }
    public void increaseMoney(int money){
        this.money += money;
    }
    public void decreaseMoney(int money){
        this.money -= money;
    }
    public int getMoney(){
        return money;
    }
    public void increaseMade(){
        this.made++;
    }
    public int getMade(){
        return this.made;
    }
    public void setMade(int made){
        this.made = made;
    }
    public void bundleUpdate(Coffee coffee){
        decreaseWater(coffee.water);
        decreaseMilk(coffee.milk);
        decreaseCoffeeBean(coffee.coffeeBean);
        decreaseCups();
        increaseMoney(coffee.price);
        increaseMade();
    }
    public void summary(){
        String summary =
                """
                The coffee machine has:
                %d ml of water
                %d ml of milk
                %d g of coffee beans
                %d disposable cups
                $%d of money
                """;
        System.out.println(String.format(summary,water,milk,coffeeBean,cups,money));
    }
}