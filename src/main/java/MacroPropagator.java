import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.RealVar;
import org.chocosolver.util.ESat;

class MacroPropagator extends Propagator<RealVar> {

    RealVar a, b, c, d, e, f, g, h;
    Ingredient ingredientA, ingredientB, ingredientC, ingredientD,
            ingredientE, ingredientF, ingredientG;

    // Macro bounds set for this recipe

    Double rsLB = 13.0;
    Double rsUB = 13.0;
    Double seLB = 0.0;
    Double seUB = 100.0;
    Double solidsLB = 40.0;
    Double solidsUB = 100.0;
    Double waterLB = 0.0;
    Double waterUB = 100.0;
    Double fatLB = 15.0;
    Double fatUB = 15.0;
    Double saturatedLB = 0.0;
    Double saturatedUB = 100.0;
    Double unsaturatedLB = 0.0;
    Double unsaturatedUB = 100.0;
    Double msnfLB = 11.0;
    Double msnfUB = 11.0;
    Double proteinLB = 0.0;
    Double proteinUB = 100.0;
    Double brixLB = 0.0;
    Double brixUB = 100.0;
    Double acidLB = 0.0;
    Double acidUB = 100.0;

    public MacroPropagator(
            Ingredient ingredientA,
            Ingredient ingredientB,
            Ingredient ingredientC,
            Ingredient ingredientD,
            Ingredient ingredientE,
            Ingredient ingredientF,
            Ingredient ingredientG,
            RealVar a,
            RealVar b,
            RealVar c,
            RealVar d,
            RealVar e,
            RealVar f,
            RealVar g
    ) {
        super();
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.g = g;
        this.ingredientA = ingredientA;
        this.ingredientB = ingredientB;
        this.ingredientC = ingredientC;
        this.ingredientD = ingredientD;
        this.ingredientE = ingredientE;
        this.ingredientF = ingredientF;
        this.ingredientG = ingredientG;
    }

    @Override
    public void propagate(int evtmask) throws ContradictionException {
        a.updateUpperBound(a.getUB(), this);
        b.updateUpperBound(b.getUB(), this);
        c.updateUpperBound(c.getUB(), this);
        d.updateUpperBound(d.getUB(), this);
        e.updateUpperBound(e.getUB(), this);
        f.updateUpperBound(f.getUB(), this);
        g.updateUpperBound(a.getUB(), this);

    }

    @Override
    public ESat isEntailed() {
        return ESat.UNDEFINED;
    }
}
