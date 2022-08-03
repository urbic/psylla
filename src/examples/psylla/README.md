# Example Psylla programs

## Hello, world
Prints greeting.

* [__`hello.psy`__](hello.psy)

## Asterisks

Prints _n_ × _n_ square filled with spaces and asterisks. The non-negative
integer _n_ is passed in the command line.

* [__`asterisks-chessboard.psy`__](asterisks-chessboard.psy) — spaces and
  asterisks are arranged in the chessboard order
* [__`asterisks-nested-squares.psy`__](asterisks-nested-squares.psy) — spaces
  and asterisks form nested squares
* [__`asterisks-solid-square.psy`__](asterisks-solid-square.psy) — the whole
  square is filled with asterisks

## Echo

Prints all the command-line arguments separated by spaces and finally line feed
character.

* [__`echo.psy`__](echo.psy)

## Cat

Prints the content of the file. The name of the file is passed in the command
line.

* [__`cat.psy`__](cat.psy)

## Factorial

Prints the _n_!. The non-negative integer _n_ is passed in the command line.

* [__`factorial-iterative.psy`__](factorial-iterative.psy) — iterative
  implementation
* [__`factorial-recursive.psy`__](factorial-recursive.psy) — recursive
  implementation
* [__`factorial-stream.psy`__](factorial-stream.psy) — stream-based
  implementation

## Fences balance

Checks if the standard input contains mutually balanced fences of three kinds
(`()`, `[]`, `{}`).

* [__`fences-balance.psy`__](fences-balance.psy)

## Fibonacci numbers

Prints the first _n_ Fibonacci numbers. The non-negative integer _n_ is passed
in the command line.

* [__`fibonacci-binet.psy`__](fibonacci-binet.psy) — calculation using [Binet’s
  formula](https://en.wikipedia.org/wiki/Fibonacci_number#Binet's_formula)
  _(inexact when n > 70)_
* [__`fibonacci-iterative.psy`__](fibonacci-iterative.psy) — iterative
  implementation
* [__`fibonacci-recursive-1.psy`__](fibonacci-recursive-1.psy) — recursive
  implementation № 1
* [__`fibonacci-recursive-2.psy`__](fibonacci-recursive-2.psy) — recursive
  implementation № 2
* [__`fibonacci-recursive-memoize.psy`__](fibonacci-recursive-memoize.psy) —
  fast memoized recursive implementation
* [__`fibonacci-recursive-naïve.psy`__](fibonacci-recursive-naïve.psy) — naïve
  recursive implementation _(very slow!)_
* [__`fibonacci-matrix.psy`__](fibonacci-matrix.psy) — implementation based on
  fast matrix exponentiation

## Tower of Hanoi

Solves the [Tower of Hanoi](https://en.wikipedia.org/wiki/Tower_of_Hanoi)
problem for _n_ discs. The non-negative integer _n_ is passed in the command
line.

* [__`hanoi-recursive.psy`__](hanoi-recursive.psy) — recursive implementation

## Find files

List files in a directory hierarchy. Files or directories are specified in the
command line. The current directory is assumed by default.

* [__`find.psy`__](find.psy)

## Decimal fractions

Prints the decimal periodic representation of the vulgar fraction. The fraction
is passed in the command line in the form _p_`/`_q_.

* [__`fraction-floyd.psy`__](fraction-floyd.psy) — an implementation with the
  cycle detection based on the [Floyd’s Tortoise and Hare
  algorithm](https://en.wikipedia.org/wiki/Cycle_detection#Floyd.27s_Tortoise_and_Hare)
* [__`fraction-naïve.psy`__](fraction-naïve.psy) — naïve implementation _(very
  slow!)_

## Greatest common divisor

Prints the GCD calculated using [Euclid’s
algorithm](https://en.wikipedia.org/wiki/Euclidean_algorithm). Numbers are
passed in the command line.

* [__`gcd-iterative.psy`__](gcd-iterative.psy) — iterative implementation
* [__`gcd-recursive.psy`__](gcd-recursive.psy) — recursive implementation

## Head and tail

* [__`head.psy`__](head.psy)
* [__`tail.psy`__](tail.psy)

## Conway’s Game of Life

Shows the evolution of the cellular automata controlled by the rules of
[Conway’s game of life](https://en.wikipedia.org/wiki/Game_of_Life). Runs in
ANSI terminal. Cursor positioning and color selection are made by issuing
escape sequences.

* [__`life.psy`__](life.psy)

## Pascal triangle

Prints the first _n_ rows of the Pascal triangle. Non-negative integer _n_ is
passed in the command line.

* [__`pascal-triangle.psy`__](pascal-triangle.psy)

## Permutations

Prints all the permutations of the numbers 1 thru _`n`_. Non-negative integer
_n_ is passed in the command line.

* [__`permutations-lexicographical.psy`__](permutations-lexicographical.psy) —
  iterative implementation, generates permutations in lexicographical order
* [__`permutations-recursive.psy`__](permutations-recursive.psy) — recursive
  implementation
* [__`permutations-shuffling.psy`__](permutations-shuffling.psy)

## Compute _π_

Prints the value of _π_ calculated using the [Viète’s
formula](https://en.wikipedia.org/wiki/Vi%C3%A8te%27s_formula).

* [__`pi-viete.psy`__](pi-viete.psy)

## Fast power

Prints the _n_ raised to power of _k_ using the [fast exponentiation
algorithm](https://en.wikipedia.org/wiki/Exponentiation_by_squaring).
Non-negative integer _n_ and _k_ are passed in the command line.

* [__`power-fast-iterative.psy`__](power-fast-iterative.psy) — iterative
  implementation
* [__`power-fast-recursive.psy`__](power-fast-recursive.psy) — recursive
  implementation

## Factorization

Print the prime factors of each integer passed in the command line.

* [__`factor-wheel.psy`__](factor-wheel.psy) — [wheel
  factorization](https://en.wikipedia.org/wiki/Wheel_factorization)

## Prime numbers

Prints the first prime numbers that do not exceed a given _n_. Non-negative
integer _n_ is passed in the command line.

* [__`primes-array-optimized.psy`__](primes-array-optimized.psy)
* [__`primes-bitset-optimized.psy`__](primes-bitset-optimized.psy)
* [__`primes-bitset.psy`__](primes-bitset.psy)
* [__`primes-eratosthenes-array-optimized.psy`__](primes-eratosthenes-array-optimized.psy)
* [__`primes-eratosthenes-array.psy`__](primes-eratosthenes-array.psy) — the
  sieve of Eratosthenes implemented as an array
* [__`primes-eratosthenes-bitarray-optimized.psy`__](primes-eratosthenes-bitarray-optimized.psy)
* [__`primes-eratosthenes-bitset.psy`__](primes-eratosthenes-bitset.psy) — the
  sieve of Eratosthenes implemented as a bitset
* [__`primes-filtering-multicontext.psy`__](primes-filtering-multicontext.psy)
* [__`primes-filtering.psy`__](primes-filtering.psy)
* [__`primes-optimized.psy`__](primes-optimized.psy)
* [__`primes.psy`__](primes.psy)
* [__`primes-regexp.psy`__](primes-regexp.psy)
* [__`primes-sundaram.psy`__](primes-sundaram.psy) — [the sieve of
  Sundaram](https://en.wikipedia.org/wiki/Sieve_of_Sundaram) algorithm
* [__`primes-wheel.psy`__](primes-wheel.psy) — implemented based on [the wheel
  factorization algorithm](https://en.wikipedia.org/wiki/Wheel_factorization)

## Russian multiplication

Prints the product of _m_ and _n_ calculated using the [Russian peasant
multiplication
algorithm](https://en.wikipedia.org/wiki/Ancient_Egyptian_multiplication#Russian_peasant_multiplication).
Non-negative integer _m_ and _n_ are passed in the command line.

* [__`russianmul.psy`__](russianmul.psy)

## Shuffling

Prints the shuffled list of command-line parameters.

* [__`shuffle.psy`__](shuffle.psy) — shuffle using [Fisher—Yates—Durstenfeld
  algorithm](https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle)

## Sorting

Prints the lines from the given file sorted according certain criterion.

* [__`sort-inverse.psy`__](sort-inverse.psy) — sort in inverse lexicographical
  order
* [__`sort-length.psy`__](sort-length.psy) — sort by lengths
* [__`sort.psy`__](sort.psy) — sort in lexicographical order

## Square root

Prints the square root of _x_. Non-negative _x_ is passed in the command line.

* [__`sqrt-bisection-floyd.psy`__](sqrt-bisection-floyd.psy) — bisection method
  with cycle detection using Floyd’s algorithm
* [__`sqrt-hero-iterative.psy`__](sqrt-hero-iterative.psy) — iterative
  implementation of the Newton’s method
* [__`sqrt-hero-recursive.psy`__](sqrt-hero-recursive.psy) — recursive
  implementation of the Newton’s method

## Count lines, words and characters in a file

The name of the file is passed in the command line.

* [__`wc.psy`__](wc.psy)
