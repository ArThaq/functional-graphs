package graphs

case class UndirectedGraph[V](vertices: Set[V], adjacencyList: Map[V, Set[V]])(implicit ordering: Ordering[V]) extends Graph[V, (V, V)] {
  def edges: Set[(V, V)] = adjacencyList.flatMap { case (from, toSet) =>
    toSet.map(to => if (ordering.compare(from, to) <= 0) (from, to) else (to, from))
  }.toSet

  def neighbors(vertex: V): Set[V] = adjacencyList.getOrElse(vertex, Set.empty)

  def addEdge(edge: (V, V)): UndirectedGraph[V] = {
    val (from, to) = edge
    val updatedAdjacencyList = adjacencyList
      .updatedWith(from) {
        case Some(neighbors) => Some(neighbors + to)
        case None => Some(Set(to))
      }
      .updatedWith(to) {
        case Some(neighbors) => Some(neighbors + from)
        case None => Some(Set(from))
      }
    copy(adjacencyList = updatedAdjacencyList)
  }

  def removeEdge(edge: (V, V)): UndirectedGraph[V] = {
    val (from, to) = edge
    val updatedAdjacencyList = adjacencyList
      .updatedWith(from) {
        case Some(neighbors) => Some(neighbors - to)
        case None => None
      }
      .updatedWith(to) {
        case Some(neighbors) => Some(neighbors - from)
        case None => None
      }
    copy(adjacencyList = updatedAdjacencyList)
  }
}
