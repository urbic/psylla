#!/usr/bin/perl
#
# $URL: svn://domestic/54/doc/inf/perl-problems/examples/perl/primes-sundaram.pl $
# $Id: primes-sundaram.pl 656 2012-03-04 23:05:06Z  $
#
use warnings;

my $n=shift or die "$0: Укажите число в командной строке\n";

$n=int(($n-1)/2);

my @sieve=(1..$n);

for(my $i=1; $i<=$n; $i++)
{
	for(my $j=1; $j<=$i; $j++)
	{
		my $k=$i+$j+2*$i*$j;
		$sieve[$k-1]=0 if $k<=$n;
		print "$k\n";
	}
}

print "$_\n" for 2, map {2*$_+1} grep {$_} @sieve;
