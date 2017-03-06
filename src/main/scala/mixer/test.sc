
"""curl -H "Content-Type: application/json" -X POST -d '{"username":"xyz","password":"xyz"}' http://localhost:3000/api/login"""
import akka.http.scaladsl.model.DateTime

val ts = DateTime.now
ts.toString()


import java.time.Instant

val i = Instant.MIN.toString
i

11 / 2
11 % 2



var x = List[Int]()
x = 1 :: x
x
x = 3 :: 5 :: x
x
x.contains(5)
x.indexOf(5)

case class P(n: String, a: Int = 0) {
  val v = Instant.MIN
  def update(u: Int): P = copy(a = u + a)
}



var py = P("a")
val t = py.update(10)
py.v
t.v

import java.util.Calendar
val now = Calendar.getInstance()
val currentMinute = now.get(Calendar.SECOND)







"abc".reverse
