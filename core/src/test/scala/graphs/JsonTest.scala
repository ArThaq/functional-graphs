package graphs

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import zio.json._

class JsonTest extends AnyFlatSpec with Matchers {

  "DirectedGraph JSON encoding/decoding" should "work correctly" in {
    val graph = DirectedGraph(Set(1, 2, 3), Map(1 -> Set(2), 2 -> Set(3), 3 -> Set.empty))
    val json = graph.toJson
    val decodedGraph = json.fromJson[DirectedGraph[Int]].getOrElse(fail("Decoding failed"))

    decodedGraph shouldEqual graph
  }

  "UndirectedGraph JSON encoding/decoding" should "work correctly" in {
    val graph = UndirectedGraph(Set(1, 2, 3), Map(1 -> Set(2), 2 -> Set(1, 3), 3 -> Set(2)))
    val json = graph.toJson
    val decodedGraph = json.fromJson[UndirectedGraph[Int]].getOrElse(fail("Decoding failed"))

    decodedGraph shouldEqual graph
  }

  "WeightedGraph JSON encoding/decoding" should "work correctly" in {
    val graph = WeightedGraph(Set(1, 2, 3), Map(1 -> Set((2, 1.0)), 2 -> Set((3, 2.0)), 3 -> Set.empty))
    val json = graph.toJson
    val decodedGraph = json.fromJson[WeightedGraph[Int, Double]].getOrElse(fail("Decoding failed"))

    decodedGraph shouldEqual graph
  }
}
