
val a = List((1, "a"), (2, "b"))

val count = 0

a.foldLeft(count) ((c: Int, e:(Int, String)) => c + e._1)
