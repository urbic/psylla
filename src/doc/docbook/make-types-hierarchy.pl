#!/usr/bin/perl

use warnings;
use IO::Handle;
use Getopt::Long;
use File::Basename;

my $outputDir='.';
GetOptions 'O|output-directory=s'=>\$outputDir;

my $inputFile=shift;

my %h;
open my $input, '<', $inputFile;
while(<$input>)
{
	chomp;
	my ($head, $tail)=split /:\s+/;
	$h{$head}=[sort split /\s+/, $tail];
}

for my $head(sort keys %h)
{
	my @agenda=($head);
	my %processed;
	$baseName=basename($inputFile, '.txt');
	open my $dot, '|-', "dot -Tsvg -o \"$outputDir/${baseName}_${head}.svg\"";
	$dot->print(<<__DOT__);
digraph $head
{
	graph
	[
		bgcolor="none",
		resolution="60",
	]
	node
	[
		shape="note",
		style="filled",
		fillcolor="linen",
		fontname="monospace",
		href="PsyllaReference_Types.xhtml#PsyllaReference_Types_Details_\\N",
		target="_parent"
	]
__DOT__
	while(@agenda)
	{
		my $rhs=shift @agenda;
		next if exists $processed{$rhs};
		$processed{$rhs}++;
		next unless defined $h{$rhs};
		push @agenda, @{$h{$rhs}};
		my $lhs=join ', ', @{$h{$rhs}};
		$dot->print("\t$lhs -> $rhs;\n");
	}
	$dot->print("\t$head [fillcolor=\"wheat\"]\n");
	$dot->print("}\n\n");
	$dot->close;
}

{
	my @agenda=(sort keys %h);
	my %processed;
	$baseName=basename($inputFile, '.txt');
	open my $dot, '|-', "dot -Tsvg -o \"$outputDir/${baseName}.svg\"";
	$dot->print(<<__DOT__);
digraph "Psylla Types Hierarchy"
{
	graph
	[
		bgcolor="none"
	]
	node
	[
		shape="note",
		style="filled",
		fillcolor="linen",
		fontname="monospace"
	]
__DOT__
	while(@agenda)
	{
		my $rhs=shift @agenda;
		next if exists $processed{$rhs};
		print "[$rhs]\n";
		$processed{$rhs}++;
		next unless defined $h{$rhs};
		push @agenda, @{$h{$rhs}};
		my $lhs=join ', ', @{$h{$rhs}};
		$dot->print("\t$lhs -> $rhs;\n");
	}
	$dot->print("}\n\n");
	$dot->close;
}
