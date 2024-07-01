package graphs

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class UndirectedGraphTest extends AnyFlatSpec with Matchers {
  "An UndirectedGraph" should "add and remove edges correctly" in {
    implicit val ordering: Ordering[Int] = Ordering.Int
    val graph = UndirectedGraph[Int](Set(1, 2, 3), Map())
    val graphWithEdge = graph.addEdge(1 -> 2)

    graphWithEdge.neighbors(1) should contain (2)
    graphWithEdge.neighbors(2) should contain (1)

    val graphWithoutEdge = graphWithEdge.removeEdge(1 -> 2)
    graphWithoutEdge.neighbors(1) should not contain (2)
    graphWithoutEdge.neighbors(2) should not contain (1)
  }
}
