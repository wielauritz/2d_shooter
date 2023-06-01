package algorithms;

import java.util.*;
import java.util.stream.Collectors;

public class AStar {

    /* Nodes repräsentieren die einzelnen Kästchen */
    public interface GraphNode {
        String getId();
    }

    /* IDN für Date Nodes */
    public class Graph<T extends GraphNode> {
        private Set<T> nodes;
        private Map<String, Set<String>> connections;

        public Graph() {
        }

        public T getNode(String id) {
            return nodes.stream()
                    .filter(node -> node.getId().equals(id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No node found with ID"));
        }

        public Set<T> getConnections(T node) {
            return connections.get(node.getId()).stream()
                    .map(this::getNode)
                    .collect(Collectors.toSet());
        }
    }

    /* Kostenberechnung zwischen Nodes */
    public interface Scorer<T extends GraphNode> {
        double computeCost(T from, T to);
    }

    /* Route Nodes für Effi */
    class RouteNode<T extends GraphNode> implements Comparable<RouteNode<T>> {
        private final T current;
        private T previous;
        private double routeScore;
        private double estimatedScore;

        RouteNode(T current) {
            this(current, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        }

        RouteNode(T current, T previous, double routeScore, double estimatedScore) {
            this.current = current;
            this.previous = previous;
            this.routeScore = routeScore;
            this.estimatedScore = estimatedScore;
        }

        public T getCurrent() {
            return current;
        }

        public T getPrevious() {
            return previous;
        }

        public double getRouteScore() {
            return routeScore;
        }

        public double getEstimatedScore() {
            return estimatedScore;
        }

        public void setPrevious(T previous) {
            this.previous = previous;
        }

        public void setRouteScore(double routeScore) {
            this.routeScore = routeScore;
        }

        public void setEstimatedScore(double estimatedScore) {
            this.estimatedScore = estimatedScore;
        }

        @Override
        public int compareTo(RouteNode<T> o) {
            if (this.estimatedScore > o.estimatedScore) {
                return 1;
            } else if (this.estimatedScore < o.estimatedScore) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /* Routenberechner */
    public class RouteFinder<T extends GraphNode> {
        private final Graph<T> graph;
        private final Scorer<T> nextNodeScorer;
        private final Scorer<T> targetScorer;

        public RouteFinder(Graph<T> graph, Scorer<T> nextNodeScorer, Scorer<T> targetScorer) {
            this.graph = graph;
            this.nextNodeScorer = nextNodeScorer;
            this.targetScorer = targetScorer;
        }

        public List<T> findRoute(T from, T to) {
            Queue<RouteNode<T>> openSet = new PriorityQueue<>();
            Map<T, RouteNode<T>> allNodes = new HashMap<>();

            RouteNode<T> start = new RouteNode<>(from, null, 0d, targetScorer.computeCost(from, to));
            openSet.add(start);
            allNodes.put(from, start);

            /* nächster schritt-Node aussuchen */
            while (!openSet.isEmpty()) {
                RouteNode<T> next = openSet.poll();
                if (next.getCurrent().equals(to)) {
                    List<T> route = new ArrayList<>();
                    RouteNode<T> current = next;
                    do {
                        route.add(0, current.getCurrent());
                        current = allNodes.get(current.getPrevious());
                    } while (current != null);
                    return route;
                }

                /* Falls nicht angekommen (weitermachen/break) */
                graph.getConnections(next.getCurrent()).forEach(connection -> {
                    RouteNode<T> nextNode = allNodes.getOrDefault(connection, new RouteNode<>(connection));
                    allNodes.put(connection, nextNode);

                    double newScore = next.getRouteScore() + nextNodeScorer.computeCost(next.getCurrent(), connection);
                    if (newScore < nextNode.getRouteScore()) {
                        nextNode.setPrevious(next.getCurrent());
                        nextNode.setRouteScore(newScore);
                        nextNode.setEstimatedScore(newScore + targetScorer.computeCost(connection, to));
                        openSet.add(nextNode);
                    }
                });
            }

            throw new IllegalStateException("No route found");
        }
    }

    /* Beispiel-Implementierung von GraphNode */
    public class Station implements GraphNode {
        private String id;

        public Station(String id) {
            this.id = id;
        }

        @Override
        public String getId() {
            return id;
        }
    }
}
