package graphs

import zio.json._

case class DirectedGraph[V](vertices: Set[V], adjacencyList: Map[V, Set[V]]) {

  def addEdge(edge: (V, V)): DirectedGraph[V] = {
    val (from, to) = edge
    val updatedList = adjacencyList.updatedWith(from) {
      case Some(neighbors) => Some(neighbors + to)
      case None => Some(Set(to))
    }
    DirectedGraph(vertices + from + to, updatedList)
  }

  def removeEdge(edge: (V, V)): DirectedGraph[V] = {
    val (from, to) = edge
    val updatedList = adjacencyList.updatedWith(from) {
      case Some(neighbors) => Some(neighbors - to)
      case None => None
    }
    DirectedGraph(vertices, updatedList)
  }

  def edgeCount: Int = adjacencyList.values.map(_.size).sum

  def neighbors(vertex: V): Set[V] = adjacencyList.getOrElse(vertex, Set.empty)
}

object DirectedGraph {
  implicit def encoder[V: JsonEncoder: JsonFieldEncoder]: JsonEncoder[DirectedGraph[V]] = {
    implicit val setEncoder: JsonEncoder[Set[V]] = JsonEncoder.set[V]
    implicit val mapEncoder: JsonEncoder[Map[V, Set[V]]] = JsonEncoder.map[V, Set[V]]
    DeriveJsonEncoder.gen[DirectedGraph[V]]
  }

  implicit def decoder[V: JsonDecoder: JsonFieldDecoder]: JsonDecoder[DirectedGraph[V]] = {
    implicit val setDecoder: JsonDecoder[Set[V]] = JsonDecoder.set[V]
    implicit val mapDecoder: JsonDecoder[Map[V, Set[V]]] = JsonDecoder.map[V, Set[V]]
    DeriveJsonDecoder.gen[DirectedGraph[V]]
  }
}
