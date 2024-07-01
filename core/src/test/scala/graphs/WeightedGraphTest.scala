package graphs

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class WeightedGraphTest extends AnyFlatSpec with Matchers {
  "A WeightedGraph" should "add and remove edges correctly" in {
    val graph = WeightedGraph[Int, Int](Set(1, 2, 3), Map())
    val graphWithEdge = graph.addEdge((1, 2, 10))

    graphWithEdge.neighbors(1) should contain (2)
    graphWithEdge.weightedNeighbors(1) should contain ((2, 10))

    val graphWithoutEdge = graphWithEdge.removeEdge((1, 2, 10))
    graphWithoutEdge.neighbors(1) should not contain (2)
    graphWithoutEdge.weightedNeighbors(1) should not contain ((2, 10))
  }

  it should "correctly list all edges" in {
    val graph = WeightedGraph[Int, Int](Set(1, 2, 3), Map())
      .addEdge((1, 2, 10))
      .addEdge((2, 3, 20))

    graph.edges should contain allOf ((1, 2, 10), (2, 3, 20))
  }
}
