package graphs

import zio.json._

case class UndirectedGraph[V](vertices: Set[V], adjacencyList: Map[V, Set[V]]) {

  def addEdge(edge: (V, V)): UndirectedGraph[V] = {
    val (v1, v2) = edge
    val updatedList = adjacencyList
      .updatedWith(v1) {
        case Some(neighbors) => Some(neighbors + v2)
        case None => Some(Set(v2))
      }
      .updatedWith(v2) {
        case Some(neighbors) => Some(neighbors + v1)
        case None => Some(Set(v1))
      }
    UndirectedGraph(vertices + v1 + v2, updatedList)
  }

  def removeEdge(edge: (V, V)): UndirectedGraph[V] = {
    val (v1, v2) = edge
    val updatedList = adjacencyList
      .updatedWith(v1) {
        case Some(neighbors) => Some(neighbors - v2)
        case None => None
      }
      .updatedWith(v2) {
        case Some(neighbors) => Some(neighbors - v1)
        case None => None
      }
    UndirectedGraph(vertices, updatedList)
  }

  def edgeCount: Int = adjacencyList.values.map(_.size).sum / 2

  def neighbors(vertex: V): Set[V] = adjacencyList.getOrElse(vertex, Set.empty)
}

object UndirectedGraph {
  implicit def encoder[V: JsonEncoder: JsonFieldEncoder]: JsonEncoder[UndirectedGraph[V]] = {
    implicit val setEncoder: JsonEncoder[Set[V]] = JsonEncoder.set[V]
    implicit val mapEncoder: JsonEncoder[Map[V, Set[V]]] = JsonEncoder.map[V, Set[V]]
    DeriveJsonEncoder.gen[UndirectedGraph[V]]
  }

  implicit def decoder[V: JsonDecoder: JsonFieldDecoder]: JsonDecoder[UndirectedGraph[V]] = {
    implicit val setDecoder: JsonDecoder[Set[V]] = JsonDecoder.set[V]
    implicit val mapDecoder: JsonDecoder[Map[V, Set[V]]] = JsonDecoder.map[V, Set[V]]
    DeriveJsonDecoder.gen[UndirectedGraph[V]]
  }
}
