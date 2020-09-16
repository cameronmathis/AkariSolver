# AkariSolver
This is a Java Application that solves Akari Puzzles.

## Table of contents
* [General info](#general-info)
* [Setup](#setup)
* [Features](#features)
* [Status](#status)
* [Language details](#Language-details)
* [Contact](#contact)

## General info
This is a project I decided I wanted to do after a HW assignment from one of my course at Auburn University. I was curious if I could do it, and if so how long it would take (about 2 days).

## Setup
In order to run, clone this repo onto your local machine. <br/><br/>
Create a .txt file of the puzzle in the following format: 
>x <br/>
>y <br/>
>x1 y1 z1 <br/>
>x2 y2 z2 <br/>

Where x is the number of columns, y is the number of rows, x\<i> and y\<i> are the coordinates of the black cells, and z\<i> 
specifies the number of bulbs which must be placed adjacent to its four sides with 5 indicating the absence of a number.
The bottom left of the puzzle should be indexed at (1,1).<br/>
Do not add blank lines at the start or end of file. <br/><br/>
Run the run.sh script. The accepted format is:
>./run.sh [problem file path+name] [solution file path+name]

## Features
Implemented:
* Puzzle file can be read and stored
* Can solve akari puzzles
* Solution file can be created

To-do list:
* Implement cell class instead of using ints
* Fix script for Windows Machines
* Analyze code for potential improvements

## Status
Project is: _finished_

## Language details
Language used: Java </br>
Version used: 11.0.8

## Contact
Created by [@cameronmathis](https://github.com/cameronmathis/) - feel free to contact me!
