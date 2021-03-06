package org.idey.algo.datastructure.graph;

import org.idey.algo.iterator.graph.DepthFirstSearchIterator;

import java.util.*;

public class Graph<T> {
    private Map<T, Set<T>> map;

    public Graph() {
        map = new HashMap<>();
    }

    public Graph addEdge(T src, T destination){
        if(src!=null){
            if(src==destination || src.equals(destination)){
                throw new IllegalArgumentException("Source and Destination can not be same");
            }else{
                Set<T> desitinations = map.get(src);
                if(desitinations==null){
                    desitinations = new LinkedHashSet<>();
                }
                if(destination!=null){
                    desitinations.add(destination);
                    Set<T> destinationsOfDestination = map.get(destination);
                    if(destinationsOfDestination==null){
                        map.put(destination, new LinkedHashSet<>());
                    }
                }
                map.put(src,desitinations);
            }
        }else{
            throw new IllegalArgumentException("Invalid Source node");
        }
        return this;
    }

    public Iterable<T> getNeighbors(T vertex) {
        Set<T> neighbors = this.map.get(vertex);
        if (neighbors == null || neighbors.isEmpty()) {
            return Collections.emptySet();
        } else {
            return Collections.unmodifiableSet(neighbors);
        }
    }

    public Iterable<T> getAllVertices(){
        if(!map.isEmpty()){
            return Collections.unmodifiableSet(map.keySet());
        }else{
            return Collections.emptySet();
        }
    }

    public boolean isVertexExist(T vertex){
        return map.containsKey(vertex);
    }

    public boolean isGraphEmpty(){
        return map.isEmpty();
    }




    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Graph{");
        sb.append("map=").append(map);
        sb.append('}');
        return sb.toString();
    }


}
