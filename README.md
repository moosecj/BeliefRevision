To run the belief revision agent you have to run Main.java, located in the src folder.

You will then be prompted to enter a belief base, however you can also enter one of the following commands:

    agm - this runs the agm test and prints the output
    reset - this prompts the user to start over with a new beleif base
    exit - terminates the program
    help - prints out he syntax for beliefs

When entering a belief base, you have to adhere to the following syntax:

    For a symbol: φ 
    For a not operation: !φ
    For an or operation: φ OR ψ
    For an and operation: φ AND ψ
    For an implication: φ -> ψ
    For a biimplication: φ <-> ψ
    For a paranthesis: (φ)

An example could be 'p, p -> q' or 'p, p -> (q AND r)'
