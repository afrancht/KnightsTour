# Knight's Tour in Scala

This is an immutable (no use of ListBuffers, vars...) solution to the Knight's Tour problem. 
A knight tour is a sequence of moves by the Knight which results in the Knights going through
every single board space exactly once. For more information refer to: https://en.wikipedia.org/wiki/Knight%27s_tour

To understand the algorithm you might want to familiarise yourself with the different movement the Knight in chess can make.  

The algorithm is optimized for finding a solution to a board in 360 seconds or less.

##Data Structures and variables
* **Board dimension** is an ```integer``, maximum board size is 40x40 and boards are squares (same horizontal and vertical rows)
* A **position** on the chessboard is a pair of ``integers``. E.g. ``(1,2)`` 
* A **path** is a list of positions. The first position or move number 0, is the last element in this list (the list is in reverse order). The last knights move is the first element in the list.

##Running the code

The code has a main method therefore you can produce an output by navigating to the folder containing the code and running the commands:
```
$ scalac knightTour.scala
$ scala knightTour
```

You can also copy the contents of object knightTour (all the methods) onto a scala REPL which has to be previously installed and can be launched by issuing the command ``$ scala`` in the terminal window. Then you can copy the test cases one by one and view the result.  
 ### Test cases
 ```
 ordered_moves(8, List((3,4), (3,2)), (1,3)) == List((0,1), (0,5), (2,1), (2,5))
 ordered_moves(8, List((4,0)), (0,0)) == List((2,1), (1,2))
 ordered_moves(8, List((0,4)), (0,0)) == List((1,2), (2,1))
 first_closed_tour_heuristic(6, List((3,3)))  == Some(List((5,4), (4,2), (5,0), (3,1), (1,0), (2,2), (3,0), (5,1), (3,2), (1,1), (0,3), (1,5), (3,4), (5,5), (4,3), (3,5), (1,4), (0,2), (2,3), (0,4), (2,5), (4,4), (5,2), (4,0), (2,1), (0,0), (1,2), (2,4), (0,5), (1,3), (0,1), (2,0), (4,1), (5,3), (4,5), (3,3)))
 first_tour_heuristic(8, List((0,0))) == Some(List((6,5), (7,7), (5,6), (4,4), (2,3), (3,5), (1,4), (0,2), (2,1), (4,2), (5,4), (3,3), (5,2), (7,3), (6,1), (4,0), (3,2), (5,3), (3,4), (4,6), (2,7), (0,6), (2,5), (0,4), (1,6), (3,7), (4,5), (2,4), (4,3), (2,2), (1,0), (3,1), (5,0), (7,1), (6,3), (7,5), (6,7), (5,5), (7,4), (6,6), (4,7), (2,6), (0,7), (1,5), (0,3), (1,1), (3,0), (5,1), (7,0), (6,2), (4,1), (6,0), (7,2), (6,4), (7,6), (5,7), (3,6), (1,7), (0,5), (1,3), (0,1), (2,0), (1,2), (0,0)))
  
 Last test case should find a result in under 10 seconds
 first_tour_heuristic(40, List((0,0)))
 ``` 
