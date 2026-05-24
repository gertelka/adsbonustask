import java.util.*;

public class Graph {
    private final Map<Integer, List<Edge>> adjList;
    private final Map<Integer, Vertex> vertices;

    public Graph() {
        this.adjList = new HashMap<>();
        this.vertices = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        vertices.put(v.getId(), v);
        adjList.putIfAbsent(v.getId(), new ArrayList<>());
    }

    public void addEdge(int from, int to, int weight) {
        if (adjList.containsKey(from) && adjList.containsKey(to)) {
            Vertex src = vertices.get(from);
            Vertex dest = vertices.get(to);
            adjList.get(from).add(new Edge(src, dest, weight));
            adjList.get(to).add(new Edge(dest, src, weight));
        }
    }

    public void dijkstra(int start) {
        if (!vertices.containsKey(start)) {
            System.out.println("The starting point was not found.");
            return;
        }

        int numVertices = vertices.size();
        Map<Integer, Integer> distances = new HashMap<>();
        Set<Integer> visited = new HashSet<>();


        for (int vId : vertices.keySet()) {
            distances.put(vId, Integer.MAX_VALUE);
        }
        distances.put(start, 0);


        for (int i = 0; i < numVertices; i++) {
            int currentVertex = -1;
            int minDistance = Integer.MAX_VALUE;


            for (int vId : vertices.keySet()) {
                if (!visited.contains(vId) && distances.get(vId) < minDistance) {
                    minDistance = distances.get(vId);
                    currentVertex = vId;
                }
            }

            if (currentVertex == -1) break;
            visited.add(currentVertex);


            for (Edge edge : adjList.get(currentVertex)) {
                int neighbor = edge.getDestination().getId();
                if (!visited.contains(neighbor)) {
                    int newDist = distances.get(currentVertex) + edge.getWeight();
                    if (newDist < distances.get(neighbor)) {
                        distances.put(neighbor, newDist);
                    }
                }
            }
        }


        System.out.println("\n======================================");
        System.out.println("Dijkstra's algorithm from the top " + start + ":");
        System.out.println("======================================");
        for (int vId : distances.keySet()) {
            int d = distances.get(vId);
            String distStr = (d == Integer.MAX_VALUE) ? "Unattainable" : String.valueOf(d);
            System.out.println("To the top " + vId + " -> Minimum distance: " + distStr);
        }
        System.out.println("======================================");
    }
}
