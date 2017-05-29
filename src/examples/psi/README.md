# Example Ψ scripts

## Asterisks

Prints _n_ × _n_ square filled with spaces and asterisks. The non-negative
integer _n_ is passed in the command line.

* [__`asterisks-chessboard.psi`__](asterisks-chessboard.psi) — spaces and asterisks are arranged in the chessboard order
* [__`asterisks-nested-squares.psi`__](asterisks-nested-squares.psi) — spaces and asterisks form nested squares
* [__`asterisks-solid-square.psi`__](asterisks-solid-square.psi) — the whole square is filled with asterisks

## Cat

Prints the content of the file. The name of the file is passed in the command
line.

* [__`cat.psi`__](cat.psi)

## Factorial

Prints the _n_!. The non-negative integer _n_ is passed in the command line.

* [__`factorial-iterative.psi`__](factorial-iterative.psi) — iterative implementation
* [__`factorial-recursive.psi`__](factorial-recursive.psi) — recursive implementation

## Fibonacci numbers

Prints the first _n_ Fibonacci numbers. The non-negative integer _n_ is passed
in the command line.
 
* [__`fibonacci-binet.psi`__](fibonacci-binet.psi) — calculation using Binet’s formula _(inexact when_ _n_ > 70​_)_
* [__`fibonacci-iterative.psi`__](fibonacci-iterative.psi) — iterative implementation
* [__`fibonacci-recursive-1.psi`__](fibonacci-recursive-1.psi) — recursive implementation № 1
* [__`fibonacci-recursive-2.psi`__](fibonacci-recursive-2.psi) — recursive implementation № 2
* [__`fibonacci-recursive-memoize.psi`__](fibonacci-recursive-memoize.psi) — fast memoized recursive implementation
* [__`fibonacci-recursive-naïve.psi`__](fibonacci-recursive-naïve.psi) — naïve recursive implementation _(very slow!)_
* [__`fibonacci-matrix.psi`__](fibonacci-matrix.psi) — implementation based on fast matrix exponentiation

## Find files

* [__`find.psi`__](find.psi)

## Decimal fractions

Prints the decimal periodic representation of the vulgar fraction. The fraction
is passed in the command line in the form _p_`/`_q_.

* [__`fraction-floyd.psi`__](fraction-floyd.psi) — an implementation with the cycle detection based on the Floyd’s algorithm
* [__`fraction-naïve.psi`__](fraction-naïve.psi) — naïve implementation _(very slow!)_

## Greatest common divisor

Prints the GCD calculated using Euclid’s algorithm.

* [__`gcd-iterative.psi`__](gcd-iterative.psi) — iterative implementation
* [__`gcd-recursive.psi`__](gcd-recursive.psi) — recursive implementation

## Head and tail

* [__`head.psi`__](head.psi)
* [__`tail.psi`__](tail.psi)

## Conway’s Game of Life

Shows the evolution of the cellular automata controlled by the rules of
[Conway’s game of life](https://en.wikipedia.org/wiki/Game_of_Life). Runs in
ANSI terminal. Cursor positioning and color selection are made by issuing
escape sequences.

* [__`life.psi`__](life.psi`)

## Pascal triangle

Prints the first _n_ rows of the Pascal triangle. Non-negative integer _n_ is
passed in the command line.

* [__`pascal-triangle.psi`__](pascal-triangle.psi)

## Permutations

Prints all the permutations of the numbers 1 thru _`n`_. Non-negative integer
_n_ is passed in the command line.

* [__`permutations-lexicographical.psi`__](permutations-lexicographical.psi) — iterative implementation, generates permutations in lexicographical order
* [__`permutations-recursive.psi`__](permutations-recursive.psi) — recursive implementation
* [__`permutations-shuffling.psi`__](permutations-shuffling.psi)

## Compute _π_

Prints the value of _π_ calculated using the [Viète’s
formula](https://en.wikipedia.org/wiki/Vi%C3%A8te%27s_formula).

* [__`pi-viete.psi`__](pi-viete.psi)

## Fast power

Prints the _n_ raised to power of _k_ using the [fast exponentiation algorithm](https://en.wikipedia.org/wiki/Exponentiation_by_squaring). Non-negative integer _n_ and _k_ are passed in the command line.

* [__`power-fast-iterative.psi`__](power-fast-iterative.psi) — iterative implementation
* [__`power-fast-recursive.psi`__](power-fast-recursive.psi) — recursive implementation

## Prime numbers

Prints the first prime numbers that do not exceed a given _n_. Non-negative integer _n_ is passed in the command line.

* [__`primes-array-optimized.psi`__](primes-array-optimized.psi)
* [__`primes-bitset-optimized.psi`__](primes-bitset-optimized.psi)
* [__`primes-bitset.psi`__](primes-bitset.psi)
* [__`primes-eratosthenes-array-optimized.psi`__](primes-eratosthenes-array-optimized.psi)
* [__`primes-eratosthenes-array.psi`__](primes-eratosthenes-array.psi) — the sieve of Eratosthenes implemented as an array
* [__`primes-eratosthenes-bitarray-optimized.psi`__](primes-eratosthenes-bitarray-optimized.psi)
* [__`primes-eratosthenes-bitset.psi`__](primes-eratosthenes-bitset.psi) — the sieve of Eratosthenes implemented as a bitset
* [__`primes-filtering-multicontext.psi`__](primes-filtering-multicontext.psi)
* [__`primes-filtering.psi`__](primes-filtering.psi)
* [__`primes-optimized.psi`__](primes-optimized.psi)
* [__`primes.psi`__](primes.psi)
* [__`primes-regexp.psi`__](primes-regexp.psi)
* [__`primes-sundaram.psi`__](primes-sundaram.psi) — [the sieve of Sundaram](https://en.wikipedia.org/wiki/Sieve_of_Sundaram) algorithm
* [__`primes-wheel.psi`__](primes-wheel.psi) — implemented based on [the wheel factorization algorithm](https://en.wikipedia.org/wiki/Wheel_factorization)

## Russian multiplication

Prints the product of _m_ and _n_ calculated using the [russian peasant
multiplication
algorithm](https://en.wikipedia.org/wiki/Ancient_Egyptian_multiplication#Russian_peasant_multiplication).
Non-negative integer _m_ and _n_ are passed in the command line.

* [__`russianmul.psi`__](russianmul.psi)

## Shuffling

Prints the shuffled list of command-line parameters.

* [__`shuffle.psi`__](shuffle.psi) — shuffle using [Fisher—Yates—Durstenfeld algorithm](https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle)

## Sorting

Prints the lines from the given file sorted according certain criterion.

* [__`sort-inverse.psi`__](sort-inverse.psi) — sort in inverse lexicographical order
* [__`sort-length.psi`__](sort-length.psi) — sort by lengths
* [__`sort.psi`__](sort.psi) — sort in lexicographical order

## Square root

Prints the square root of _x_. Non-negative _x_ is passed in the command line. 

* [__`sqrt-bisection-floyd.psi`__](sqrt-bisection-floyd.psi) — bisection method with cycle detection using Floyd’s algorithm
* [__`sqrt-hero-iterative.psi`__](sqrt-hero-iterative.psi) — iterative implementation of the Newton’s method
* [__`sqrt-hero-recursive.psi`__](sqrt-hero-recursive.psi) — recursive implementation of the Newton’s method

## Count lines, words and characters in a file

The name of the file is passed in the command line.

* [__`wc.psi`__](wc.psi)
