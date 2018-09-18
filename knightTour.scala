
object knightTour {

  type Pos = (Int, Int)    // a position on a chessboard
  type Path = List[Pos]    // a path (a list of positions)


  // Function that tests whether the position is inside the board and not yet element in the path.
  def is_legal(dim: Int, path: Path)(x: Pos) : Boolean = if ( x._1 >= 0 && x._2 >= 0  && x._1 < dim && x._2 < dim && !(path.contains(x))  ) true else false

  def clock_one(dim: Int, path: Path, x: Pos) : List[Pos] = if ( is_legal(dim,path)(x._1+1,x._2+2) ) List( (x._1+1,x._2+2) ) else Nil

  def clock_two(dim: Int, path: Path, x: Pos) : List[Pos] = if ( is_legal(dim,path)(x._1+2,x._2+1) ) List( (x._1+2,x._2+1) ) else Nil

  def clock_three(dim: Int, path: Path, x: Pos) : List[Pos] = if ( is_legal(dim,path)(x._1+2,x._2-1) ) List( (x._1+2,x._2-1) ) else Nil

  def clock_four(dim: Int, path: Path, x: Pos) : List[Pos] = if ( is_legal(dim,path)(x._1+1,x._2-2) ) List( (x._1+1,x._2-2) ) else Nil

  def clock_five(dim: Int, path: Path, x: Pos) : List[Pos] = if ( is_legal(dim,path)(x._1-1,x._2-2) ) List( (x._1-1,x._2-2) ) else Nil

  def clock_six(dim: Int, path: Path, x: Pos) : List[Pos] = if ( is_legal(dim,path)(x._1-2,x._2-1) ) List( (x._1-2,x._2-1) ) else Nil

  def clock_seven(dim: Int, path: Path, x: Pos) : List[Pos] = if ( is_legal(dim,path)(x._1-2,x._2+1) ) List( (x._1-2,x._2+1) ) else Nil

  def clock_eight(dim: Int, path: Path, x: Pos) : List[Pos] = if ( is_legal(dim,path)(x._1-1,x._2+2) ) List( (x._1-1,x._2+2) ) else Nil

  // Function that calculates for a all legal onward moves that are not already in the path position. Moves are ordered
  // clockwise (first one is one up two to the right).
  def legal_moves(dim: Int, path: Path, x: Pos) : List[Pos] = {

  clock_one(dim,path,x) ::: clock_two(dim,path,x) ::: clock_three(dim,path,x) ::: clock_four(dim,path,x) ::: clock_five(dim,path,x) ::: clock_six(dim,path,x) ::: clock_seven(dim,path,x) ::: clock_eight(dim,path,x)
  }

  // Function that calculates a list of onward moves like in but orders them according to Warnsdorf’s rule. That means
  // moves with the fewest legal onward moves should come first.
  def ordered_moves(dim: Int, path: Path, x: Pos) : List[Pos] =   {

    val sorted = for ( n <- legal_moves(dim,path,x) ) yield List(n) ::: legal_moves(dim,path:::List(x):::List (n),n)
    for ( n <- sorted.sortWith(_.size<_.size)) yield n.head

  }

  def first(xs: List[Pos], f: Pos => Option[Path]) : Option[Path] = {
    if (xs == Nil) None

    else {

      val func: Option[Path] = f(xs.head)

      if (func != None) func
      else {
        first(xs.tail, f)
      }
    }
  }

  // Function that searches for a single closed tour using the ordered moves function.
  def closedTourPos(x: Pos) : Path = {

    List( (x._1+1,x._2+2) , (x._1+2,x._2+1), (x._1+2,x._2-1), (x._1+1,x._2-2), (x._1-1,x._2-2), (x._1-2,x._2-1), (x._1-2,x._2+1), (x._1-1,x._2+2) )
  }

  def first_closed_tour_heuristic(dim: Int, path: Path) : Option[Path] = {

    if ( path.length == dim * dim && closedTourPos(path.head).contains(path.last)) Some ( path )

    else{
      val result : Option[Path] = first(ordered_moves(dim,path,path.head),(y => first_closed_tour_heuristic(dim, y :: path) ) )
      result
    }
  }

  // Function that searches for *non-closed* tours.
  def first_tour_heuristic(dim: Int, path: Path) : Option[Path] = {

    if ( path.length == dim * dim ) Some ( path )

    else{
      val result : Option[Path] = first(ordered_moves(dim,path,path.head),(y => first_tour_heuristic(dim, y :: path) ) )
      result
    }
  }

  def main(args: Array[String]): Unit = {
    println("Test cases")
    print("1: ordered_moves(8, List((3,4), (3,2)), (1,3)) == List((0,1), (0,5), (2,1), (2,5)) = ")
    println(ordered_moves(8, List((3,4), (3,2)), (1,3)) == List((0,1), (0,5), (2,1), (2,5)))
    print("2: ordered_moves(8, List((4,0)), (0,0)) == List((2,1), (1,2)) = ")
    println(ordered_moves(8, List((4,0)), (0,0)) == List((2,1), (1,2)))
    print("3: ordered_moves(8, List((0,4)), (0,0)) == List((1,2), (2,1)) = ")
    println(ordered_moves(8, List((0,4)), (0,0)) == List((1,2), (2,1)))
    print("4: first_closed_tour_heuristic(6, List((3,3))) == Some(List((5,4), (4,2), (5,0), (3,1), (1,0), (2,2), (3,0), (5,1), (3,2), (1,1), (0,3), (1,5), (3,4), (5,5), (4,3), (3,5), (1,4), (0,2), (2,3), (0,4), (2,5), (4,4), (5,2), (4,0), (2,1), (0,0), (1,2), (2,4), (0,5), (1,3), (0,1), (2,0), (4,1), (5,3), (4,5), (3,3))) = ")
    println(first_closed_tour_heuristic(6, List((3,3))) == Some(List((5,4), (4,2), (5,0), (3,1), (1,0), (2,2), (3,0), (5,1), (3,2), (1,1), (0,3), (1,5), (3,4), (5,5), (4,3), (3,5), (1,4), (0,2), (2,3), (0,4), (2,5), (4,4), (5,2), (4,0), (2,1), (0,0), (1,2), (2,4), (0,5), (1,3), (0,1), (2,0), (4,1), (5,3), (4,5), (3,3))))
    print("5: first_tour_heuristic(8, List((0,0))) == Some(List((6,5), (7,7), (5,6), (4,4), (2,3), (3,5), (1,4), (0,2), (2,1), (4,2), (5,4), (3,3), (5,2), (7,3), (6,1), (4,0), (3,2), (5,3), (3,4), (4,6), (2,7), (0,6), (2,5), (0,4), (1,6), (3,7), (4,5), (2,4), (4,3), (2,2), (1,0), (3,1), (5,0), (7,1), (6,3), (7,5), (6,7), (5,5), (7,4), (6,6), (4,7), (2,6), (0,7), (1,5), (0,3), (1,1), (3,0), (5,1), (7,0), (6,2), (4,1), (6,0), (7,2), (6,4), (7,6), (5,7), (3,6), (1,7), (0,5), (1,3), (0,1), (2,0), (1,2), (0,0))) = ")
    println(first_tour_heuristic(8, List((0,0))) == Some(List((6,5), (7,7), (5,6), (4,4), (2,3), (3,5), (1,4), (0,2), (2,1), (4,2), (5,4), (3,3), (5,2), (7,3), (6,1), (4,0), (3,2), (5,3), (3,4), (4,6), (2,7), (0,6), (2,5), (0,4), (1,6), (3,7), (4,5), (2,4), (4,3), (2,2), (1,0), (3,1), (5,0), (7,1), (6,3), (7,5), (6,7), (5,5), (7,4), (6,6), (4,7), (2,6), (0,7), (1,5), (0,3), (1,1), (3,0), (5,1), (7,0), (6,2), (4,1), (6,0), (7,2), (6,4), (7,6), (5,7), (3,6), (1,7), (0,5), (1,3), (0,1), (2,0), (1,2), (0,0))))
    print("6: the following test case should produce a result in a small amount of time: ")
    println(first_tour_heuristic(40, List((0,0))))


  }


}



