package graphs

case class DirectedGraph[V](vertices: Set[V], adjacencyList: Map[V, Set[V]]) extends Graph[V, (V, V)] {
  def edges: Set[(V, V)] = adjacencyList.flatMap { case (from, toSet) => toSet.map(to => (from, to)) }.toSet

  def neighbors(vertex: V): Set[V] = adjacencyList.getOrElse(vertex, Set.empty)

  def addEdge(edge: (V, V)): DirectedGraph[V] = {
    val (from, to) = edge
    val updatedAdjacencyList = adjacencyList.updatedWith(from) {
      case Some(neighbors) => Some(neighbors + to)
      case None => Some(Set(to))
    }
    copy(adjacencyList = updatedAdjacencyList)
  }

  def removeEdge(edge: (V, V)): DirectedGraph[V] = {
    val (from, to) = edge
    val updatedAdjacencyList = adjacencyList.updatedWith(from) {
      case Some(neighbors) => Some(neighbors - to)
      case None => None
    }
    copy(adjacencyList = updatedAdjacencyList)
  }

  // méthode pour tests unitaires : compte le nombre d'arêtes
  def edgeCount: Int = adjacencyList.values.map(_.size).sum
}
