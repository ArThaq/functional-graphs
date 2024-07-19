package graphs

import zio.json._

case class WeightedGraph[V, E](vertices: Set[V], adjacencyList: Map[V, Set[(V, E)]]) {

  def addEdge(edge: (V, V, E)): WeightedGraph[V, E] = {
    val (from, to, weight) = edge
    val updatedList = adjacencyList.updatedWith(from) {
      case Some(neighbors) => Some(neighbors + ((to, weight)))
      case None => Some(Set((to, weight)))
    }
    WeightedGraph(vertices + from + to, updatedList)
  }

  def removeEdge(edge: (V, V, E)): WeightedGraph[V, E] = {
    val (from, to, weight) = edge
    val updatedList = adjacencyList.updatedWith(from) {
      case Some(neighbors) => Some(neighbors - ((to, weight)))
      case None => None
    }
    WeightedGraph(vertices, updatedList)
  }

  def edgeCount: Int = adjacencyList.values.map(_.size).sum

  def neighbors(vertex: V): Set[(V, E)] = adjacencyList.getOrElse(vertex, Set.empty)

  // Ajoutez cette méthode pour obtenir toutes les arêtes
  def edges: Set[(V, V, E)] = {
    adjacencyList.flatMap { case (from, neighbors) =>
      neighbors.map { case (to, weight) => (from, to, weight) }
    }.toSet
  }

  // Ajoutez cette méthode pour obtenir les voisins pondérés
  def weightedNeighbors(vertex: V): Set[(V, E)] = adjacencyList.getOrElse(vertex, Set.empty)
}

object WeightedGraph {
  implicit def encoder[V: JsonEncoder: JsonFieldEncoder, E: JsonEncoder]: JsonEncoder[WeightedGraph[V, E]] = {
    implicit val setEncoder: JsonEncoder[Set[(V, E)]] = JsonEncoder.set[(V, E)]
    implicit val mapEncoder: JsonEncoder[Map[V, Set[(V, E)]]] = JsonEncoder.map[V, Set[(V, E)]]
    DeriveJsonEncoder.gen[WeightedGraph[V, E]]
  }

  implicit def decoder[V: JsonDecoder: JsonFieldDecoder, E: JsonDecoder]: JsonDecoder[WeightedGraph[V, E]] = {
    implicit val setDecoder: JsonDecoder[Set[(V, E)]] = JsonDecoder.set[(V, E)]
    implicit val mapDecoder: JsonDecoder[Map[V, Set[(V, E)]]] = JsonDecoder.map[V, Set[(V, E)]]
    DeriveJsonDecoder.gen[WeightedGraph[V, E]]
  }
}
