package graphs

case class WeightedGraph[V, E](vertices: Set[V], adjacencyList: Map[V, Set[(V, E)]]) extends Graph[V, (V, V, E)] {
  def edges: Set[(V, V, E)] = adjacencyList.flatMap { case (from, toSet) =>
    toSet.map { case (to, weight) => (from, to, weight) }
  }.toSet

  // Return the neighbors without weights for compatibility with the Graph trait
  def neighbors(vertex: V): Set[V] = adjacencyList.getOrElse(vertex, Set.empty).map(_._1)

  // Return neighbors with weights
  def weightedNeighbors(vertex: V): Set[(V, E)] = adjacencyList.getOrElse(vertex, Set.empty)

  def addEdge(edge: (V, V, E)): WeightedGraph[V, E] = {
    val (from, to, weight) = edge
    val updatedAdjacencyList = adjacencyList.updatedWith(from) {
      case Some(neighbors) => Some(neighbors + ((to, weight)))
      case None => Some(Set((to, weight)))
    }
    copy(adjacencyList = updatedAdjacencyList)
  }

  def removeEdge(edge: (V, V, E)): WeightedGraph[V, E] = {
    val (from, to, weight) = edge
    val updatedAdjacencyList = adjacencyList.updatedWith(from) {
      case Some(neighbors) => Some(neighbors - ((to, weight)))
      case None => None
    }
    copy(adjacencyList = updatedAdjacencyList)
  }
}
