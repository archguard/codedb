# Math

## Efficient Java Matrix Library

https://github.com/lessthanoptimal/ejml

Efficient Java Matrix Library (EJML) is a linear algebra library for manipulating real/complex/dense/sparse matrices.
Its design goals are; 1) to be as computationally and memory efficient as possible for both small and large matrices, 
and 2) to be accessible to both novices and experts. These goals are accomplished by dynamically selecting the best 
algorithms to use at runtime, clean API, and multiple interfaces. EJML is free, written in 100% Java and has been 
released under an Apache v2.0 license.

```kotlin
val ejmlVersion = "0.41"
implementation("org.ejml:ejml-kotlin:$ejmlVersion")
implementation("org.ejml:ejml-all:$ejmlVersion")
```

EJML provides three different ways to access the library. This lets the user trade off ease of use for control/complexity. An example of each is shown below. All of which implement Kalman gain function:

```java
mult(H,P,c);
multTransB(c,H,S);
addEquals(S,R);
if( !invert(S,S_inv) ) throw new RuntimeException("Invert failed");
multTransA(H,S_inv,d);
mult(P,d,K);
```

## JavaSMT

https://github.com/sosy-lab/java-smt

JavaSMT is a common API layer for accessing various SMT solvers. The API is optimized for performance (using JavaSMT
has very little runtime overhead compared to using the solver API directly), customizability (features and settings 
exposed by various solvers should be visible through the wrapping layer) and type-safety (it shouldn't be possible to
add boolean terms to integer ones at compile time) sometimes at the cost of verbosity.

```java
// Instantiate JavaSMT with SMTInterpol as backend (for dependencies cf. documentation)
try (SolverContext context = SolverContextFactory.createSolverContext(
        config, logger, shutdownNotifier, Solvers.SMTINTERPOL)) {
  IntegerFormulaManager imgr = context.getFormulaManager().getIntegerFormulaManager();

  // Create formula "a = b" with two integer variables
  IntegerFormula a = imgr.makeVariable("a");
  IntegerFormula b = imgr.makeVariable("b");
  BooleanFormula f = imgr.equal(a, b);

  // Solve formula, get model, and print variable assignment
  try (ProverEnvironment prover = context.newProverEnvironment(ProverOptions.GENERATE_MODELS)) {
    prover.addConstraint(f);
    boolean isUnsat = prover.isUnsat();
    assert !isUnsat;
    try (Model model = prover.getModel()) {
      System.out.printf("SAT with a = %s, b = %s", model.evaluate(a), model.evaluate(b));
    }
  }
}
```

## Stochastic Simulation

https://github.com/umontreal-simul/ssj

SSJ is a Java library for stochastic simulation, developed under the supervision of Pierre L'Ecuyer in
the Simulation and Optimization Laboratory, Department of Computer Science and Operations Researc
h at Université de Montréal. It provides facilities for:



## LogicNG

https://github.com/logic-ng/LogicNG

LogicNG is a Java Library for creating, manipulating and solving Boolean and Pseudo-Boolean formulas. 
It includes 100% Java implementations of popular tools like MiniSAT, Glucose, PBLib, or OpenWBO.

```java
FormulaFactory f = new FormulaFactory();
PropositionalParser p = new PropositionalParser(f);
Formula formula = p.parse("A & ~(B | ~C)");
```