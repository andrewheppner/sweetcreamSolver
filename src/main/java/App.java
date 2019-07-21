import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.constraints.Operator;
import org.chocosolver.solver.constraints.real.PropScalarMixed;
import org.chocosolver.solver.variables.RealVar;


/**
 * * eg. ingredients = [ "wholeMilk", "40%Cream", "sucrose", "skimMilkPowder", "buttermilk", "tapiocaStarch", "salt", "sugaredEggYolk" ]
 *
 * Each ingredient has a measurement value "totalAmountInRecipe".
 *
 * macroNutrients = ["rs", "se", "solids", "water", "fat", "saturated", "unsaturated", "msnf", "protein", "brix", "acid" ]
 *
 * Each ingredient has a value between 0 - 1 for the following macro-nutrient attributes. ex. wholeMilk is 0.128 solids, 0.872 water
 *
 * Constraints:
 *
 * 1. Sum of [ingredients] = 100
 *
 * 2. May set the "totalAmountInRecipe" as fixed value if applicable.
 *
 * 3. Set min/max/exact values for the macro-nutrient values in the output recipe eg. 15% fat, minimum of 40% solids
 *
 * 4. sum of totalAmountInRecipe * macroNutrient value for that ingredient for EACH INGREDIENT must fall within constraints
 *    set for that macroNutrient in proposed recipe.
 *
 */

public class App {
    public static void main(String[] args) {

        Double MAXIMUM_RECIPE_AMOUNT = 100.0;

        // Set up Ingredients in the recipe

        Ingredient wholeMilk = new Ingredient("wholeMilk", 0.0, 0.0, 0.128, 0.872, 0.037, 0.024, 0.013, 0.091, 0.035,
                0.049, 0.0);
        Ingredient cream = new Ingredient("40%cream", 0.0, 0.0, 0.454, 0.0, 0.547, 0.4, 0.26, 0.14, 0.054, 0.019, 0.0);
        Ingredient sucrose = new Ingredient("sucrose", 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
        Ingredient skimMilkPowder = new Ingredient("skimMilkPowder", 0.0, 0.0, 0.97, 0.0, 0.07, 0.05, 0.02, 0.963,
                0.38, 0.54, 0.0);
        Ingredient buttermilk = new Ingredient("buttermilk", 0.0, 0.0, 0.099,
                0.901, 0.009, 0.005, 0.003, 0.095, 0.033, 0.048, 0.0);
        Ingredient tapiocaStarch = new Ingredient("tapiocaStarch", 0.0, 0.0,
                0.867, 0.133, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        Ingredient salt = new Ingredient("salt", 0.0, 5.9, 1.0, 0.0, 0.0, 0.0
                ,0.0, 0.0, 0.0, 0.0, 0.0);
        Ingredient sugaredEggYolk = new Ingredient("sugaredEggYolk", 0.1, 0.1
                , 0.529, 0.471, 0.239, 0.09, 0.143, 0.0, 0.143, 0.105, 0.0);

        // Create Solver model
        Model model = new Model("Recipe Solver Model");

        // Create variables, these represent the amount of each ingredient in
        // the recipe.

        RealVar amountWholeMilk = model.realVar("amountWholeMilk", 0.0,
                100.0, 0.01);
        RealVar amountCream = model.realVar("amountCream", 0.0, 100.0, 0.01);
        RealVar amountSucrose = model.realVar("amountSucrose", 0.0, 100.0,
                0.01);
        RealVar amountSkimMilkPowder = model.realVar("amountSkimMilkPowder",
                0.0,
                100.0, 0.01);
        RealVar amountButtermilk = model.realVar("amountButtermilk", 0.0,
                100.0, 0.01);
        RealVar amountTapiocaStarch = model.realVar("amountTapiocaStarch", 0.0,
                100.0, 0.01);
        RealVar amountSalt = model.realVar("amountSalt", 0.0, 100.0, 0.01);
        RealVar amountSugaredEggYolk = model.realVar("amountSugaredEggYolk",
                0.0,
                100.0, 0.01);

        // Array of all ingredients in the recipe
        RealVar[] allIngredientsInRecipe = {
                amountWholeMilk,
                amountCream,
                amountSucrose,
                amountSkimMilkPowder,
                amountButtermilk,
                amountTapiocaStarch,
                amountSalt,
                amountSugaredEggYolk,
        };

        // Array of Fat coefficients for these ingredients
        double[] fatCoefficients = {
                wholeMilk.getFat(),
                cream.getFat(),
                sucrose.getFat(),
                skimMilkPowder.getFat(),
                buttermilk.getFat(),
                tapiocaStarch.getFat(),
                salt.getFat(),
                sugaredEggYolk.getFat(),
        };

        double[] rsCoefficients = {
                wholeMilk.getRs(),
                cream.getRs(),
                sucrose.getRs(),
                skimMilkPowder.getRs(),
                buttermilk.getRs(),
                tapiocaStarch.getRs(),
                salt.getRs(),
                sugaredEggYolk.getRs(),
        };

        double[] msnfCoefficients = {
                wholeMilk.getMsnf(),
                cream.getMsnf(),
                sucrose.getMsnf(),
                skimMilkPowder.getMsnf(),
                buttermilk.getMsnf(),
                tapiocaStarch.getMsnf(),
                salt.getMsnf(),
                sugaredEggYolk.getMsnf(),
        };

        double[] solidsCoefficients = {
                wholeMilk.getSolids(),
                cream.getSolids(),
                sucrose.getSolids(),
                skimMilkPowder.getSolids(),
                buttermilk.getSolids(),
                tapiocaStarch.getSolids(),
                salt.getSolids(),
                sugaredEggYolk.getSolids(),
        };

        double[] waterCoefficients = {
                wholeMilk.getWater(),
                cream.getWater(),
                sucrose.getWater(),
                skimMilkPowder.getWater(),
                buttermilk.getWater(),
                tapiocaStarch.getWater(),
                salt.getWater(),
                sugaredEggYolk.getWater(),
        };

        double[] saturatedFatCoefficients = {
                wholeMilk.getSaturated(),
                cream.getSaturated(),
                sucrose.getSaturated(),
                skimMilkPowder.getSaturated(),
                buttermilk.getSaturated(),
                tapiocaStarch.getSaturated(),
                salt.getSaturated(),
                sugaredEggYolk.getSaturated(),
        };

        double[] unsaturatedFatCoefficients = {
                wholeMilk.getUnsaturated(),
                cream.getUnsaturated(),
                sucrose.getUnsaturated(),
                skimMilkPowder.getUnsaturated(),
                buttermilk.getUnsaturated(),
                tapiocaStarch.getUnsaturated(),
                salt.getUnsaturated(),
                sugaredEggYolk.getUnsaturated(),
        };

        double[] proteinCoefficients = {
                wholeMilk.getProtein(),
                cream.getProtein(),
                sucrose.getProtein(),
                skimMilkPowder.getProtein(),
                buttermilk.getProtein(),
                tapiocaStarch.getProtein(),
                salt.getProtein(),
                sugaredEggYolk.getProtein(),
        };

        double[] brixCoefficients = {
                wholeMilk.getBrix(),
                cream.getBrix(),
                sucrose.getBrix(),
                skimMilkPowder.getBrix(),
                buttermilk.getBrix(),
                tapiocaStarch.getBrix(),
                salt.getBrix(),
                sugaredEggYolk.getBrix(),
        };

        double[] acidCoefficients = {
                wholeMilk.getAcid(),
                cream.getAcid(),
                sucrose.getAcid(),
                skimMilkPowder.getAcid(),
                buttermilk.getAcid(),
                tapiocaStarch.getAcid(),
                salt.getAcid(),
                sugaredEggYolk.getAcid(),
        };

        RealVar fatValue = model.realVar("realFat",15.0);
        RealVar rsValue = model.realVar("rsValue", 13.0);
        RealVar msnfValue = model.realVar("msnfValue", 11.0);
        RealVar solidsValue = model.realVar("solidsValue", 40.0, 100.0, 0.01);
        RealVar waterValue = model.realVar("waterValue", 0.0, 100.0, 0.01);
        RealVar saturatedFatValue = model.realVar("saturatedFatValue", 0.0,
                100.0, 0.01);
        RealVar unsaturatedFatValue = model.realVar("unsaturatedFatValue",
                0.0, 100.0,
                0.01);
        RealVar proteinValue = model.realVar("proteinValue", 0.0, 100.0, 0.01);
        RealVar brixValue = model.realVar("brixValue", 0.0, 100.0, 0.01);
        RealVar acidValue = model.realVar("acidValue", 0.0, 100.0, 0.01);

        // Add total measure constraint
        model.realIbexGenericConstraint("{0}+{1}+{2}+{3}+{4}+{5}+{6}+{7}=100.0"
                , allIngredientsInRecipe[0], allIngredientsInRecipe[1],
                allIngredientsInRecipe[2], allIngredientsInRecipe[3],
                allIngredientsInRecipe[4], allIngredientsInRecipe[5],
                allIngredientsInRecipe[6], allIngredientsInRecipe[7]).post();

        model.post(new Constraint("fatConstraint",
                new PropScalarMixed(allIngredientsInRecipe, fatCoefficients,
                        Operator.EQ, 15.0)));

        // Add Fat Constraint
//        model.scalar(allIngredientsInRecipe, fatCoefficients, "=", fatValue).post();

        // Add rs Constraint
//        model.scalar(allIngredientsInRecipe, rsCoefficients, "=", rsValue).post();

        // Add msnf Constraint
//        model.scalar(allIngredientsInRecipe, msnfCoefficients, "=", msnfValue).post();

        // Add Solids Constraint
//        model.scalar(allIngredientsInRecipe, solidsCoefficients, ">=",
//                solidsValue.getLB()).post();

        // Add water constraint
//        model.scalar(allIngredientsInRecipe, waterCoefficients, "<=",
//                waterValue.getUB()).post();

        // Add saturated fat constraint
//        model.scalar(allIngredientsInRecipe, saturatedFatCoefficients, "<=",
//                saturatedFatValue.getUB()).post();

        // Add unsaturated fat constraint
//        model.scalar(allIngredientsInRecipe, unsaturatedFatCoefficients, "<=",
//                unsaturatedFatValue.getUB()).post();

        // Add Protein constraint
//        model.scalar(allIngredientsInRecipe, proteinCoefficients, "<=",
//                proteinValue.getUB()).post();

        // Add brix Constraint
//        model.scalar(allIngredientsInRecipe, brixCoefficients, "<=",
//                brixValue.getUB()).post();

        // Add acid Constraint
//        model.scalar(allIngredientsInRecipe, acidCoefficients, "<=",
//                acidValue.getUB()).post();

        // Add fixed amount Constraints
//        model.arithm(amountButtermilk, "=", 9.0).post();
//        model.arithm(amountTapiocaStarch, "=", 1).post();
//        model.arithm(amountSugaredEggYolk, "=", 7).post();
//        model.arithm(amountSalt, "<", 1).post();


        // create the solver
        Solver solver = model.getSolver();

        // get the solution
        Solution solution = solver.findSolution();

        // print the solution
        if(solution != null){
            System.out.println(solution.toString());
        } else {
            System.out.println("no Solution found");
        }

    }
}
