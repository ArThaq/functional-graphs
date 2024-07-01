package graphs

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DirectedGraphTest extends AnyFlatSpec with Matchers {
  "A DirectedGraph" should "add and remove edges correctly" in {
    val graph = DirectedGraph[Int](Set(1, 2, 3), Map())
    val graphWithEdge = graph.addEdge(1 -> 2)

    graphWithEdge.neighbors(1) should contain (2)
    graphWithEdge.neighbors(2) shouldBe empty

    val graphWithoutEdge = graphWithEdge.removeEdge(1 -> 2)
    graphWithoutEdge.neighbors(1) shouldBe empty
  }

  "A DirectedGraph" should "return the correct number of edges" in {
    val graph = DirectedGraph(Set(1, 2, 3), Map())

    // Ajouter quelques arêtes pour tester
    val graphWithEdges = graph
      .addEdge(1 -> 2)
      .addEdge(2 -> 3)
      .addEdge(1 -> 3)
      .removeEdge(1 -> 3)

    // Vérifier le nombre d'arêtes
    graphWithEdges.edgeCount shouldEqual 2
    print(graphWithEdges.edgeCount)
  }
}
