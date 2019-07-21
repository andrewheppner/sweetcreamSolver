public class Ingredient {
    String name;
    Double rs;
    Double se;
    Double solids;
    Double water;
    Double fat;
    Double saturated;
    Double unsaturated;
    Double msnf;
    Double protein;
    Double brix;
    Double acid;

    public Ingredient(
            String name,
            Double rs,
            Double se,
            Double solids,
            Double water,
            Double fat,
            Double saturated,
            Double unsaturated,
            Double msnf,
            Double protein,
            Double brix,
            Double acid
    ) {
        this.name = name;
        this.rs = rs;
        this.water = water;
        this.se = se;
        this.solids = solids;
        this.fat = fat;
        this.saturated = saturated;
        this.unsaturated = unsaturated;
        this.msnf = msnf;
        this.protein = protein;
        this.brix = brix;
        this.acid = acid;
    }

    public String getName() {
        return name;
    }

    public Double getRs() {
        return rs;
    }

    public Double getSe() {
        return se;
    }

    public Double getSolids() {
        return solids;
    }

    public Double getWater() {
        return water;
    }

    public Double getFat() {
        return fat;
    }

    public Double getSaturated() {
        return saturated;
    }

    public Double getUnsaturated() {
        return unsaturated;
    }

    public Double getMsnf() {
        return msnf;
    }

    public Double getProtein() {
        return protein;
    }

    public Double getBrix() {
        return brix;
    }

    public Double getAcid() {
        return acid;
    }
}
